package me.usercyk.envision.datagen;

import me.usercyk.envision.datagen.custom.AbstractRummageLootTableProvider;
import me.usercyk.envision.item.EnvisionItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;

public class EnvisionRummageLootProvider extends AbstractRummageLootTableProvider {
    public EnvisionRummageLootProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        this.addDrop(
                Blocks.GRASS_BLOCK,
                LootTable.builder().pool(
                        LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                                .with(ItemEntry.builder(EnvisionItems.COBBLESTONE_SHARD).weight(20))
                                .with(ItemEntry.builder(EnvisionItems.COBBLED_DEEPSLATE_SHARD).weight(15))
                                .with(ItemEntry.builder(EnvisionItems.DIORITE_SHARD).weight(15))
                                .with(ItemEntry.builder(EnvisionItems.ANDESITE_SHARD).weight(15))
                                .with(ItemEntry.builder(EnvisionItems.GRANITE_SHARD).weight(15))
                                .with(ItemEntry.builder(EnvisionItems.TUFF_SHARD).weight(15))
                                .conditionally(new RandomChanceWithLootingLootCondition(0.025F, 0.001F))
                )
        );
        this.addDrop(
                Blocks.GRAVEL,
                LootTable.builder().pool(
                        LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                                .with(ItemEntry.builder(EnvisionItems.STONE_SHARD).weight(20))
                                .with(ItemEntry.builder(EnvisionItems.DEEPSLATE_SHARD).weight(15))
                                .with(ItemEntry.builder(EnvisionItems.DIORITE_SHARD).weight(15))
                                .with(ItemEntry.builder(EnvisionItems.ANDESITE_SHARD).weight(15))
                                .with(ItemEntry.builder(EnvisionItems.GRANITE_SHARD).weight(15))
                                .with(ItemEntry.builder(EnvisionItems.TUFF_SHARD).weight(15))
                                .conditionally(new RandomChanceWithLootingLootCondition(0.025F, 0.001F))
                )
        );
        this.addDrop(
                Blocks.SAND,
                LootTable.builder()
                        .pool(
                                LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                                        .with(ItemEntry.builder(EnvisionItems.STONE_SHARD).weight(20))
                                        .with(ItemEntry.builder(EnvisionItems.DEEPSLATE_SHARD).weight(15))
                        )
                        .pool(
                                LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                                        .with(ItemEntry.builder(EnvisionItems.NETHERRACK_SHARD).weight(15))
                                        .with(ItemEntry.builder(EnvisionItems.BASALT_SHARD).weight(15))
                                        .with(ItemEntry.builder(EnvisionItems.BLACKSTONE_SHARD).weight(15))
                                        .conditionally(new RandomChanceWithLootingLootCondition(0.025F, 0.001F))
                        ));
        this.addDrop(
                Blocks.SOUL_SAND,
                LootTable.builder()
                        .pool(
                                LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                                        .with(ItemEntry.builder(EnvisionItems.NETHERRACK_SHARD).weight(15))
                                        .with(ItemEntry.builder(EnvisionItems.BASALT_SHARD).weight(15))
                                        .with(ItemEntry.builder(EnvisionItems.BLACKSTONE_SHARD).weight(15))
                        )
                        .pool(
                                LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                                        .with(ItemEntry.builder(EnvisionItems.END_STONE_SHARD))
                                        .conditionally(new RandomChanceWithLootingLootCondition(0.025F, 0.001F))
                        )
        );
        this.addDrop(
                Blocks.SOUL_SOIL,
                LootTable.builder()
                        .pool(
                                LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                                        .with(ItemEntry.builder(EnvisionItems.NETHERRACK_SHARD).weight(15))
                                        .with(ItemEntry.builder(EnvisionItems.BASALT_SHARD).weight(15))
                                        .with(ItemEntry.builder(EnvisionItems.BLACKSTONE_SHARD).weight(15))
                        )
                        .pool(
                                LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                                        .with(ItemEntry.builder(EnvisionItems.END_STONE_SHARD))
                                        .conditionally(new RandomChanceWithLootingLootCondition(0.025F, 0.001F))
                        )
        );
    }
}
