package me.usercyk.envision.mixin;

import me.usercyk.envision.item.custom.ShardItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class canBreakIntoShards {
    @Inject(method = "canHarvest", at = @At("HEAD"), cancellable = true)
    public void canHarvest(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.isIn(ShardItem.HAS_SHARDS)) {
            cir.setReturnValue(true);
        }
    }
}
