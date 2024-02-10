package me.usercyk.envision.item.custom;

import me.usercyk.envision.Envision;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

public class RummageToolItem extends ToolItem {
    public RummageToolItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return state.isIn(RummageManager.RUMMAGE_ABLE);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState state = world.getBlockState(blockPos);
        if (!this.isSuitableFor(state)) return ActionResult.PASS;
        if (!world.isClient()) {
            PlayerEntity player = context.getPlayer();
            if (player != null) {
                rummage((ServerWorld) world, blockPos);
                context.getStack().damage(1, player, p -> p.sendToolBreakStatus(context.getHand()));
                player.getHungerManager().addExhaustion(2.0F);
            }
        }
        return ActionResult.success(world.isClient);
    }

    private void rummage(ServerWorld world, BlockPos blockPos) {
        RummageManager.getRummageStacks(world, blockPos, this.getDefaultStack()).forEach(
                stack -> Block.dropStack(world, blockPos.up(), stack)
        );
    }


    public static class RummageManager {
        public static final TagKey<Block> RUMMAGE_ABLE = TagKey.of(RegistryKeys.BLOCK, Envision.id("rummage_able"));

        public static final LootContextType RUMMAGE = LootContextTypes.register(
                "envision:rummage",
                builder -> builder.require(LootContextParameters.BLOCK_STATE)
                        .require(LootContextParameters.ORIGIN)
                        .require(LootContextParameters.TOOL)
        );

        public static Identifier getRummageLootID(Block block) {
            Identifier identifier = Registries.BLOCK.getId(block);
            return identifier.withPrefixedPath("rummage/");
        }

        public static List<ItemStack> getRummageStacks(ServerWorld world, BlockPos blockPos, ItemStack tool) {
            BlockState state = world.getBlockState(blockPos);
            Identifier rummageLootID = RummageManager.getRummageLootID(state.getBlock());
            if (rummageLootID == LootTables.EMPTY) {
                return Collections.emptyList();
            } else {
                LootContextParameterSet.Builder builder = (new LootContextParameterSet.Builder(world))
                        .add(LootContextParameters.ORIGIN, Vec3d.ofCenter(blockPos))
                        .add(LootContextParameters.TOOL, tool)
                        .add(LootContextParameters.BLOCK_STATE, state);
                LootContextParameterSet lootContextParameterSet = builder.build(RummageManager.RUMMAGE);
                LootTable lootTable = world.getServer().getLootManager().getLootTable(rummageLootID);
                return lootTable.generateLoot(lootContextParameterSet);
            }
        }
    }
}
