package me.eastcause.duels.repository;

import com.github.yannicklamprecht.worldborder.api.BorderAPI;
import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import lombok.Getter;
import me.eastcause.duels.DuelPlugin;
import me.eastcause.duels.model.Duel;
import me.eastcause.duels.model.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DuelManager {

    @Getter private static ConcurrentHashMap<Long, Duel> DUELS = new ConcurrentHashMap<>();
    @Getter private static ConcurrentHashMap<UUID, Long> DUELS_ID = new ConcurrentHashMap<>();

    public static Duel getDuel(UUID uuid){
        long l = getDUELS_ID().getOrDefault(uuid, 0L);
        if(l <= 0L){
            return null;
        }
        return getDUELS().get(l);
    }

    public static Duel createDuel(Player player, Player other, int time, Location location, Kit kit){
        long id = System.currentTimeMillis();
        Duel versus = new Duel(id, player, other, time, location, kit);
        getDUELS().put(id, versus);
        getDUELS_ID().put(player.getUniqueId(), id);
        getDUELS_ID().put(other.getUniqueId(), id);
        player.getInventory().setContents(kit.getEq());
        player.getInventory().setArmorContents(kit.getArmor());
        other.getInventory().setContents(kit.getEq());
        other.getInventory().setArmorContents(kit.getArmor());
        return versus;
    }

    public static void removeDuel(Duel duel){
        getDUELS_ID().remove(duel.getAccepting().getUniqueId());
        getDUELS_ID().remove(duel.getInviter().getUniqueId());
        getDUELS().remove(duel.getId());
    }

    public static void join(Player player, boolean tp){
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setFoodLevel(40);
        player.setHealth(20);
        new BukkitRunnable() {
            UUID uuid = player.getUniqueId();
            @Override
            public void run() {
                Player p =Bukkit.getPlayer(uuid);
                if(p != null){
                    p.setFireTicks(0);
                }
            }
        }.runTaskLater(DuelPlugin.getDuelPlugin(), 1L);
        for(PotionEffect effect : player.getActivePotionEffects()){
            player.removePotionEffect(effect.getType());
        }
        int i = 0;
        for(Kit kit : KitManager.getKITS().values()){
            player.getInventory().setItem(i, kit.getItemStack().clone());
            i++;
        }
        if(tp){
            player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        }
    }

    public static void removeBorder(Player player){
        WorldBorderApi worldBorderApi = BorderAPI.getApi();
        worldBorderApi.resetWorldBorderToGlobal(player);
    }

}
