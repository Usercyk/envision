package me.usercyk.envision.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.usercyk.envision.worldgen.chunkgen.FilledChunkGenerator;
import net.minecraft.server.network.SpawnLocating;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SpawnLocating.class)
public class CheckCeiling {
    @ModifyVariable(method = "findOverworldSpawn", at = @At("STORE"))
    private static boolean hasCeiling(boolean bl, @Local(argsOnly = true) ServerWorld world) {
        if (world.getChunkManager().getChunkGenerator() instanceof FilledChunkGenerator) return true;
        return bl;
    }
}
