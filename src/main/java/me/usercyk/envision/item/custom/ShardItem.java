package me.usercyk.envision.item.custom;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ShardItem extends Item {
    private final Block entireBlock;

    public ShardItem(Block entireBlock) {
        super(new FabricItemSettings());
        this.entireBlock = entireBlock;
    }

    public Block getEntireBlock() {
        return entireBlock;
    }
}
