package me.eastcause.duels.utils;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemBuilder implements Cloneable{

    private ItemStack itemStack;
    private List<String> lore = new ArrayList<>();

    public ItemBuilder(Material material){
        this(material, 1);
    }

    public ItemBuilder(ItemStack itemStack){
        this.itemStack = itemStack.clone();
        if(itemStack.hasItemMeta()){
            if(itemStack.getItemMeta().hasLore()){
                lore = itemStack.getItemMeta().getLore();
            }
        }
    }

    public ItemBuilder(Material material, int amount){
        itemStack = new ItemStack(material, amount);
    }

    public ItemStack build(){
        if(itemStack == null) return null;
        setLore(lore);
        return itemStack;
    }

    @Override
    protected ItemBuilder clone(){
        return new ItemBuilder(itemStack);
    }

    public ItemBuilder setMaterial(Material material){
        this.itemStack.setType(material);
        return this;
    }

    public ItemBuilder setAmount(int amount){
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setDurability(short durability){
        this.itemStack.setDurability(durability);
        return this;
    }

    public ItemBuilder setDurability(int durability){
        this.itemStack.setDurability((short) durability);
        return this;
    }

    public ItemMeta getItemMeta(){
        return this.itemStack.getItemMeta();
    }

    public ItemBuilder setDisplayName(String name){
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(Utils.color(name));
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore){
        this.lore = lore;
        ItemMeta meta = getItemMeta();
        meta.setLore(Utils.color(lore));
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder putLore(List<String> lore){
        this.lore = lore;
        return this;
    }

    public ItemBuilder addLoreLine(String line){
        this.lore.add(line);
        return this;
    }

    public ItemBuilder deleteLoreLine(int index){
        ItemMeta meta = getItemMeta();
        if(meta.getLore() == null){
            return this;
        }
        if(meta.getLore().isEmpty()){
            return this;
        }
        int size = meta.getLore().size();
        if(index >= size){
            return this;
        }
        meta.getLore().remove(index);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder clearLore(){
        ItemMeta meta = getItemMeta();
        meta.setLore(new ArrayList<>());
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level){
        ItemMeta meta = getItemMeta();
        meta.addEnchant(enchantment, level, true);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level){
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder setEnchantments(Map<Enchantment, Integer> enchantments){
        this.itemStack.addUnsafeEnchantments(enchantments);
        return this;
    }

    public ItemBuilder removeEnchantments(Enchantment... enchantments){
        Arrays.asList(enchantments).forEach(enchantment -> {
            this.itemStack.removeEnchantment(enchantment);
        });
        return this;
    }

    public ItemBuilder setSkullMeta(String owner){
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        meta.setOwner(owner);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setRepairCost(int cost){
        Repairable repairable = (Repairable) itemStack.getItemMeta();
        repairable.setRepairCost(cost);
        this.itemStack.setItemMeta((ItemMeta) repairable);
        return this;
    }

    public ItemBuilder setLeatherArmorMeta(Color color){
        LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();
        meta.setColor(color);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setFireworkMeta(int power, FireworkEffect... fireworkEffects){
        FireworkMeta meta = (FireworkMeta) itemStack.getItemMeta();
        meta.setPower(power);
        meta.clearEffects();
        meta.addEffects(fireworkEffects);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setFireworkEffectMeta(FireworkEffect effect){
        FireworkEffectMeta meta = (FireworkEffectMeta) itemStack.getItemMeta();
        meta.setEffect(effect);
        this.itemStack.setItemMeta(meta);
        return this;
    }


    public ItemBuilder setBookMeta(String author, String title, String... pages){
        BookMeta bookMeta = (BookMeta) itemStack.getItemMeta();
        bookMeta.setAuthor(author);
        bookMeta.setTitle(title);
        bookMeta.setPages(pages);
        this.itemStack.setItemMeta(bookMeta);
        return this;
    }

    public ItemBuilder setBannerMeta(DyeColor baseColor, Pattern... patterns){
        BannerMeta bannerMeta = (BannerMeta) itemStack.getItemMeta();
        bannerMeta.setBaseColor(baseColor);
        bannerMeta.setPatterns(Arrays.asList(patterns));
        this.itemStack.setItemMeta(bannerMeta);
        return this;
    }

}
