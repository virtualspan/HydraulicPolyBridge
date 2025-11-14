package net.virtualspan.polymc;

import io.github.theepicblock.polymc.api.misc.PolyMapProvider;
import io.github.theepicblock.polymc.impl.NOPPolyMap;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;

import java.net.SocketAddress;
import java.net.InetSocketAddress;

/**
 * Returns NOPPolyMap for Floodgate connections registered at INIT.
 * Provisional NOP is only applied for Geyser/Bedrock connections (thread/addr heuristics).
 */
public final class FloodgatePolyMapHandler {
    private FloodgatePolyMapHandler() {}

    public static void register(MinecraftServer server) {
        PolyMapProvider.EVENT.register((ClientConnection connection) -> {
            if (FloodgateTracker.isFloodgateConnection(connection)) {
                return NOPPolyMap.INSTANCE;
            }

            String thread = Thread.currentThread().getName();
            SocketAddress addr = connection.getAddress();

            boolean isGeyserThread = thread.contains("Geyser");
            boolean isGeyserAddr = false;
            if (addr instanceof InetSocketAddress inet) {
                isGeyserAddr = (inet.getPort() == 0) ||
                        (inet.getAddress() != null && inet.getAddress().isLoopbackAddress() && inet.getPort() == 0);
            }

            if (isGeyserThread || isGeyserAddr) {
                if (!FloodgateTracker.isProvisional(connection)) {
                    FloodgateTracker.markProvisional(connection);
                    return NOPPolyMap.INSTANCE;
                }
            }

            return null;
        });
    }
}
