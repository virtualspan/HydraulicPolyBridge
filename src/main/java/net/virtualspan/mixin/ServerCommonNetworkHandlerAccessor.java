package net.virtualspan.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerCommonNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Accessor mixin to expose the protected 'connection' field
 * from ServerCommonNetworkHandler.
 */
@Mixin(ServerCommonNetworkHandler.class)
public interface ServerCommonNetworkHandlerAccessor {
    @Accessor("connection")
    ClientConnection getConnection();
}
