package me.usercyk.envision.worldgen.chunkgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.Blender;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.noise.NoiseConfig;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class FilledChunkGenerator extends NoiseChunkGenerator {
    public static final Codec<FilledChunkGenerator> CODEC = RecordCodecBuilder.create(
            (instance) -> instance.group(
                    BiomeSource.CODEC
                            .fieldOf("biome_source")
                            .forGetter(FilledChunkGenerator::getBiomeSource),
                    ChunkGeneratorSettings.REGISTRY_CODEC
                            .fieldOf("settings")
                            .forGetter(FilledChunkGenerator::getSettings),
                    Registries.BLOCK.getCodec()
                            .fieldOf("filling_block")
                            .forGetter(FilledChunkGenerator::getFillingBlock)
            ).apply(instance, instance.stable(FilledChunkGenerator::new)));
    private final Block fillingBlock;

    public FilledChunkGenerator(BiomeSource biomeSource, RegistryEntry<ChunkGeneratorSettings> settings, Block fillingBlock) {
        super(biomeSource, settings);
        this.fillingBlock = fillingBlock;
    }

    public Block getFillingBlock() {
        return fillingBlock;
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int startX = chunk.getPos().getStartX();
        int startZ = chunk.getPos().getStartZ();
        BlockState blockState = this.fillingBlock.getDefaultState();

        BlockPos.iterate(startX, -64, startZ, startX + 15, 319, startZ + 15).forEach(
                blockPos -> chunk.setBlockState(blockPos, blockState, false)
        );
        BlockPos.iterate(startX, -64, startZ, startX + 15, -64, startZ + 15).forEach(
                blockPos -> {
                    chunk.setBlockState(mutable.set(blockPos.getX(), 319, blockPos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
                    chunk.setBlockState(mutable.set(blockPos.getX(), -64, blockPos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
                }
        );
    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk) {
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public void carve(ChunkRegion chunkRegion, long seed, NoiseConfig noiseConfig, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk, GenerationStep.Carver carverStep) {
    }

    @Override
    public void populateEntities(ChunkRegion region) {
    }

    @Override
    public void generateFeatures(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor) {
    }
}
