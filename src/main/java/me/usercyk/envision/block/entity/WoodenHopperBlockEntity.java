package me.usercyk.envision.block.entity;

import me.usercyk.envision.block.EnvisionBlockEntityTypes;
import me.usercyk.envision.screen.WoodenHopperScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.Hopper;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WoodenHopperBlockEntity
        extends LootableContainerBlockEntity
        implements Hopper, ExtendedScreenHandlerFactory {
    public static final int TRANSFER_COOLDOWN = 16;
    public static final int INVENTORY_SIZE = 1;

    public static final String CONTAINER_TRANS_KEY = "container.wooden_hopper";
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
    private int transferCooldown = -1;
    private long lastTickTime;

    public WoodenHopperBlockEntity(BlockPos pos, BlockState state) {
        super(EnvisionBlockEntityTypes.WOODEN_HOPPER_ENTITY_TYPE, pos, state);
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, WoodenHopperBlockEntity blockEntity) {
        --blockEntity.transferCooldown;
        blockEntity.lastTickTime = world.getTime();
        if (!blockEntity.needsCooldown()) {
            blockEntity.setTransferCooldown(0);
            WoodenHopperBlockEntity.insertAndExtract(world, pos, state, blockEntity, () -> WoodenHopperBlockEntity.extract(world, blockEntity));
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    private static boolean insertAndExtract(World world, BlockPos pos, BlockState state, WoodenHopperBlockEntity blockEntity, BooleanSupplier booleanSupplier) {
        if (world.isClient) {
            return false;
        }
        if (!blockEntity.needsCooldown() && state.get(HopperBlock.ENABLED)) {
            boolean bl = false;
            if (!blockEntity.isEmpty()) {
                bl = WoodenHopperBlockEntity.insert(world, pos, state, blockEntity);
            }
            if (!blockEntity.isFull()) {
                bl |= booleanSupplier.getAsBoolean();
            }
            if (bl) {
                blockEntity.setTransferCooldown(TRANSFER_COOLDOWN);
                WoodenHopperBlockEntity.markDirty(world, pos, state);
                return true;
            }
        }
        return false;
    }

    private static boolean insert(World world, BlockPos pos, BlockState state, Inventory inventory) {
        Inventory inventory2 = WoodenHopperBlockEntity.getOutputInventory(world, pos, state);
        if (inventory2 == null) {
            return false;
        }
        Direction direction = state.get(HopperBlock.FACING).getOpposite();
        if (WoodenHopperBlockEntity.isInventoryFull(inventory2, direction)) {
            return false;
        }
        for (int i = 0; i < inventory.size(); ++i) {
            if (inventory.getStack(i).isEmpty()) continue;
            ItemStack itemStack = inventory.getStack(i).copy();
            ItemStack itemStack2 = WoodenHopperBlockEntity.transfer(inventory, inventory2, inventory.removeStack(i, 1), direction);
            if (itemStack2.isEmpty()) {
                inventory2.markDirty();
                return true;
            }
            inventory.setStack(i, itemStack);
        }
        return false;
    }

    private static IntStream getAvailableSlots(Inventory inventory, Direction side) {
        if (inventory instanceof SidedInventory) {
            return IntStream.of(((SidedInventory) inventory).getAvailableSlots(side));
        }
        return IntStream.range(0, inventory.size());
    }

    private static boolean isInventoryFull(Inventory inventory, Direction direction) {
        return WoodenHopperBlockEntity.getAvailableSlots(inventory, direction).allMatch(slot -> {
            ItemStack itemStack = inventory.getStack(slot);
            return itemStack.getCount() >= itemStack.getMaxCount();
        });
    }

    private static boolean isInventoryEmpty(Inventory inv, Direction facing) {
        return WoodenHopperBlockEntity.getAvailableSlots(inv, facing).allMatch(slot -> inv.getStack(slot).isEmpty());
    }

    public static boolean extract(World world, Hopper hopper) {
        Inventory inventory = WoodenHopperBlockEntity.getInputInventory(world, hopper);
        if (inventory != null) {
            Direction direction = Direction.DOWN;
            if (WoodenHopperBlockEntity.isInventoryEmpty(inventory, direction)) {
                return false;
            }
            return WoodenHopperBlockEntity.getAvailableSlots(inventory, direction).anyMatch(slot -> WoodenHopperBlockEntity.extract(hopper, inventory, slot, direction));
        }
        for (ItemEntity itemEntity : WoodenHopperBlockEntity.getInputItemEntities(world, hopper)) {
            if (!WoodenHopperBlockEntity.extract(hopper, itemEntity)) continue;
            return true;
        }
        return false;
    }

    private static boolean extract(Hopper hopper, Inventory inventory, int slot, Direction side) {
        ItemStack itemStack = inventory.getStack(slot);
        if (!itemStack.isEmpty() && WoodenHopperBlockEntity.canExtract(hopper, inventory, itemStack, slot, side)) {
            ItemStack itemStack2 = itemStack.copy();
            ItemStack itemStack3 = WoodenHopperBlockEntity.transfer(inventory, hopper, inventory.removeStack(slot, 1), null);
            if (itemStack3.isEmpty()) {
                inventory.markDirty();
                return true;
            }
            inventory.setStack(slot, itemStack2);
        }
        return false;
    }

    public static boolean extract(Inventory inventory, ItemEntity itemEntity) {
        boolean bl = false;
        ItemStack itemStack = itemEntity.getStack().copy();
        ItemStack itemStack2 = WoodenHopperBlockEntity.transfer(null, inventory, itemStack, null);
        if (itemStack2.isEmpty()) {
            bl = true;
            itemEntity.setStack(ItemStack.EMPTY);
            itemEntity.discard();
        } else {
            itemEntity.setStack(itemStack2);
        }
        return bl;
    }

    public static ItemStack transfer(@Nullable Inventory from, Inventory to, ItemStack stack, @Nullable Direction side) {
        if (to instanceof SidedInventory sidedInventory) {
            if (side != null) {
                int[] is = sidedInventory.getAvailableSlots(side);
                int i = 0;
                while (i < is.length) {
                    if (stack.isEmpty()) return stack;
                    stack = WoodenHopperBlockEntity.transfer(from, to, stack, is[i], side);
                    ++i;
                }
                return stack;
            }
        }
        int j = to.size();
        int i = 0;
        while (i < j) {
            if (stack.isEmpty()) return stack;
            stack = WoodenHopperBlockEntity.transfer(from, to, stack, i, side);
            ++i;
        }
        return stack;
    }

    private static boolean canInsert(Inventory inventory, ItemStack stack, int slot, @Nullable Direction side) {
        if (!inventory.isValid(slot, stack)) {
            return false;
        }
        return !(inventory instanceof SidedInventory) || ((SidedInventory) inventory).canInsert(slot, stack, side);
    }

    private static boolean canExtract(Inventory hopperInventory, Inventory fromInventory, ItemStack stack, int slot, Direction facing) {
        if (!fromInventory.canTransferTo(hopperInventory, slot, stack)) {
            return false;
        }
        return !(fromInventory instanceof SidedInventory) || ((SidedInventory) fromInventory).canExtract(slot, stack, facing);
    }

    private static ItemStack transfer(@Nullable Inventory from, Inventory to, ItemStack stack, int slot, @Nullable Direction side) {
        ItemStack itemStack = to.getStack(slot);
        if (WoodenHopperBlockEntity.canInsert(to, stack, slot, side)) {
            int j;
            boolean bl = false;
            boolean bl2 = to.isEmpty();
            if (itemStack.isEmpty()) {
                to.setStack(slot, stack);
                stack = ItemStack.EMPTY;
                bl = true;
            } else if (WoodenHopperBlockEntity.canMergeItems(itemStack, stack)) {
                int i = stack.getMaxCount() - itemStack.getCount();
                j = Math.min(stack.getCount(), i);
                stack.decrement(j);
                itemStack.increment(j);
                bl = j > 0;
            }
            if (bl) {
                WoodenHopperBlockEntity hopperBlockEntity;
                if (bl2 && to instanceof WoodenHopperBlockEntity && !(hopperBlockEntity = (WoodenHopperBlockEntity) to).isDisabled()) {
                    j = 0;
                    if (from instanceof WoodenHopperBlockEntity hopperBlockEntity2) {
                        if (hopperBlockEntity.lastTickTime >= hopperBlockEntity2.lastTickTime) {
                            j = 1;
                        }
                    }
                    hopperBlockEntity.setTransferCooldown(TRANSFER_COOLDOWN - j);
                }
                to.markDirty();
            }
        }
        return stack;
    }

    @Nullable
    private static Inventory getOutputInventory(World world, BlockPos pos, BlockState state) {
        Direction direction = state.get(HopperBlock.FACING);
        return WoodenHopperBlockEntity.getInventoryAt(world, pos.offset(direction));
    }

    @Nullable
    private static Inventory getInputInventory(World world, Hopper hopper) {
        return WoodenHopperBlockEntity.getInventoryAt(world, hopper.getHopperX(), hopper.getHopperY() + 1.0, hopper.getHopperZ());
    }

    public static List<ItemEntity> getInputItemEntities(World world, Hopper hopper) {
        return hopper.getInputAreaShape().getBoundingBoxes().stream().flatMap(box -> world.getEntitiesByClass(ItemEntity.class, box.offset(hopper.getHopperX() - 0.5, hopper.getHopperY() - 0.5, hopper.getHopperZ() - 0.5), EntityPredicates.VALID_ENTITY).stream()).collect(Collectors.toList());
    }

    @Nullable
    public static Inventory getInventoryAt(World world, BlockPos pos) {
        return WoodenHopperBlockEntity.getInventoryAt(world, (double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5);
    }

    @Nullable
    private static Inventory getInventoryAt(World world, double x, double y, double z) {
        List<Entity> list;
        BlockEntity blockEntity;
        Inventory inventory = null;
        BlockPos blockPos = BlockPos.ofFloored(x, y, z);
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block instanceof InventoryProvider) {
            inventory = ((InventoryProvider) block).getInventory(blockState, world, blockPos);
        } else if (blockState.hasBlockEntity() && (blockEntity = world.getBlockEntity(blockPos)) instanceof Inventory && (inventory = (Inventory) blockEntity) instanceof ChestBlockEntity && block instanceof ChestBlock) {
            inventory = ChestBlock.getInventory((ChestBlock) block, blockState, world, blockPos, true);
        }
        if (inventory == null && !(list = world.getOtherEntities(null, new Box(x - 0.5, y - 0.5, z - 0.5, x + 0.5, y + 0.5, z + 0.5), EntityPredicates.VALID_INVENTORIES)).isEmpty()) {
            inventory = (Inventory) list.get(world.random.nextInt(list.size()));
        }
        return inventory;
    }

    private static boolean canMergeItems(ItemStack first, ItemStack second) {
        return first.getCount() <= first.getMaxCount() && ItemStack.canCombine(first, second);
    }

    public static void onEntityCollided(World world, BlockPos pos, BlockState state, Entity entity, WoodenHopperBlockEntity blockEntity) {
        ItemEntity itemEntity;
        if (entity instanceof ItemEntity && !(itemEntity = (ItemEntity) entity).getStack().isEmpty() && VoxelShapes.matchesAnywhere(VoxelShapes.cuboid(entity.getBoundingBox().offset(-pos.getX(), -pos.getY(), -pos.getZ())), blockEntity.getInputAreaShape(), BooleanBiFunction.AND)) {
            WoodenHopperBlockEntity.insertAndExtract(world, pos, state, blockEntity, () -> WoodenHopperBlockEntity.extract(blockEntity, itemEntity));
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.readLootTable(nbt)) {
            Inventories.readNbt(nbt, this.inventory);
        }
        this.transferCooldown = nbt.getInt("TransferCooldown");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (!this.writeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory);
        }
        nbt.putInt("TransferCooldown", this.transferCooldown);
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        this.generateLoot(null);
        return Inventories.splitStack(this.method_11282(), slot, amount);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.generateLoot(null);
        this.method_11282().set(slot, stack);
        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable(CONTAINER_TRANS_KEY);
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new WoodenHopperScreenHandler(syncId, playerInventory, this);
    }

    private boolean isFull() {
        for (ItemStack itemStack : this.inventory) {
            if (!itemStack.isEmpty() && itemStack.getCount() == itemStack.getMaxCount()) continue;
            return false;
        }
        return true;
    }

    @Override
    public double getHopperX() {
        return (double) this.pos.getX() + 0.5;
    }

    @Override
    public double getHopperY() {
        return (double) this.pos.getY() + 0.5;
    }

    @Override
    public double getHopperZ() {
        return (double) this.pos.getZ() + 0.5;
    }

    private void setTransferCooldown(int transferCooldown) {
        this.transferCooldown = transferCooldown;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean needsCooldown() {
        return this.transferCooldown > 0;
    }

    private boolean isDisabled() {
        return this.transferCooldown > TRANSFER_COOLDOWN;
    }

    @Override
    protected DefaultedList<ItemStack> method_11282() {
        return this.inventory;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
    }
}

