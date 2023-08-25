package com.aki.things.block.shelf;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.Font;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.text.OrderedText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.joml.Math;
import org.joml.Quaternionf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;


public class shelfBER implements BlockEntityRenderer<shelfBlockEntity> {
    public static final Logger LOGGER = LoggerFactory.getLogger("shelfBER");

    static List<Vec3d> North = List.of(new Vec3d(0.75, 0.75, 0.75),new Vec3d(0.25, 0.75, 0.75),new Vec3d(0.75, 0.25, 0.75),new Vec3d(0.25, 0.25, 0.75));
    static List<Vec3d> South = List.of(new Vec3d(0.75, 0.75, 0.25),new Vec3d(0.25, 0.75, 0.25),new Vec3d(0.75, 0.25, 0.25),new Vec3d(0.25, 0.25, 0.25));
    static List<Vec3d> East = List.of(new Vec3d(0.25,0.75,0.75),new Vec3d(0.25,0.75,0.25),new Vec3d(0.25,0.25,0.75),new Vec3d(0.25,0.25,0.25));
    static List<Vec3d> West = List.of(new Vec3d(0.75,0.75,0.75),new Vec3d(0.75,0.75,0.25),new Vec3d(0.75,0.25,0.75),new Vec3d(0.75,0.25,0.25));
    public shelfBER(BlockEntityRendererFactory.Context context){

    }

    static int getLight(World world, BlockPos pos){
        return LightmapTextureManager.pack(world.getLightLevel(LightType.BLOCK, pos), world.getLightLevel(LightType.SKY, pos));
    }
    private double getX(Float num){
        return 0.1 * Math.cos(Math.toRadians(num + 90));
    }
    private double getY(Float num){
        return 0.1 * Math.sin(Math.toRadians(num + 90));
    }
    private double getZ(Float num){
        return 0.1 * Math.sin(Math.toRadians(num + 90));
    }

    float degrees = 0F;
    @Override
    public void render(shelfBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        for(int slot = 0; slot<4 ; slot++){
            degrees += 0.001F;
            //ITEM RENDERER
            Direction facing;
            if (entity.getWorld().getBlockState(entity.getPos()).isAir()){
                return;
            } else {
                facing = entity.getWorld().getBlockState(entity.getPos()).get(Properties.HORIZONTAL_FACING);
            }
            World world = entity.getWorld();
            Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
            Quaternionf quaternionf = camera.getRotation();

            Quaternionf camQuaternion = new Quaternionf(quaternionf.x,-quaternionf.y,-quaternionf.z,-quaternionf.w);

            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            TextRenderer font = MinecraftClient.getInstance().textRenderer;
            ItemStack item = entity.getStack(slot + 1);
            int lightt = getLight(world, entity.getPos());
            matrices.push();
            switch (facing) {
                case NORTH -> matrices.translate(North.get(slot).x,North.get(slot).y,North.get(slot).z);
                case SOUTH -> matrices.translate(South.get(slot).x,South.get(slot).y,South.get(slot).z);
                case EAST -> matrices.translate(East.get(slot).x,East.get(slot).y,East.get(slot).z);
                case WEST -> matrices.translate(West.get(slot).x,West.get(slot).y,West.get(slot).z);
            }
            matrices.scale(0.45F, 0.45F, 0.45F);
            matrices.multiply(new Quaternionf(camera.getRotation().x, camera.getRotation().y, camera.getRotation().z, camera.getRotation().w));
            itemRenderer.renderItem(item, ModelTransformationMode.FIXED,lightt, overlay, matrices,vertexConsumers,world,1);
            matrices.pop();

            //TEXT RENDERER
            matrices.push();
            switch (facing) {
                case NORTH -> matrices.translate(North.get(slot).x - getX(camera.getYaw()),North.get(slot).y - 0.12F,North.get(slot).z - getZ(camera.getYaw()));
                case SOUTH -> matrices.translate(South.get(slot).x - getX(camera.getYaw()),South.get(slot).y - 0.12F,South.get(slot).z - getZ(camera.getYaw()));
                case EAST -> matrices.translate(East.get(slot).x - getX(camera.getYaw()),East.get(slot).y - 0.12F,East.get(slot).z - getZ(camera.getYaw()));
                case WEST -> matrices.translate(West.get(slot).x - getX(camera.getYaw()),West.get(slot).y - 0.12F - getY(camera.getPitch()),West.get(slot).z - getZ(camera.getYaw()));
            }
            matrices.scale(0.018F, -0.018F, 0.018F);
            matrices.multiply(camQuaternion);
            //matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
            font.draw(String.valueOf(item.getCount()),0,0.1F,0xffbb00, true, matrices.peek().getPositionMatrix(), vertexConsumers, TextRenderer.TextLayerType.POLYGON_OFFSET, 0x8c6700,lightt,true);
            matrices.pop();
        }
    }
}
