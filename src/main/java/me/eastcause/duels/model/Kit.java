package me.eastcause.duels.model;

import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Data
public class Kit {

    private int slot;
    private String name;
    private ItemStack itemStack;
    private ItemStack[] eq;
    private ItemStack[] armor;
    private double attackSpeed;
    private int maximumNoDamageTicks;

    public Kit(int slot, String name, ItemStack itemStack, ItemStack[] eq, ItemStack[] armor, double attackSpeed, int maximumNoDamageTicks){
        this.slot = slot;
        this.name = name;
        this.itemStack = itemStack;
        this.eq = eq;
        this.armor = armor;
        this.attackSpeed = attackSpeed;
        this.maximumNoDamageTicks = maximumNoDamageTicks;
    }
}
