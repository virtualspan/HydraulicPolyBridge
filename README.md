# Polymer Patch Hydraulic Compat
Allows Polymer Patch Mods to work alongside with Hydraulic as seamlessly as without them.

## How it works
Normally, using a mod like Farmer’s Delight with Hydraulic works as expected. However, when you add a Polymer Patch mod (for example, Farmer’s Delight + Farmer’s Delight Polymer Patch), Polymer interferes with Hydraulic by disguising items and blocks, which works with a custom texture for Java players, but it leaves Bedrock players with vanilla placeholders such as a Trial Key or invisible blocks as these custom textures don't work on Bedrock.

This compat mod intercepts Polymer’s disguises and allows Polymer Patch mods to work seamlessly alongside Hydraulic. For Bedrock players connecting through Geyser/Floodgate, it sends the original item or block instead of the disguised version.

In practice, this means that `Farmer’s Delight + Hydraulic` works just as smoothly as `Farmer’s Delight + Farmer’s Delight Polymer Patch + Hydraulic`.

## Compatibility

Polymer mods with custom entities are not supported as they are yet to be supported in Hydraulic. Other than that, there hasn't been enough testing to make a list (except Farmer's Delight as it used in testing). But you should be able to expect that if `X + Hydraulic` works, that `This Mod + X + X Polymer Patch + Hydraulic` should also work.

You can also get fully Polymer mods such as the [TSA](https://modrinth.com/mods?q=tsa) mods (excluding Decorations & Furniture) to work with alongside this mod with [my fork](https://github.com/virtualspan/bedframe/releases) of [Bedframe](https://modrinth.com/mod/bedframe) by [@sylvxa](https://github.com/sylvxa). It adds compatibility by simply removing the item component of Bedframe. Meaning that you will have to use [Rainbow](https://geysermc.org/wiki/other/rainbow/) to convert item textures or they will be invisible in inventory, but only for the items/blocks added by the fully Polymer mods.

See [this spreadsheet](https://docs.google.com/spreadsheets/d/1ru1hisG0D22LlQG8Kkw0XwVGLOjtfU8odAiiZo6nnzQ/edit?usp=sharing) for mods that Bedframe adds support for. Polymer Patch Mods that don't work with Bedframe will work with this mod.

## Bonus

If you want a (very buggy!) proof of concept, you can use this mod alongside the [Polymania](https://modrinth.com/modpack/polymania) modpack given that you remove and add the mods below:

**Add:**
- [Geyser Custom Item API V2 + Hydraulic](https://geysermc.org/download/?project=other-projects&hydraulic=expanded)
- [Floodgate](https://modrinth.com/mod/floodgate/versions?l=fabric)
- [Polymer Patch Hydraulic Compat (this mod)](https://github.com/virtualspan/Polymer-Patch-Hydraulic-Compat/releases/tag/1.0)
- (Optional) [My fork of Bedframe](https://github.com/virtualspan/bedframe/releases)

**Remove:**
- Dungeons and Tarverns
- ForgeConfigAPIPort
- Leaves Us In Peace
- PolyFactory
