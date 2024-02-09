package me.usercyk.envision.worldgen;

import com.mojang.serialization.Codec;
import me.usercyk.envision.Envision;
import me.usercyk.envision.worldgen.chunkgen.FilledChunkGenerator;
import me.usercyk.envision.worldgen.chunkgen.SkyblockChunkGenerator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.ArrayList;

public class EnvisionWorlds {

    public static final ArrayList<EnvisionWorld> ENVISION_WORLDS = new ArrayList<>();

    public static final EnvisionWorld FILLED_WORLD =
            registerWorld("filled_chunk", FilledChunkGenerator.CODEC, "filled_world");

    public static final EnvisionWorld SKYBLOCK =
            registerWorld("skyblock_chunk", SkyblockChunkGenerator.CODEC, "skyblock");

    @SuppressWarnings("SameParameterValue")
    private static EnvisionWorld registerWorld(String chunkGenName, Codec<? extends ChunkGenerator> codec, String worldName) {
        EnvisionWorld world = new EnvisionWorld(registerChunkGenerator(chunkGenName, codec), registerWorldPreset(worldName));
        ENVISION_WORLDS.add(world);
        return world;
    }

    private static Identifier registerChunkGenerator(String chunkGenName, Codec<? extends ChunkGenerator> codec) {
        Identifier chunkGenID = Envision.id(chunkGenName);
        Registry.register(Registries.CHUNK_GENERATOR, chunkGenID, codec);
        return chunkGenID;
    }

    private static Identifier registerWorldPreset(String worldName) {
        Identifier worldID = Envision.id(worldName);
        RegistryKey.of(RegistryKeys.WORLD_PRESET, worldID);
        return worldID;
    }

    public static void register() {
        Envision.LOGGER.info("Registering worlds for " + Envision.MOD_ID);
    }

    public record EnvisionWorld(Identifier chunkGenerator, Identifier worldPreset) {
        public String getTransKey() {
            return this.worldPreset.toTranslationKey("generator");
        }
    }
}
