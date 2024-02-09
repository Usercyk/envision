package me.usercyk.envision.datagen;

import me.usercyk.envision.block.EnvisionBlocks;
import me.usercyk.envision.block.entity.WoodenHopperBlockEntity;
import me.usercyk.envision.item.EnvisionItemGroup;
import me.usercyk.envision.item.EnvisionItems;
import me.usercyk.envision.worldgen.EnvisionWorlds;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class EnvisionEnglishProvider extends FabricLanguageProvider {
    public EnvisionEnglishProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(EnvisionWorlds.FILLED_WORLD.getTransKey(), "Filled World");
        translationBuilder.add(EnvisionWorlds.SKYBLOCK.getTransKey(), "Skyblock");

        translationBuilder.add(EnvisionItems.STONE_SHARD, "Stone Shard");
        translationBuilder.add(EnvisionItems.END_STONE_SHARD, "End Stone Shard");
        translationBuilder.add(EnvisionItems.NETHERRACK_SHARD, "Netherrack Shard");
        translationBuilder.add(EnvisionItems.COBBLESTONE_SHARD, "Cobblestone Shard");
        translationBuilder.add(EnvisionItems.GRANITE_SHARD, "Granite Shard");
        translationBuilder.add(EnvisionItems.DIORITE_SHARD, "Diorite Shard");
        translationBuilder.add(EnvisionItems.ANDESITE_SHARD, "Andesite Shard");
        translationBuilder.add(EnvisionItems.TUFF_SHARD, "Tuff Shard");
        translationBuilder.add(EnvisionItems.DEEPSLATE_SHARD, "Deepslate Shard");
        translationBuilder.add(EnvisionItems.BASALT_SHARD, "Basalt Shard");
        translationBuilder.add(EnvisionItems.BLACKSTONE_SHARD, "Blackstone Shard");
        translationBuilder.add(EnvisionItems.COBBLED_DEEPSLATE_SHARD, "Cobbled Deepslate Shard");

        translationBuilder.add(EnvisionItems.WOODEN_RUMMAGE_TOOL, "Wooden Rummage Tool");
        translationBuilder.add(EnvisionItems.STONE_RUMMAGE_TOOL, "Stone Rummage Tool");
        translationBuilder.add(EnvisionItems.GOLDEN_RUMMAGE_TOOL, "Golden Rummage Tool");
        translationBuilder.add(EnvisionItems.IRON_RUMMAGE_TOOL, "Iron Rummage Tool");
        translationBuilder.add(EnvisionItems.DIAMOND_RUMMAGE_TOOL, "Diamond Rummage Tool");
        translationBuilder.add(EnvisionItems.NETHERITE_RUMMAGE_TOOL, "Netherite Rummage Tool");

        translationBuilder.add(EnvisionItemGroup.ENVISION_GROUP_ID.toTranslationKey("itemGroup"), "Envision");

        translationBuilder.add(EnvisionBlocks.WOODEN_HOPPER, "Wooden Hopper");
        translationBuilder.add(WoodenHopperBlockEntity.CONTAINER_TRANS_KEY, "Wooden Hopper");
    }
}
