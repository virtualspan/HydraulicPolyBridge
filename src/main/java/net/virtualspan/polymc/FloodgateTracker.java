package net.virtualspan.polymc;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.geysermc.floodgate.api.FloodgateApi;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import net.virtualspan.mixin.ServerCommonNetworkHandlerAccessor;

/**
 * Tracks Floodgate players by connection at INIT (earliest reliable timing),
 * and cleans up on DISCONNECT. Also keeps a UUID set for diagnostics.
 */
public final class FloodgateTracker {
    private FloodgateTracker() {}

    private static final Map<ClientConnection, UUID> FLOODGATE_CONN_TO_UUID = new ConcurrentHashMap<>();
    private static final Set<ClientConnection> PROVISIONAL_CONNECTIONS = ConcurrentHashMap.newKeySet();

    public static void register() {
        ServerPlayConnectionEvents.INIT.register((ServerPlayNetworkHandler handler, net.minecraft.server.MinecraftServer server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            UUID uuid = player.getUuid();
            ClientConnection conn = ((ServerCommonNetworkHandlerAccessor)(Object)handler).getConnection();

            if (FloodgateApi.getInstance().isFloodgatePlayer(uuid)) {
                FLOODGATE_CONN_TO_UUID.put(conn, uuid);
                PROVISIONAL_CONNECTIONS.remove(conn);
            } else {
                PROVISIONAL_CONNECTIONS.remove(conn);
            }
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            ClientConnection conn = ((ServerCommonNetworkHandlerAccessor)(Object)handler).getConnection();
            FLOODGATE_CONN_TO_UUID.remove(conn);
            PROVISIONAL_CONNECTIONS.remove(conn);
        });
    }

    public static boolean isFloodgateConnection(ClientConnection connection) {
        return FLOODGATE_CONN_TO_UUID.containsKey(connection);
    }

    public static boolean isProvisional(ClientConnection connection) {
        return PROVISIONAL_CONNECTIONS.contains(connection);
    }

    public static void markProvisional(ClientConnection connection) {
        PROVISIONAL_CONNECTIONS.add(connection);
    }
}
