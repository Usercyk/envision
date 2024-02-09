package me.usercyk.envision.datagen;

import me.usercyk.envision.block.EnvisionBlocks;
import me.usercyk.envision.item.EnvisionItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class EnvisionModelProvider extends FabricModelProvider {
    public EnvisionModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        registerWoodenHopper(blockStateModelGenerator);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        EnvisionItems.SHARDS.forEach(
                shardItem -> itemModelGenerator.register(shardItem, Models.GENERATED)
        );
        EnvisionItems.RUMMAGE_TOOLS.forEach(
                rummageTool -> itemModelGenerator.register(rummageTool, Models.GENERATED)
        );
    }

    private void registerWoodenHopper(BlockStateModelGenerator blockStateModelGenerator) {
        Identifier identifier = ModelIds.getBlockModelId(EnvisionBlocks.WOODEN_HOPPER);
        Identifier identifier2 = ModelIds.getBlockSubModelId(EnvisionBlocks.WOODEN_HOPPER, "_side");
        blockStateModelGenerator.registerItemModel(EnvisionBlocks.WOODEN_HOPPER.asItem());
        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(EnvisionBlocks.WOODEN_HOPPER)
                        .coordinate(
                                BlockStateVariantMap.create(Properties.HOPPER_FACING)
                                        .register(Direction.DOWN, BlockStateVariant.create().put(VariantSettings.MODEL, identifier))
                                        .register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, identifier2))
                                        .register(
                                                Direction.EAST,
                                                BlockStateVariant.create()
                                                        .put(VariantSettings.MODEL, identifier2)
                                                        .put(VariantSettings.Y, VariantSettings.Rotation.R90))
                                        .register(
                                                Direction.SOUTH,
                                                BlockStateVariant.create()
                                                        .put(VariantSettings.MODEL, identifier2)
                                                        .put(VariantSettings.Y, VariantSettings.Rotation.R180))
                                        .register(
                                                Direction.WEST,
                                                BlockStateVariant.create()
                                                        .put(VariantSettings.MODEL, identifier2)
                                                        .put(VariantSettings.Y, VariantSettings.Rotation.R270)))
        );
    }
}
