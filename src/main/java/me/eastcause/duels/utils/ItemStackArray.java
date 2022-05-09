package me.eastcause.duels.utils;

import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Data
public class ItemStackArray {

    private ItemStack[] items;

    public ItemStackArray(int maxArray){
        items = new ItemStack[maxArray];
    }

    public ItemStackArray setItem(int slot, ItemStack itemStack){
        items[(slot - 1)] = itemStack;
        return this;
    }

}
