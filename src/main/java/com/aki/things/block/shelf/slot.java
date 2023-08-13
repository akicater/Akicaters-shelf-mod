package com.aki.things.block.shelf;

import net.minecraft.item.Item;
import org.apache.commons.lang3.ObjectUtils;

import static java.lang.constant.ConstantDescs.NULL;

public class slot {
    public slot(Item item){
    }
    static Item item;
    public Boolean isEmpty(){
        return item != null;
    }
    public void clearItem(){
        item = null;
    }
    public void setItem(Item item){
        this.item = item;
    }
}
