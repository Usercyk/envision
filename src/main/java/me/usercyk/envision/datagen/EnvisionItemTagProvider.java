package me.usercyk.envision.datagen;

import me.usercyk.envision.item.EnvisionItems;
import me.usercyk.envision.item.custom.ShardItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class EnvisionItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public EnvisionItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture, null);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ShardItem.SHARDS)
                .add(EnvisionItems.SHARDS.toArray(new Item[]{}));
    }
}
