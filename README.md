####  ⚠️ Warning: This mod is vibecoded and not actively maintained. Feel free to test out the mod but it may be unstable and contain bugs!

# HydraulicPolyBridge
HydraulicPolyBridge allows [Polymer Patch Mods](https://modrinth.com/mods?q=polymer+patch) and [PolyMc](https://github.com/TheEpicBlock/PolyMc) to work seamlessly alongside [Hydraulic](https://geysermc.org/download?project=other-projects&hydraulic=expanded), ensuring both Java and Bedrock players get a consistent experience.

Recommended PolyMc fork: https://github.com/SAGUMEDREAM/PolyMc/releases

## How it works
Normally, using a mod like Farmer’s Delight with Hydraulic works as expected. However, when you add either a Polymer Patch Mod or PolyMc (for example, `Farmer’s Delight + Farmer’s Delight Polymer Patch` or `Farmer’s Delight + PolyMc`), Polymer/PolyMc interferes with Hydraulic by disguising items and blocks. This works with custom textures for Java players, but Geyser/Bedrock players only see vanilla placeholders such as trial keys or invisible blocks from the disguises since custom Polymer/PolyMc textures don’t work on Bedrock.

HydraulicPolyBridge intercepts Polymer/PolyMC’s disguises and bypasses them for Floodgate players. Bedrock clients are sent the original item or block instead of the disguised version which Hydraulic provides textures for, while Java players continue to see Polymer/PolyMc’s intended textures.

In practice, this means that `Farmer’s Delight + Hydraulic` works just as smoothly as `Farmer’s Delight + Farmer’s Delight Polymer Patch + Hydraulic` or `Farmer’s Delight + PolyMc + Hydraulic` on Bedrock.

## Compatibility
Mods with custom entities are not yet supported (as in the entites will be invisible on Bedrock), because Hydraulic itself does not currently handle them. Beyond that, if `X + Hydraulic` works, then you can expect `HydraulicPolyBridge + X + X Polymer Patch + Hydraulic` or `HydraulicPolyBridge + X + PolyMc + Hydraulic` to also work on Bedrock.

## HydraulicPolyBridge vs [Bedframe](https://modrinth.com/mod/bedframe)

| Feature                  | HydraulicPolyBridge                                                                    | Bedframe                                                                                 |
|--------------------------|----------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------|
| **Approach**             | Bypasses Polymer/PolyMc disguises for Bedrock players, sends original items/blocks which Hydraulic converts | Converts Polymer textures into Bedrock resourcepacks |
| **Java Players**         | See Polymer/PolyMc’s intended disguises with custom textures                           | See Polymer’s intended disguises with custom textures                                    |
| **Bedrock Players**      | See original items/blocks (without disguises) which then have converted textures from Hydraulic | See converted Polymer textures (if supported)                                        |
| **Compatibility**        | Works with Polymer Patch Mods and PolyMC (when the base mod also works using Hydraulic)| Supports many Polymer mods (see [spreadsheet](https://docs.google.com/spreadsheets/d/1ru1hisG0D22LlQG8Kkw0XwVGLOjtfU8odAiiZo6nnzQ/edit?usp=sharing)), but has limitations, especially towards Polymer Patch Mods |
| **Focus**                | Ensures that Polymer Patch Mods and PolyMC don’t break Bedrock support with Hydraulic  | Converts Polymer blocks and items textures to Bedrock                                    |

TL;DR: Bedframe focuses on converting Polymer textures for Bedrock, while HydraulicPolyBridge ensures Polymer Patch Mods and PolyMC don’t interfere with Hydraulic.

## Credits
HydraulicPolyBridge wouldn’t exist without the work of these projects:
- [Hydraulic](https://geysermc.org/projects/hydraulic) — Clientside mod compatibility layer for Geyser, which is the foundation for this mod.
- [Bedframe](https://modrinth.com/mod/bedframe) — Much of the design here was inspired by Bedframe’s excellent work making Polymer mods compatible with Geyser.
- [Polymer](https://modrinth.com/mod/polymer) — The disguise system for items and blocks that this mod intercepts.
- [Polymer Patch Mods](https://modrinth.com/mods?q=polymer+patch) — Adds Polymer compatibility patches for many mods.
- [PolyMc](https://github.com/TheEpicBlock/PolyMc) - The original mod that allowed vanilla Java clients to join clientside modded servers.
- [PolyMC fork](https://github.com/SAGUMEDREAM/PolyMc/releases) by [@SAGUMEDREAM](https://github.com/SAGUMEDREAM) — The PolyMc fork that this was developed with.
- [Other PolyMC forks](https://github.com/TheEpicBlock/PolyMc/forks) — Community forks that maintain PolyMc functionality for newer versions.
- [Floodgate](https://modrinth.com/mod/floodgate) — Provides Bedrock player detection API used.
- [Geyser](https://geysermc.org/) — The plugin/mod that allows Bedrock clients to connect to Java servers.

## Bonus
If you want a (very buggy!) proof of concept, you can use this mod alongside the [Polymania](https://modrinth.com/modpack/polymania) modpack, provided you remove and add the mods below:

**Add:**
- [Geyser Custom Item API V2 + Hydraulic](https://geysermc.org/download/?project=other-projects&hydraulic=expanded)
- [Floodgate](https://modrinth.com/mod/floodgate/versions?l=fabric)
- [HydraulicPolyBridge (this mod)](https://github.com/virtualspan/Polymer-Patch-Hydraulic-Compat/releases/tag/1.0)

**Remove:**
- Dungeons and Taverns
- ForgeConfigAPIPort
- Leaves Us In Peace
- PolyFactory

**Optionally to add Biomes O Plenty with PolyMc:**

**Add:**
- [PolyMC fork](https://github.com/SAGUMEDREAM/PolyMc/releases)
- [Biomes O Plenty](https://modrinth.com/mod/biomes-o-plenty)

**Remove:**
- CC: Tweaked Polymer Patch
