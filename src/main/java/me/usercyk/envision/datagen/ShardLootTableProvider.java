package me.usercyk.envision.datagen;

import me.usercyk.envision.item.EnvisionItems;
import me.usercyk.envision.item.custom.ShardItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemConvertible;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.tag.ItemTags;

public class ShardLootTableProvider extends FabricBlockLootTableProvider {

    private static final LootCondition.Builder WITH_PICKAXE = MatchToolLootCondition.builder(
            ItemPredicate.Builder.create().tag(ItemTags.PICKAXES)
    );

    public ShardLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    private LootTable.Builder dropShards(Block block, ItemConvertible shard) {
        return LootTable.builder().pool(
                LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(ItemEntry.builder(block).conditionally(WITH_PICKAXE)
                                .alternatively(this.addSurvivesExplosionCondition(block, ItemEntry.builder(shard))))
        ).randomSequenceId(block.getLootTableId());
    }

    private LootTable.Builder dropShardsWithSilkTouch(Block dropWithSilkTouch, Block drop, ItemConvertible shard) {
        return LootTable.builder().pool(
                LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(ItemEntry.builder(dropWithSilkTouch).conditionally(WITH_SILK_TOUCH)
                                .alternatively(ItemEntry.builder(drop).conditionally(WITH_PICKAXE))
                                .alternatively(this.addSurvivesExplosionCondition(dropWithSilkTouch, ItemEntry.builder(shard))))
        ).randomSequenceId(dropWithSilkTouch.getLootTableId());
    }

    @Override
    public void generate() {
        this.addBlockDropShardWithSilkTouch(Blocks.STONE, Blocks.COBBLESTONE, EnvisionItems.COBBLESTONE_SHARD);
        this.addBlockDropShardWithSilkTouch(Blocks.DEEPSLATE, Blocks.COBBLED_DEEPSLATE, EnvisionItems.COBBLED_DEEPSLATE_SHARD);
        this.addBlockDropShard(EnvisionItems.COBBLESTONE_SHARD);
        this.addBlockDropShard(EnvisionItems.NETHERRACK_SHARD);
        this.addBlockDropShard(EnvisionItems.END_STONE_SHARD);
        this.addBlockDropShard(EnvisionItems.GRANITE_SHARD);
        this.addBlockDropShard(EnvisionItems.DIORITE_SHARD);
        this.addBlockDropShard(EnvisionItems.ANDESITE_SHARD);
        this.addBlockDropShard(EnvisionItems.BASALT_SHARD);
        this.addBlockDropShard(EnvisionItems.TUFF_SHARD);
        this.addBlockDropShard(EnvisionItems.BLACKSTONE_SHARD);
        this.addBlockDropShard(EnvisionItems.COBBLED_DEEPSLATE_SHARD);
    }

    private void addBlockDropShard(ShardItem shardItem) {
        addBlockDropShard(shardItem.getEntireBlock(), shardItem);
    }

    private void addBlockDropShard(Block block, ItemConvertible shard) {
        this.addDrop(block, block1 -> this.dropShards(block1, shard));
    }

    private void addBlockDropShardWithSilkTouch(Block dropWithSilkTouch, Block drop, ItemConvertible shard) {
        this.addDrop(dropWithSilkTouch, block -> this.dropShardsWithSilkTouch(block, drop, shard));
    }

    @Override
    public String getName() {
        return "Envision Shard Loot Table Provider";
    }
}
