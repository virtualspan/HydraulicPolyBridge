package net.virtualspan.mixin;

import eu.pb4.polymer.core.api.entity.PolymerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.nucleoid.packettweaker.PacketContext;
import org.geysermc.floodgate.api.FloodgateApi;

@Mixin(PolymerEntity.class)
public interface GlobalEntityReplacementBypassMixin {
    @Inject(method = "getPolymerEntityType", at = @At("RETURN"), cancellable = true)
    private void bypassEntityReplacement(PacketContext context,
                                         CallbackInfoReturnable<EntityType<?>> cir) {
        if (context instanceof PacketContext.NotNullWithPlayer ctx) {
            ServerPlayerEntity player = ctx.getPlayer();
            if (player != null) {
                if (FloodgateApi.getInstance().isFloodgatePlayer(player.getUuid())) {
                    // Bedrock: bypass disguise
                    System.out.println("[PolyCompat] Entity replacement bypass triggered for " + cir.getReturnValue());
                    cir.setReturnValue(cir.getReturnValue());
                } else {
                    // Java: keep Polymer’s return value
                    cir.setReturnValue(cir.getReturnValue());
                }
            }
        }
    }
}
