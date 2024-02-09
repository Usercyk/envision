package me.usercyk.envision.item;

import me.usercyk.envision.Envision;
import me.usercyk.envision.block.EnvisionBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


public class EnvisionItemGroup {
    public static final Identifier ENVISION_GROUP_ID = Envision.id("envision_group");
    @SuppressWarnings("unused")
    public static final ItemGroup ENVISION_GROUP = Registry.register(
            Registries.ITEM_GROUP,
            ENVISION_GROUP_ID,
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(EnvisionItems.STONE_SHARD))
                    .displayName(Text.translatable(ENVISION_GROUP_ID.toTranslationKey("itemGroup")))
                    .entries((displayContext, entries) -> {
                        EnvisionItems.SHARDS.forEach(entries::add);
                        EnvisionItems.RUMMAGE_TOOLS.forEach(entries::add);
                        entries.add(EnvisionBlocks.WOODEN_HOPPER);
                    })
                    .build()
    );

    public static void register() {
        Envision.LOGGER.info("Registering item group for " + Envision.MOD_ID);
    }
}
