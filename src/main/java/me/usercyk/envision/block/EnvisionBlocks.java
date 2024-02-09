package me.usercyk.envision.block;

import me.usercyk.envision.Envision;
import me.usercyk.envision.block.custom.WoodenHopperBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class EnvisionBlocks {

    public static final WoodenHopperBlock WOODEN_HOPPER = registerBlock("wooden_hopper", new WoodenHopperBlock());

    @SuppressWarnings("SameParameterValue")
    private static <T extends Block> T registerBlock(String name, T block) {
        Registry.register(Registries.ITEM, Envision.id(name), new BlockItem(block, new FabricItemSettings()));
        return Registry.register(Registries.BLOCK, Envision.id(name), block);
    }

    public static void register() {
        Envision.LOGGER.info("Registering blocks for " + Envision.MOD_ID);
        EnvisionBlockEntityTypes.register();
    }
}
