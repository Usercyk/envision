package me.usercyk.envision.datagen;

import me.usercyk.envision.block.EnvisionBlocks;
import me.usercyk.envision.item.EnvisionItems;
import me.usercyk.envision.item.custom.RummageToolItem;
import me.usercyk.envision.item.custom.ShardItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

public class EnvisionRecipeProvider extends FabricRecipeProvider {
    public EnvisionRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        EnvisionItems.SHARDS.forEach(item -> offerPieceRecipe(exporter, item));
        offerRummageToolsRecipe(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, EnvisionBlocks.WOODEN_HOPPER)
                .pattern("x x")
                .pattern("xsx")
                .pattern(" x ")
                .input('s', Items.CHEST)
                .input('x', ItemTags.PLANKS)
                .criterion("has_tag", FabricRecipeProvider.conditionsFromTag(ItemTags.PLANKS))
                .offerTo(exporter);
    }

    private void offerPieceRecipe(RecipeExporter exporter, ShardItem shardItem) {
        RecipeProvider.offer2x2CompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, shardItem.getEntireBlock(), shardItem);
    }

    private void offerRummageToolRecipe(RecipeExporter exporter, RummageToolItem item, ItemConvertible ingredient) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, item)
                .pattern("  x")
                .pattern(" s ")
                .pattern("s  ")
                .input('s', Items.STICK)
                .input('x', ingredient)
                .criterion(FabricRecipeProvider.hasItem(ingredient),
                        FabricRecipeProvider.conditionsFromItem(ingredient))
                .offerTo(exporter);
    }

    private void offerRummageToolRecipe(RecipeExporter exporter, RummageToolItem item, TagKey<Item> tag) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, item)
                .pattern("  x")
                .pattern(" s ")
                .pattern("s  ")
                .input('s', Items.STICK)
                .input('x', tag)
                .criterion("has_tag", FabricRecipeProvider.conditionsFromTag(tag))
                .offerTo(exporter);
    }

    private void offerRummageToolsRecipe(RecipeExporter exporter) {
        offerRummageToolRecipe(exporter, EnvisionItems.WOODEN_RUMMAGE_TOOL, ItemTags.PLANKS);
        offerRummageToolRecipe(exporter, EnvisionItems.STONE_RUMMAGE_TOOL, ItemTags.STONE_TOOL_MATERIALS);
        offerRummageToolRecipe(exporter, EnvisionItems.IRON_RUMMAGE_TOOL, Items.IRON_INGOT);
        offerRummageToolRecipe(exporter, EnvisionItems.GOLDEN_RUMMAGE_TOOL, Items.GOLD_INGOT);
        offerRummageToolRecipe(exporter, EnvisionItems.DIAMOND_RUMMAGE_TOOL, Items.DIAMOND);
        RecipeProvider.offerNetheriteUpgradeRecipe(exporter, EnvisionItems.DIAMOND_RUMMAGE_TOOL, RecipeCategory.TOOLS, EnvisionItems.NETHERITE_RUMMAGE_TOOL);
    }
}
