package com.aki.things.block.shelf;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class shelfBlock extends BlockWithEntity implements Waterloggable, BlockEntityProvider {
    public static final Logger LOGGER = LoggerFactory.getLogger("shelf");
    public static DirectionProperty FACING;
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    public shelfBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.SOUTH));
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof shelfBlockEntity entity) {
            int slot = getSlotNumber(hit);
            if (entity.getStack(slot) == ItemStack.EMPTY && player.getStackInHand(Hand.MAIN_HAND) != ItemStack.EMPTY) {
                    setItemStack(world, pos, player, slot, entity);
                    entity.markDirty();
                    world.playSound(player,pos,SoundEvents.BLOCK_WOOD_PLACE,SoundCategory.BLOCKS, 1.0f, (float) (Math.random() + 0.5));
                    return ActionResult.success(world.isClient);
                } else if (entity.getStack(slot) != ItemStack.EMPTY && player.getStackInHand(Hand.MAIN_HAND) == ItemStack.EMPTY) {
                        removeItemStack(world, pos, player, slot, entity);
                        entity.markDirty();
                        world.playSound(player,pos,SoundEvents.BLOCK_WOOD_HIT,SoundCategory.BLOCKS, 1.0f, (float) (Math.random() + 0.5));
                        return ActionResult.success(world.isClient);
                    } else {
                        world.playSound(player,pos,SoundEvents.ITEM_SHIELD_BREAK,SoundCategory.BLOCKS, 0.25f, (float) (Math.random() + 0.5));
                        return ActionResult.CONSUME_PARTIAL;
                    }
        } else {
            return ActionResult.PASS;
        }
    }

    public static void setItemStack(World world, BlockPos pos, PlayerEntity player, int slot, shelfBlockEntity entity){
        if (!world.isClient){
            world.playSound(player, pos, SoundEvents.BLOCK_WOOD_HIT, SoundCategory.BLOCKS, (float) (Math.random() + 0.25), (float) (Math.random() + 0.25));
            entity.setStack(slot, player.getStackInHand(Hand.MAIN_HAND));
            player.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
        }

        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
    }
    public static void removeItemStack(World world, BlockPos pos, PlayerEntity player, int slot, shelfBlockEntity entity){
        if (!world.isClient) {
            world.playSound(player, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, (float) (Math.random() + 0.25), (float) (Math.random() + 0.25));
            player.setStackInHand(Hand.MAIN_HAND, entity.getStack(slot));
            entity.setStack(slot, ItemStack.EMPTY);
        }

        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
    }

    private static int getSlotNumber(BlockHitResult hit) {
        int slot;
        if (hit.getSide() == Direction.SOUTH || hit.getSide() == Direction.NORTH){
            slot = hit.getPos().getX() - hit.getBlockPos().getX() >= 0.5F ? 1 : 2;
        } else {
            slot = hit.getPos().getZ() - hit.getBlockPos().getZ() >= 0.5F ? 1 : 2;
        }
        int column = hit.getPos().getY() - hit.getBlockPos().getY() >= 0.5F ? 0 : 2;
        return slot + column;
    }
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            if (world.getBlockEntity(pos) instanceof shelfBlockEntity) {
                shelfBlockEntity entity = (shelfBlockEntity) world.getBlockEntity(pos);
                if (!entity.isEmpty()) {
                    for(int i = 1; i <= 4; ++i) {
                        ItemStack itemStack = entity.getStack(i);
                        if (!itemStack.isEmpty()) {
                            ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                        }
                    }

                    entity.clear();
                    world.updateComparators(pos, this);
                }
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new shelfBlockEntity(pos, state);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView blockView, BlockPos pos, ShapeContext context) {
        Direction dir = state.get(FACING);
        switch(dir) {
            case NORTH:
                return VoxelShapes.cuboid(0.0f, 0.0f, 0.5f, 1.0f, 1.0f, 1.0f);
            case SOUTH:
                return VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.5f);
            case EAST:
                return VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 0.5f, 1.0f, 1.0f);
            case WEST:
                return VoxelShapes.cuboid(0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            default:
                return VoxelShapes.fullCube();
        }
    }
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }
    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }
    static {
        FACING = Properties.HORIZONTAL_FACING;
    }

}
