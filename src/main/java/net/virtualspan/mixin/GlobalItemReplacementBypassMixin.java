package net.virtualspan.mixin;

import eu.pb4.polymer.core.api.item.PolymerItem;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.nucleoid.packettweaker.PacketContext;
import org.geysermc.floodgate.api.FloodgateApi;

/**
 * Mixin to override Polymer item disguises for Floodgate (Bedrock) players.
 * Ensures Bedrock clients see the real item instead of disguised replacements.
 */
@Mixin(PolymerItem.class)
public interface GlobalItemReplacementBypassMixin {
    @Inject(method = "getPolymerReplacement", at = @At("RETURN"), cancellable = true)
    private void bypassReplacementForBedrock(Item item,
                                             PacketContext context,
                                             CallbackInfoReturnable<Item> cir) {
        if (context instanceof PacketContext.NotNullWithPlayer ctx) {
            ServerPlayerEntity player = ctx.getPlayer();
            if (player != null) {
                if (FloodgateApi.getInstance().isFloodgatePlayer(player.getUuid())) {
                    // Bedrock: bypass disguise, return original item
                    cir.setReturnValue(item);
                } else {
                    // Java: keep Polymer’s disguised return value
                    cir.setReturnValue(cir.getReturnValue());
                }
            }
        }
    }
}
