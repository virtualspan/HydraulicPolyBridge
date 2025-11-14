package net.virtualspan;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.virtualspan.compat.FloodgateTracker;
import net.virtualspan.compat.FloodgatePolyMapHandler;

public class PolyCompatHydraulic implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        FloodgateTracker.register();
        ServerLifecycleEvents.SERVER_STARTED.register((MinecraftServer server) -> {
            FloodgatePolyMapHandler.register(server);
            System.out.println("[PolyCompatHydraulic] PolyMapProvider hooked. Floodgate players will use NOPPolyMap.");
        });
    }
}
