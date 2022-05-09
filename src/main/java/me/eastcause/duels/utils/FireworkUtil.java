package me.eastcause.duels.utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkUtil {

    public static void fireworks(Location location, int amount){
        for(int i = 0; i < amount; i++){
            Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
            FireworkMeta fireworkMeta = firework.getFireworkMeta();
            fireworkMeta.setPower(1);
            fireworkMeta.addEffects(FireworkEffect.builder().withColor(Color.RED, Color.AQUA, Color.YELLOW, Color.LIME, Color.WHITE, Color.FUCHSIA).flicker(true).withFade(Color.BLACK).build());
            firework.setFireworkMeta(fireworkMeta);
        }
    }
}
