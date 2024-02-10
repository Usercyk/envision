package me.usercyk.envision.mixin;

import me.usercyk.envision.block.EnvisionBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedstoneWireBlock.class)
public class canRunOnWoodenHopper {
    @Inject(method = "canRunOnTop", at = @At("HEAD"), cancellable = true)
    private void canRunOnWoodenHopperTop(BlockView world, BlockPos pos, BlockState floor, CallbackInfoReturnable<Boolean> cir) {
        if (floor.isOf(EnvisionBlocks.WOODEN_HOPPER)) cir.setReturnValue(true);
    }
}
