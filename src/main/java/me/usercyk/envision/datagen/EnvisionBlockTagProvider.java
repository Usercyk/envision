package me.usercyk.envision.datagen;

import me.usercyk.envision.item.EnvisionItems;
import me.usercyk.envision.item.custom.RummageToolItem;
import me.usercyk.envision.item.custom.ShardItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class EnvisionBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public EnvisionBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(RummageToolItem.RummageManager.RUMMAGE_ABLE)
                .add(Blocks.GRASS_BLOCK)
                .add(Blocks.SAND)
                .add(Blocks.GRAVEL)
                .add(Blocks.SOUL_SAND)
                .add(Blocks.SOUL_SOIL);
        getOrCreateTagBuilder(ShardItem.HAS_SHARDS)
                .add(EnvisionItems.SHARDS.stream().map(ShardItem::getEntireBlock).toList().toArray(new Block[]{}));
    }
}
