package net.virtualspan.compat;

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
 * Tracks Floodgate by connection (definitive) and supports provisional bindings
 * for the early PolyMapProvider call that can occur before INIT registers.
 */
public final class FloodgateTracker {
    private FloodgateTracker() {}

    // Definitive mapping: Floodgate connections -> UUID
    private static final Map<ClientConnection, UUID> FLOODGATE_CONN_TO_UUID = new ConcurrentHashMap<>();
    // Provisional mapping: connections we temporarily flagged as NOP to cover the race
    private static final Set<ClientConnection> PROVISIONAL_CONNECTIONS = ConcurrentHashMap.newKeySet();

    public static void register() {
        // INIT: earliest point we can confirm Floodgate status
        ServerPlayConnectionEvents.INIT.register((ServerPlayNetworkHandler handler, net.minecraft.server.MinecraftServer server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            UUID uuid = player.getUuid();
            ClientConnection conn = ((ServerCommonNetworkHandlerAccessor)(Object)handler).getConnection();

            boolean isFg = FloodgateApi.getInstance().isFloodgatePlayer(uuid);
            System.out.println("[PolyCompatHydraulic] INIT: handler=" + handler + " conn=" + conn + " player=" + player.getName().getString());

            if (isFg) {
                FLOODGATE_CONN_TO_UUID.put(conn, uuid);
                System.out.println("[PolyCompatHydraulic] INIT: Confirmed Floodgate connection -> UUID " + conn + " -> " + uuid);
                // If it was provisional, it’s now definitive; we keep NOP binding.
                PROVISIONAL_CONNECTIONS.remove(conn);
            } else {
                // If we optimistically assigned NOP to a non-Floodgate connection, retract it now.
                if (PROVISIONAL_CONNECTIONS.remove(conn)) {
                    System.out.println("[PolyCompatHydraulic] INIT: Retracted provisional NOP for non-Floodgate connection " + conn);
                } else {
                    System.out.println("[PolyCompatHydraulic] INIT: Non-Floodgate player detected: " + player.getName().getString());
                }
            }
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            ClientConnection conn = ((ServerCommonNetworkHandlerAccessor)(Object)handler).getConnection();
            UUID removed = FLOODGATE_CONN_TO_UUID.remove(conn);
            PROVISIONAL_CONNECTIONS.remove(conn);
            if (removed != null) {
                System.out.println("[PolyCompatHydraulic] DISCONNECT: Removed Floodgate mapping " + conn + " -> " + removed);
            } else {
                System.out.println("[PolyCompatHydraulic] DISCONNECT: No Floodgate mapping found for " + conn);
            }
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
