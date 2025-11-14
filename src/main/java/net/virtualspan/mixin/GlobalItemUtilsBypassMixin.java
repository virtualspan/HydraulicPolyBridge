package net.virtualspan.mixin;

import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.nucleoid.packettweaker.PacketContext;
import org.geysermc.floodgate.api.FloodgateApi;

/**
 * Mixin to override Polymer item disguises for Floodgate (Bedrock) players.
 * Ensures Bedrock clients see the real item stack instead of disguised ones.
 */
@Mixin(PolymerItemUtils.class)
public class GlobalItemUtilsBypassMixin {
    @Inject(method = "getItemSafely", at = @At("RETURN"), cancellable = true)
    private static void bypassItemSafely(PolymerItem polymerItem,
                                         ItemStack stack,
                                         PacketContext context,
                                         int maxDistance,
                                         CallbackInfoReturnable<PolymerItemUtils.ItemWithMetadata> cir) {
        if (context instanceof PacketContext.NotNullWithPlayer ctx) {
            ServerPlayerEntity player = ctx.getPlayer();
            if (player != null) {
                if (FloodgateApi.getInstance().isFloodgatePlayer(player.getUuid())) {
                    // Bedrock: bypass disguise, return original item with metadata
                    Item realItem = stack.getItem();
                    cir.setReturnValue(new PolymerItemUtils.ItemWithMetadata(realItem,
                            polymerItem.getPolymerItemModel(stack, context)));
                } else {
                    // Java: keep Polymer’s disguised return value
                    cir.setReturnValue(cir.getReturnValue());
                }
            }
        }
    }
}
