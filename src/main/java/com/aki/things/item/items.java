package com.aki.things.item;

import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.List;

import static com.aki.things.block.block.*;

public class items {
    public static final List<Item> itemsArray = Arrays.asList(
            Item.fromBlock(SHELF_OAK),
            Item.fromBlock(SHELF_DARK_OAK),
            Item.fromBlock(SHELF_BIRCH),
            Item.fromBlock(SHELF_WARPED),
            Item.fromBlock(SHELF_MANGROVE),
            Item.fromBlock(SHELF_CRIMSON),
            Item.fromBlock(SHELF_ACACIA),
            Item.fromBlock(SHELF_JUNGLE),
            Item.fromBlock(SHELF_SPRUCE)
            );
}
