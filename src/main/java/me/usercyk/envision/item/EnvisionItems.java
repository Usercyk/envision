package me.usercyk.envision.item;

import me.usercyk.envision.Envision;
import me.usercyk.envision.item.custom.RummageToolItem;
import me.usercyk.envision.item.custom.ShardItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class EnvisionItems {

    public static final ArrayList<ShardItem> SHARDS = new ArrayList<>();
    public static final ArrayList<RummageToolItem> RUMMAGE_TOOLS = new ArrayList<>();

    public static final ShardItem ANDESITE_SHARD = registerShardItem("andesite");
    public static final ShardItem BASALT_SHARD = registerShardItem("basalt");
    public static final ShardItem BLACKSTONE_SHARD = registerShardItem("blackstone");
    public static final ShardItem COBBLESTONE_SHARD = registerShardItem("cobblestone");
    public static final ShardItem COBBLED_DEEPSLATE_SHARD = registerShardItem("cobbled_deepslate");
    public static final ShardItem DEEPSLATE_SHARD = registerShardItem("deepslate");
    public static final ShardItem DIORITE_SHARD = registerShardItem("diorite");
    public static final ShardItem END_STONE_SHARD = registerShardItem("end_stone");
    public static final ShardItem GRANITE_SHARD = registerShardItem("granite");
    public static final ShardItem NETHERRACK_SHARD = registerShardItem("netherrack");
    public static final ShardItem STONE_SHARD = registerShardItem("stone");
    public static final ShardItem TUFF_SHARD = registerShardItem("tuff");

    public static final RummageToolItem WOODEN_RUMMAGE_TOOL = registerRummageTool("wooden_rummage_tool", ToolMaterials.WOOD);
    public static final RummageToolItem STONE_RUMMAGE_TOOL = registerRummageTool("stone_rummage_tool", ToolMaterials.STONE);
    public static final RummageToolItem IRON_RUMMAGE_TOOL = registerRummageTool("iron_rummage_tool", ToolMaterials.IRON);
    public static final RummageToolItem GOLDEN_RUMMAGE_TOOL = registerRummageTool("golden_rummage_tool", ToolMaterials.GOLD);
    public static final RummageToolItem DIAMOND_RUMMAGE_TOOL = registerRummageTool("diamond_rummage_tool", ToolMaterials.DIAMOND);
    public static final RummageToolItem NETHERITE_RUMMAGE_TOOL = registerRummageTool("netherite_rummage_tool", ToolMaterials.NETHERITE, new FabricItemSettings().fireproof());


    private static <T extends Item> T registerItem(String name, T item) {
        return Registry.register(Registries.ITEM, Envision.id(name), item);
    }

    private static <T extends Item> T registerItem(String name, T item, ArrayList<T> group) {
        T registered = registerItem(name, item);
        group.add(registered);
        return registered;
    }

    private static ShardItem registerShardItem(String entireBlockName) {
        return registerItem(entireBlockName + "_shard",
                new ShardItem(Registries.BLOCK.get(new Identifier(entireBlockName))),
                SHARDS);
    }

    private static RummageToolItem registerRummageTool(String name, ToolMaterial material, Item.Settings settings) {
        if (settings == null) settings = new FabricItemSettings();
        return registerItem(name, new RummageToolItem(material, settings), RUMMAGE_TOOLS);
    }

    private static RummageToolItem registerRummageTool(String name, ToolMaterial material) {
        return registerRummageTool(name, material, null);
    }


    public static void register() {
        Envision.LOGGER.info("Registering items for " + Envision.MOD_ID);
    }
}
