package me.usercyk.envision.item.custom;

import me.usercyk.envision.Envision;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ShardItem extends Item {

    public static final TagKey<Block> HAS_SHARDS = TagKey.of(RegistryKeys.BLOCK, Envision.id("has_shards"));
    public static final TagKey<Item> SHARDS = TagKey.of(RegistryKeys.ITEM, Envision.id("shards"));

    private final Block entireBlock;

    public ShardItem(Block entireBlock) {
        super(new FabricItemSettings());
        this.entireBlock = entireBlock;
    }

    public Block getEntireBlock() {
        return entireBlock;
    }
}
