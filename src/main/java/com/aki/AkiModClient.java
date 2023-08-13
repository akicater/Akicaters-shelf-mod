package com.aki;

import com.aki.things.block.blockEntities;
import com.aki.things.block.shelf.shelfBER;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;


public class AkiModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(blockEntities.SHELF_BLOCK_ENTITY, shelfBER::new);

    }
}
