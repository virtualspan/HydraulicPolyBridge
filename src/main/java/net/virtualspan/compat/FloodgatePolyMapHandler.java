package net.virtualspan.compat;

import io.github.theepicblock.polymc.api.misc.PolyMapProvider;
import io.github.theepicblock.polymc.impl.NOPPolyMap;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;

import java.net.SocketAddress;
import java.net.InetSocketAddress;

/**
 * Assigns NOPPolyMap to confirmed Floodgate connections.
 * Provisional NOP is only applied for Geyser/Bedrock connections (thread/addr heuristics),
 * never for plain Java connections.
 */
public final class FloodgatePolyMapHandler {
    private FloodgatePolyMapHandler() {}

    public static void register(MinecraftServer server) {
        PolyMapProvider.EVENT.register((ClientConnection connection) -> {
            final String thread = Thread.currentThread().getName();
            final SocketAddress addr = connection.getAddress();

            System.out.println("[PolyCompatHydraulic] PolyMapProvider asked for connection: " + connection + " on thread=" + thread + " addr=" + addr);

            // 1) Confirmed Floodgate connection → return NOP immediately
            if (FloodgateTracker.isFloodgateConnection(connection)) {
                System.out.println("[PolyCompatHydraulic] Assigned NOPPolyMap to confirmed Floodgate connection " + connection);
                return NOPPolyMap.INSTANCE;
            }

            // 2) Heuristics to detect Bedrock/Geyser query early
            boolean isGeyserThread = thread.contains("Geyser");
            boolean isGeyserAddr = false;
            if (addr instanceof InetSocketAddress inet) {
                // Many Geyser→Fabric bridges show loopback + port 0 for Bedrock
                isGeyserAddr = (inet.getPort() == 0) || (inet.getAddress() != null && inet.getAddress().isLoopbackAddress() && inet.getPort() == 0);
            }

            // Only allow provisional NOP for Geyser/Bedrock paths
            if (isGeyserThread || isGeyserAddr) {
                if (!FloodgateTracker.isProvisional(connection)) {
                    FloodgateTracker.markProvisional(connection);
                    System.out.println("[PolyCompatHydraulic] Provisional NOP for early Geyser connection " + connection + " (awaiting INIT confirmation)");
                    return NOPPolyMap.INSTANCE;
                }
            }

            // 3) Java connection (Netty threads / normal addr) → never provisional
            System.out.println("[PolyCompatHydraulic] Returning default PolyMap (no Floodgate match; not a Geyser early query).");
            return null;
        });
    }
}
