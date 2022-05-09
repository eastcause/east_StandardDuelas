package me.eastcause.duels.repository;

import lombok.Getter;
import me.eastcause.duels.configuration.Config;
import me.eastcause.duels.model.Kit;
import me.eastcause.duels.utils.ItemBuilder;
import me.eastcause.duels.utils.ItemStackArray;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

public class KitManager {

    @Getter private static LinkedHashMap<Integer, Kit> KITS = new LinkedHashMap<>();

    public static Kit createKit(int slot, String name, ItemStack itemStack, ItemStack[] eq, ItemStack[] armor, double attackSpeed, int maximumNoDamageTicks){
        Kit kit = new Kit(slot, name, itemStack, eq, armor, attackSpeed, maximumNoDamageTicks);
        getKITS().put(slot, kit);
        return kit;
    }

    public static Kit getKitBySlot(int slot){
        return getKITS().getOrDefault(slot, null);
    }

    public static void load(){
        createKit(1, "&bDIAMOND", new ItemBuilder(Material.DIAMOND_SWORD).setDisplayName("&7Wyzwij gracza na pojedynek: &bDIAMOND").build(),

                new ItemStackArray(36).setItem(1, new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 5).build()).getItems(),

                new ItemStack[] {new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).build(),
                        new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).build(),
                        new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).build(),
                        new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).build()},
                Config.DEFAULT_ATTACK_SPEED, 20
        );

        createKit(2, "&aIRON", new ItemBuilder(Material.IRON_SWORD).setDisplayName("&7Wyzwij gracza na pojedynek: &fIRON").build(),

                new ItemStackArray(36).setItem(1, new ItemBuilder(Material.IRON_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 5).build()).getItems(),

                new ItemStack[] {new ItemBuilder(Material.IRON_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).build(),
                        new ItemBuilder(Material.IRON_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).build(),
                        new ItemBuilder(Material.IRON_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).build(),
                        new ItemBuilder(Material.IRON_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).build()},
                Config.DEFAULT_ATTACK_SPEED, 20
        );

        createKit(3, "&fCOMBO", new ItemBuilder(Material.NETHERITE_SWORD).setDisplayName("&7Wyzwij gracza na pojedynek: &cCOMBO").build(),

                new ItemStackArray(36).setItem(1, new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 5).addEnchant(Enchantment.FIRE_ASPECT, 2).build())
                        .setItem(2, new ItemBuilder(Material.ENCHANTED_GOLDEN_APPLE, 64).build()).getItems(),

                new ItemStack[] {new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).build(),
                        new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).build(),
                        new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).build(),
                        new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).build()},
                100.0, 1
        );

        createKit(4, "&6SOUP", new ItemBuilder(Material.GOLDEN_SWORD).setDisplayName("&7Wyzwij gracza na pojedynek: &6SOUP").build(),

                new ItemStackArray(36).setItem(1, new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 5).addEnchant(Enchantment.FIRE_ASPECT, 2).build())
                        .setItem(2, new ItemBuilder(Material.MUSHROOM_STEW, 1).build())
                        .setItem(3, new ItemBuilder(Material.MUSHROOM_STEW, 1).build())
                        .setItem(4, new ItemBuilder(Material.MUSHROOM_STEW, 1).build())
                        .setItem(5, new ItemBuilder(Material.MUSHROOM_STEW, 1).build())
                        .setItem(6, new ItemBuilder(Material.MUSHROOM_STEW, 1).build())
                        .setItem(7, new ItemBuilder(Material.MUSHROOM_STEW, 1).build())
                        .setItem(8, new ItemBuilder(Material.MUSHROOM_STEW, 1).build())
                        .setItem(9, new ItemBuilder(Material.MUSHROOM_STEW, 1).build())
                        .getItems(),

                new ItemStack[] {new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).build(),
                        new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).build(),
                        new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).build(),
                        new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).build()},
                Config.DEFAULT_ATTACK_SPEED, 20
        );
    }
}
