package net.virtualspan;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.virtualspan.polymc.FloodgateTracker;
import net.virtualspan.polymc.FloodgatePolyMapHandler;

/**
 * Main mod initializer. Registers Floodgate tracking and PolyMap handler
 * when the dedicated server starts.
 */
public class PolyCompatHydraulic implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        // Track Floodgate players early
        FloodgateTracker.register();

        // Hook PolyMapProvider once the server has started
        ServerLifecycleEvents.SERVER_STARTED.register((MinecraftServer server) -> {
            FloodgatePolyMapHandler.register(server);
        });
    }
}
