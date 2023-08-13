package com.aki.things.tabs;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Iterator;

import static com.aki.AkiMod.MOD_ID;
import static com.aki.things.block.block.SHELF_OAK;
import static com.aki.things.item.items.itemsArray;


public class groups {
    static Iterator<Item> itemsArrayIterator = itemsArray.iterator();
    private static final ItemGroup HEAD_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(SHELF_OAK))
            .displayName(Text.translatable("itemGroup.aki.head_group")).entries((displayContext, entries) -> {
                while(itemsArrayIterator.hasNext()) {
                    entries.add(itemsArrayIterator.next());
                }
            })
            .build();

    public static void initiliazeGroups(){
        Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "head_group"), HEAD_GROUP);
    }


    public static void addItemsToGroups(){
        while(itemsArrayIterator.hasNext()) {
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> {

            });
        }

    }
}
