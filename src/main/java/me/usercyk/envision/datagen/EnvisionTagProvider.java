package me.usercyk.envision.datagen;

import me.usercyk.envision.item.custom.RummageToolItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class EnvisionTagProvider extends FabricTagProvider.BlockTagProvider {
    public EnvisionTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
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
    }
}
