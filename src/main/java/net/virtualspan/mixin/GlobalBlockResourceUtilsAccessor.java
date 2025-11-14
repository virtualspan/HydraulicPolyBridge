package net.virtualspan.mixin;

import eu.pb4.polymer.blocks.api.BlockResourceCreator;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Accessor mixin to expose the private static CREATOR field
 * from PolymerBlockResourceUtils.
 */
@Mixin(value = PolymerBlockResourceUtils.class, remap = false)
public interface GlobalBlockResourceUtilsAccessor {
    @Accessor("CREATOR")
    static BlockResourceCreator getCREATOR() {
        throw new AssertionError();
    }
}
