package me.usercyk.envision.datagen.custom;

import com.google.common.collect.Sets;
import me.usercyk.envision.item.custom.RummageToolItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.impl.datagen.loot.FabricLootTableProviderImpl;
import net.minecraft.block.Block;
import net.minecraft.data.DataWriter;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class AbstractRummageLootTableProvider extends FabricBlockLootTableProvider {
    private final FabricDataOutput output;
    private final Set<Identifier> excludedFromStrictValidation = new HashSet<>();

    protected AbstractRummageLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
        this.output = dataOutput;
    }

    @Override
    public abstract void generate();

    public void excludeFromStrictValidation(Block block) {
        excludedFromStrictValidation.add(Registries.BLOCK.getId(block));
    }

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> biConsumer) {
        generate();

        for (Map.Entry<Identifier, LootTable.Builder> entry : lootTables.entrySet()) {
            Identifier identifier = entry.getKey();

            if (identifier.equals(LootTables.EMPTY)) {
                continue;
            }

            biConsumer.accept(identifier, entry.getValue());
        }

        if (output.isStrictValidationEnabled()) {
            Set<Identifier> missing = Sets.newHashSet();

            for (Identifier blockId : Registries.BLOCK.getIds()) {
                if (blockId.getNamespace().equals(output.getModId())) {
                    Identifier blockLootTableId = RummageToolItem.RummageManager.getRummageLootID(Registries.BLOCK.get(blockId));

                    if (blockLootTableId.getNamespace().equals(output.getModId())) {
                        if (!lootTables.containsKey(blockLootTableId)) {
                            missing.add(blockId);
                        }
                    }
                }
            }

            missing.removeAll(excludedFromStrictValidation);

            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing loot table(s) for %s".formatted(missing));
            }
        }
    }


    @Override
    public void addDrop(Block block, LootTable.Builder lootTable) {
        this.lootTables.put(RummageToolItem.RummageManager.getRummageLootID(block), lootTable);
    }

    @Override
    public String getName() {
        return "Envision Rummage Loot Table";
    }


    @SuppressWarnings("UnstableApiUsage")
    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return FabricLootTableProviderImpl.run(writer, this, RummageToolItem.RummageManager.RUMMAGE, output);
    }
}
