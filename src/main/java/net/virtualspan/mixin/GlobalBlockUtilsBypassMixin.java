package net.virtualspan.mixin;

import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.nucleoid.packettweaker.PacketContext;
import org.geysermc.floodgate.api.FloodgateApi;

/**
 * Mixin to override Polymer block disguises for Floodgate (Bedrock) players.
 * Ensures Bedrock clients see the real block state instead of disguised ones.
 */
@Mixin(PolymerBlockUtils.class)
public class GlobalBlockUtilsBypassMixin {
    @Inject(method = "getBlockStateSafely", at = @At("RETURN"), cancellable = true)
    private static void bypassBlockSafely(PolymerBlock polymerBlock,
                                          BlockState blockState,
                                          int maxDistance,
                                          PacketContext context,
                                          CallbackInfoReturnable<BlockState> cir) {
        if (context instanceof PacketContext.NotNullWithPlayer ctx) {
            ServerPlayerEntity player = ctx.getPlayer();
            if (player != null) {
                if (FloodgateApi.getInstance().isFloodgatePlayer(player.getUuid())) {
                    // Bedrock: bypass disguise, return original block state
                    cir.setReturnValue(blockState);
                } else {
                    // Java: keep Polymer’s disguised return value
                    cir.setReturnValue(cir.getReturnValue());
                }
            }
        }
    }
}
