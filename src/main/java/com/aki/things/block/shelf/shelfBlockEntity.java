package com.aki.things.block.shelf;


import com.aki.ImplementedInventory;
import com.mojang.logging.LogUtils;

import java.util.Objects;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import com.aki.things.block.blockEntities;

public class shelfBlockEntity extends BlockEntity implements ImplementedInventory {
    static final Logger LOGGER = LogUtils.getLogger();
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);

    public shelfBlockEntity(BlockPos pos, BlockState state) {
        super(blockEntities.SHELF_BLOCK_ENTITY, pos, state);
    }
    @Override
    public void readNbt(NbtCompound nbt) {
        inventory.clear();
        Inventories.readNbt(nbt, inventory);
        markDirty();
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, inventory);
    }

    public void clear() {
        inventory.clear();
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {

        return createNbt();
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    public int size() {
        return 4;
    }

    public boolean isEmpty() {
        return inventory.stream().allMatch(ItemStack::isEmpty);
    }

    public ItemStack getStack(int slot) {
        return inventory.get(slot - 1);
    }

    public void setInventory(DefaultedList<ItemStack> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            this.inventory.set(i, inventory.get(i));
        }
    }

    @Override
    public void markDirty() {
        if (this.world != null) {
            markDirtyInWorld(this.world, this.pos, this.getCachedState());
        }
    }

    protected void markDirtyInWorld(World world, BlockPos pos, BlockState state) {
        world.markDirty(pos);

        if (!world.isClient()) {
            ((ServerWorld) world).getChunkManager().markForUpdate(pos);
        }
    }
    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = Objects.requireNonNullElse(inventory.get(slot - 1), ItemStack.EMPTY);
        inventory.set(slot - 1, ItemStack.EMPTY);
        return itemStack;
    }

    public ItemStack removeStack(int slot) {
        return this.removeStack(slot, 64);
    }

    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot - 1, stack);
    }

    public boolean canTransferTo(Inventory hopperInventory, int slot, ItemStack stack) {
        return false;
    }

    public int getMaxCountPerStack() {
        return 1;
    }

    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }

    public boolean isValid(int slot, ItemStack stack) {
        return this.getStack(slot).isEmpty();
    }
}
