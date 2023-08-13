package com.aki.things.block;

import com.aki.things.block.shelf.shelfBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.aki.AkiMod.MOD_ID;

public class block {
    public static final Block SHELF_OAK = new shelfBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque());
    public static final Block SHELF_BIRCH = new shelfBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque());
    public static final Block SHELF_SPRUCE = new shelfBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque());
    public static final Block SHELF_DARK_OAK = new shelfBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque());
    public static final Block SHELF_JUNGLE = new shelfBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque());
    public static final Block SHELF_CRIMSON = new shelfBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque());
    public static final Block SHELF_WARPED = new shelfBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque());
    public static final Block SHELF_MANGROVE = new shelfBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque());
    public static final Block SHELF_ACACIA = new shelfBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque());

    public static void initializeBlocks() {
        //shelf
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "shelf_oak"), SHELF_OAK);
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "shelf_birch"), SHELF_BIRCH);
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "shelf_spruce"), SHELF_SPRUCE);
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "shelf_dark_oak"), SHELF_DARK_OAK);
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "shelf_jungle"), SHELF_JUNGLE);
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "shelf_crimson"), SHELF_CRIMSON);
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "shelf_warped"), SHELF_WARPED);
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "shelf_mangrove"), SHELF_MANGROVE);
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "shelf_acacia"), SHELF_ACACIA);
    }
    public static void initializeBlockItems() {
        //shelf
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "shelf_oak"), new BlockItem(SHELF_OAK, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "shelf_birch"), new BlockItem(SHELF_BIRCH, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "shelf_spruce"), new BlockItem(SHELF_SPRUCE, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "shelf_dark_oak"), new BlockItem(SHELF_DARK_OAK, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "shelf_jungle"), new BlockItem(SHELF_JUNGLE, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "shelf_crimson"), new BlockItem(SHELF_CRIMSON, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "shelf_warped"), new BlockItem(SHELF_WARPED, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "shelf_mangrove"), new BlockItem(SHELF_MANGROVE, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "shelf_acacia"), new BlockItem(SHELF_ACACIA, new FabricItemSettings()));
    }
}
