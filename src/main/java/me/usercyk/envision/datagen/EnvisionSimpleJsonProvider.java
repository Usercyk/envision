package me.usercyk.envision.datagen;

import com.google.gson.JsonObject;
import me.usercyk.envision.Envision;
import me.usercyk.envision.datagen.custom.SimpleJsonProvider;
import me.usercyk.envision.worldgen.EnvisionWorlds;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

import static me.usercyk.envision.Envision.GSON;

public class EnvisionSimpleJsonProvider extends SimpleJsonProvider {

    private static final String FILLED_CHUNK_STRING = EnvisionWorlds.FILLED_WORLD.chunkGenerator().toString();

    private static final String SKYBLOCK_CHUNK_STRING = EnvisionWorlds.SKYBLOCK.chunkGenerator().toString();

    private static final String WOODEN_HOPPER_FILE;

    private static final String WOODEN_HOPPER_SIDE_FILE;

    private static final String FILLED_WORLD_FILE;

    private static final String SKYBLOCK_FILE;

    static {
        FILLED_WORLD_FILE = """
                {
                  "dimensions": {
                    "minecraft:overworld": {
                      "type": "minecraft:overworld",
                      "generator": {
                        "type": "%s",
                        "biome_source": {
                          "type": "minecraft:multi_noise",
                          "preset": "minecraft:overworld"
                        },
                        "settings": "minecraft:overworld",
                        "filling_block": "minecraft:stone"
                      }
                    },
                    "minecraft:the_end": {
                      "type": "minecraft:the_end",
                      "generator": {
                        "type": "%s",
                        "biome_source": {
                          "type": "minecraft:the_end"
                        },
                        "settings": "minecraft:end",
                        "filling_block": "minecraft:end_stone"
                      }
                    },
                    "minecraft:the_nether": {
                      "type": "minecraft:the_nether",
                      "generator": {
                        "type": "%s",
                        "biome_source": {
                          "type": "minecraft:multi_noise",
                          "preset": "minecraft:nether"
                        },
                        "settings": "minecraft:nether",
                        "filling_block": "minecraft:netherrack"
                      }
                    }
                  }
                }""".formatted(FILLED_CHUNK_STRING, FILLED_CHUNK_STRING, FILLED_CHUNK_STRING);
        SKYBLOCK_FILE = """
                {
                  "dimensions": {
                    "minecraft:overworld": {
                      "type": "minecraft:overworld",
                      "generator": {
                        "type": "%s",
                        "biome_source": {
                          "type": "minecraft:multi_noise",
                          "preset": "minecraft:overworld"
                        },
                        "settings": "minecraft:overworld"
                      }
                    },
                    "minecraft:the_end": {
                      "type": "minecraft:the_end",
                      "generator": {
                        "type": "%s",
                        "biome_source": {
                          "type": "minecraft:the_end"
                        },
                        "settings": "minecraft:end"
                      }
                    },
                    "minecraft:the_nether": {
                      "type": "minecraft:the_nether",
                      "generator": {
                        "type": "%s",
                        "biome_source": {
                          "type": "minecraft:multi_noise",
                          "preset": "minecraft:nether"
                        },
                        "settings": "minecraft:nether"
                      }
                    }
                  }
                }""".formatted(SKYBLOCK_CHUNK_STRING, SKYBLOCK_CHUNK_STRING, SKYBLOCK_CHUNK_STRING);

        WOODEN_HOPPER_FILE = """
                {
                   "ambientocclusion": false,
                   "textures": {
                     "particle": "envision:block/wooden_hopper_outside",
                     "top": "envision:block/wooden_hopper_top",
                     "bottom": "envision:block/wooden_hopper_bottom",
                     "side": "envision:block/wooden_hopper_outside",
                     "inside": "envision:block/wooden_hopper_inside"
                   },
                   "elements": [
                     {   "from": [ 0, 10, 0 ],
                       "to": [ 16, 11, 16 ],
                       "faces": {
                         "down":  { "texture": "#bottom" },
                         "up":    { "texture": "#inside", "cullface": "up" },
                         "north": { "texture": "#side", "cullface": "north" },
                         "south": { "texture": "#side", "cullface": "south" },
                         "west":  { "texture": "#side", "cullface": "west" },
                         "east":  { "texture": "#side", "cullface": "east" }
                       }
                     },
                     {   "from": [ 0, 11, 0 ],
                       "to": [ 2, 16, 16 ],
                       "faces": {
                         "up":    { "texture": "#top", "cullface": "up" },
                         "north": { "texture": "#side", "cullface": "north" },
                         "south": { "texture": "#side", "cullface": "south" },
                         "west":  { "texture": "#side", "cullface": "west" },
                         "east":  { "texture": "#side", "cullface": "up" }
                       }
                     },
                     {   "from": [ 14, 11, 0 ],
                       "to": [ 16, 16, 16 ],
                       "faces": {
                         "up":    { "texture": "#top", "cullface": "up" },
                         "north": { "texture": "#side", "cullface": "north" },
                         "south": { "texture": "#side", "cullface": "south" },
                         "west":  { "texture": "#side", "cullface": "up" },
                         "east":  { "texture": "#side", "cullface": "east" }
                       }
                     },
                     {   "from": [ 2, 11, 0 ],
                       "to": [ 14, 16, 2 ],
                       "faces": {
                         "up":    { "texture": "#top", "cullface": "up" },
                         "north": { "texture": "#side", "cullface": "north" },
                         "south": { "texture": "#side", "cullface": "up" }
                       }
                     },
                     {   "from": [ 2, 11, 14 ],
                       "to": [ 14, 16, 16 ],
                       "faces": {
                         "up":    { "texture": "#top", "cullface": "up" },
                         "north": { "texture": "#side", "cullface": "up" },
                         "south": { "texture": "#side", "cullface": "south" }
                       }
                     },
                     {   "from": [ 4, 4, 4 ],
                       "to": [ 12, 10, 12 ],
                       "faces": {
                         "down":  { "texture": "#bottom" },
                         "north": { "texture": "#side" },
                         "south": { "texture": "#side" },
                         "west":  { "texture": "#side" },
                         "east":  { "texture": "#side" }
                       }
                     },
                     {   "from": [ 6, 0, 6 ],
                       "to": [ 10, 4, 10 ],
                       "faces": {
                         "down":  { "texture": "#side", "cullface": "down", "uv": [6.0, 12.0, 10.0, 16.0]},
                         "north": { "texture": "#side" },
                         "south": { "texture": "#side" },
                         "west":  { "texture": "#side" },
                         "east":  { "texture": "#side" }
                       }
                     }
                   ]
                 }
                """;
        WOODEN_HOPPER_SIDE_FILE = """
                {
                   "ambientocclusion": false,
                   "textures": {
                     "particle": "envision:block/wooden_hopper_outside",
                     "top": "envision:block/wooden_hopper_top",
                     "bottom": "envision:block/wooden_hopper_bottom",
                     "side": "envision:block/wooden_hopper_outside",
                     "inside": "envision:block/wooden_hopper_inside"
                   },
                   "elements": [
                     {   "from": [ 0, 10, 0 ],
                       "to": [ 16, 11, 16 ],
                       "faces": {
                         "down":  { "texture": "#bottom" },
                         "up":    { "texture": "#inside", "cullface": "up" },
                         "north": { "texture": "#side", "cullface": "north" },
                         "south": { "texture": "#side", "cullface": "south" },
                         "west":  { "texture": "#side", "cullface": "west" },
                         "east":  { "texture": "#side", "cullface": "east" }
                       }
                     },
                     {   "from": [ 0, 11, 0 ],
                       "to": [ 2, 16, 16 ],
                       "faces": {
                         "up":    { "texture": "#top", "cullface": "up" },
                         "north": { "texture": "#side", "cullface": "north" },
                         "south": { "texture": "#side", "cullface": "south" },
                         "west":  { "texture": "#side", "cullface": "west" },
                         "east":  { "texture": "#side", "cullface": "up" }
                       }
                     },
                     {   "from": [ 14, 11, 0 ],
                       "to": [ 16, 16, 16 ],
                       "faces": {
                         "up":    { "texture": "#top", "cullface": "up" },
                         "north": { "texture": "#side", "cullface": "north" },
                         "south": { "texture": "#side", "cullface": "south" },
                         "west":  { "texture": "#side", "cullface": "up" },
                         "east":  { "texture": "#side", "cullface": "east" }
                       }
                     },
                     {   "from": [ 2, 11, 0 ],
                       "to": [ 14, 16, 2 ],
                       "faces": {
                         "up":    { "texture": "#top", "cullface": "up" },
                         "north": { "texture": "#side", "cullface": "north" },
                         "south": { "texture": "#side", "cullface": "up" }
                       }
                     },
                     {   "from": [ 2, 11, 14 ],
                       "to": [ 14, 16, 16 ],
                       "faces": {
                         "up":    { "texture": "#top", "cullface": "up" },
                         "north": { "texture": "#side", "cullface": "up" },
                         "south": { "texture": "#side", "cullface": "south" }
                       }
                     },
                     {   "from": [ 4, 4, 4 ],
                       "to": [ 12, 10, 12 ],
                       "faces": {
                         "down":  { "texture": "#bottom" },
                         "north": { "texture": "#side" },
                         "south": { "texture": "#side" },
                         "west":  { "texture": "#side" },
                         "east":  { "texture": "#side" }
                       }
                     },
                     {   "from": [ 6, 4, 0 ],
                       "to": [ 10, 8, 4 ],
                       "faces": {
                         "down":  { "texture": "#side", "uv": [6.0, 12.0, 10.0, 16.0]},
                         "up":    { "texture": "#side", "uv": [6.0, 12.0, 10.0, 16.0] },
                         "north": { "texture": "#side", "cullface": "north", "uv": [6.0, 12.0, 10.0, 16.0] },
                         "west":  { "texture": "#side", "uv": [6.0, 12.0, 10.0, 16.0]},
                         "east":  { "texture": "#side", "uv": [6.0, 12.0, 10.0, 16.0]}
                       }
                     }
                   ]
                 }
                """;
    }

    public EnvisionSimpleJsonProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateSimpleJsons(SimpleJsonBuilder simpleJsonBuilder) {
        generateWorldPreset(simpleJsonBuilder, FILLED_WORLD_FILE, EnvisionWorlds.FILLED_WORLD);
        generateWorldPreset(simpleJsonBuilder, SKYBLOCK_FILE, EnvisionWorlds.SKYBLOCK);
        generateNormalFile(simpleJsonBuilder);
        generateWoodenHopperFile(simpleJsonBuilder);
    }

    private void generateWorldPreset(SimpleJsonBuilder simpleJsonBuilder, String file, EnvisionWorlds.EnvisionWorld world) {
        simpleJsonBuilder.add(file, "worldgen/world_preset", world.worldPreset(), true);
    }

    private void generateNormalFile(SimpleJsonBuilder simpleJsonBuilder) {
        ArrayList<String> worlds = new ArrayList<>();
        EnvisionWorlds.ENVISION_WORLDS.forEach(envisionWorld -> worlds.add(envisionWorld.worldPreset().toString()));

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("replace", false);
        jsonObject.add("values", GSON.toJsonTree(worlds));

        simpleJsonBuilder.add(jsonObject, "tags/worldgen/world_preset", new Identifier("normal"), true);

    }

    private void generateWoodenHopperFile(SimpleJsonBuilder simpleJsonBuilder) {
        simpleJsonBuilder.add(WOODEN_HOPPER_FILE, "models/block", Envision.id("wooden_hopper"), false);
        simpleJsonBuilder.add(WOODEN_HOPPER_SIDE_FILE, "models/block", Envision.id("wooden_hopper_side"), false);
    }
}
