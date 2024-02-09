package me.usercyk.envision.datagen;

import me.usercyk.envision.block.EnvisionBlocks;
import me.usercyk.envision.block.entity.WoodenHopperBlockEntity;
import me.usercyk.envision.item.EnvisionItemGroup;
import me.usercyk.envision.item.EnvisionItems;
import me.usercyk.envision.worldgen.EnvisionWorlds;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class EnvisionChineseProvider extends FabricLanguageProvider {
    public EnvisionChineseProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "zh_cn");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(EnvisionWorlds.FILLED_WORLD.getTransKey(), "填充世界");
        translationBuilder.add(EnvisionWorlds.SKYBLOCK.getTransKey(), "空岛");

        translationBuilder.add(EnvisionItems.STONE_SHARD, "石头碎片");
        translationBuilder.add(EnvisionItems.END_STONE_SHARD, "末地石碎片");
        translationBuilder.add(EnvisionItems.NETHERRACK_SHARD, "下界岩碎片");
        translationBuilder.add(EnvisionItems.COBBLESTONE_SHARD, "圆石碎片");
        translationBuilder.add(EnvisionItems.GRANITE_SHARD, "花岗岩碎片");
        translationBuilder.add(EnvisionItems.DIORITE_SHARD, "闪长岩碎片");
        translationBuilder.add(EnvisionItems.ANDESITE_SHARD, "安山岩碎片");
        translationBuilder.add(EnvisionItems.TUFF_SHARD, "凝灰岩碎片");
        translationBuilder.add(EnvisionItems.DEEPSLATE_SHARD, "深板岩碎片");
        translationBuilder.add(EnvisionItems.BASALT_SHARD, "玄武岩碎片");
        translationBuilder.add(EnvisionItems.BLACKSTONE_SHARD, "黑石碎片");
        translationBuilder.add(EnvisionItems.COBBLED_DEEPSLATE_SHARD, "深板岩圆石碎片");

        translationBuilder.add(EnvisionItems.WOODEN_RUMMAGE_TOOL, "木翻找器");
        translationBuilder.add(EnvisionItems.STONE_RUMMAGE_TOOL, "石翻找器");
        translationBuilder.add(EnvisionItems.GOLDEN_RUMMAGE_TOOL, "金翻找器");
        translationBuilder.add(EnvisionItems.IRON_RUMMAGE_TOOL, "铁翻找器");
        translationBuilder.add(EnvisionItems.DIAMOND_RUMMAGE_TOOL, "钻石翻找器");
        translationBuilder.add(EnvisionItems.NETHERITE_RUMMAGE_TOOL, "下界合金翻找器");

        translationBuilder.add(EnvisionItemGroup.ENVISION_GROUP_ID.toTranslationKey("itemGroup"), "视界·期望");

        translationBuilder.add(EnvisionBlocks.WOODEN_HOPPER, "木漏斗");
        translationBuilder.add(WoodenHopperBlockEntity.CONTAINER_TRANS_KEY, "木漏斗");
    }
}
