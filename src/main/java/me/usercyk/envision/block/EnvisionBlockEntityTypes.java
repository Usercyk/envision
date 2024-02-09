package me.usercyk.envision.block;

import me.usercyk.envision.Envision;
import me.usercyk.envision.block.entity.WoodenHopperBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class EnvisionBlockEntityTypes {

    public static final BlockEntityType<WoodenHopperBlockEntity> WOODEN_HOPPER_ENTITY_TYPE = registerBlockEntity("wooden_hopper", WoodenHopperBlockEntity::new, EnvisionBlocks.WOODEN_HOPPER);

    @SuppressWarnings("SameParameterValue")
    private static <T extends BlockEntity, R extends Block> BlockEntityType<T> registerBlockEntity(String name, FabricBlockEntityTypeBuilder.Factory<T> factory, R block) {
        return Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Envision.id(name),
                FabricBlockEntityTypeBuilder.create(factory, block).build());
    }

    public static void register() {
        Envision.LOGGER.info("Registering Block Entities for " + Envision.MOD_ID);
    }
}
