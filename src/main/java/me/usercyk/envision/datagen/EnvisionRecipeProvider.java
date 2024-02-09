package me.usercyk.envision.datagen;

import me.usercyk.envision.item.EnvisionItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.recipe.book.RecipeCategory;

public class EnvisionRecipeProvider extends FabricRecipeProvider {
    public EnvisionRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        EnvisionItems.SHARDS.forEach(
                shardItem -> offer2x2CompactingRecipe(exporter,
                        RecipeCategory.BUILDING_BLOCKS,
                        shardItem.getEntireBlock(),
                        shardItem)
        );
    }
}
