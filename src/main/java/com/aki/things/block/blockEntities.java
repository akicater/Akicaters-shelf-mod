package com.aki.things.block;

import com.aki.things.block.shelf.shelfBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.aki.AkiMod.MOD_ID;
import static com.aki.things.block.block.*;

public class blockEntities {
    public static BlockEntityType<shelfBlockEntity> SHELF_BLOCK_ENTITY;

    public static void registerTypes(){
        SHELF_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "shelf_block_entity"), FabricBlockEntityTypeBuilder.create(shelfBlockEntity::new,
                SHELF_OAK,SHELF_MANGROVE,SHELF_SPRUCE,SHELF_WARPED,SHELF_DARK_OAK,SHELF_CRIMSON,SHELF_ACACIA,SHELF_JUNGLE,SHELF_BIRCH).build(null));
    }
}
