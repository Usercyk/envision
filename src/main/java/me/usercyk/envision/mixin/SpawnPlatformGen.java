package me.usercyk.envision.mixin;


import me.usercyk.envision.worldgen.chunkgen.FilledChunkGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.level.ServerWorldProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class SpawnPlatformGen {
    @Inject(method = "setupSpawn", at = @At(value = "TAIL"))
    private static void generateSpawnPlatform(ServerWorld world,
                                              ServerWorldProperties worldProperties,
                                              boolean bonusChest,
                                              boolean debugWorld,
                                              CallbackInfo ci) {
        if (world.getChunkManager().getChunkGenerator() instanceof FilledChunkGenerator) {

            int spawnX = worldProperties.getSpawnX();
            int spawnY = worldProperties.getSpawnY();
            int spawnZ = worldProperties.getSpawnZ();

            BlockState grassBlock = Blocks.GRASS_BLOCK.getDefaultState();
            BlockState air = Blocks.AIR.getDefaultState();

            BlockPos.iterate(spawnX - 2, spawnY - 2, spawnZ - 2,
                    spawnX + 3, spawnY + 3, spawnZ + 3).forEach(
                    pos -> world.setBlockState(pos, air)
            );

            BlockPos.iterate(spawnX - 2, spawnY - 3, spawnZ - 2,
                    spawnX + 3, spawnY - 3, spawnZ + 3).forEach(
                    pos -> world.setBlockState(pos, grassBlock)
            );
        }
    }
}
