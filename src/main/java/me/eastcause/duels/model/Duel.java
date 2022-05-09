package me.eastcause.duels.model;

import com.github.yannicklamprecht.worldborder.api.BorderAPI;
import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import lombok.Data;
import me.eastcause.duels.DuelPlugin;
import me.eastcause.duels.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

@Data
public class Duel {

    private long id;
    private Player inviter;
    private Player accepting;
    private int secondsToStart;
    private BukkitTask bukkitTask;

    public Duel(long id, Player inviter, Player accepting, int secondsToStart, Location location, Kit kit){
        this.id = id;
        this.inviter = inviter;
        this.accepting = accepting;
        this.secondsToStart = secondsToStart;
        setBorder(location, inviter);
        setBorder(location, accepting);
        this.inviter.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(kit.getAttackSpeed());
        this.accepting.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(kit.getAttackSpeed());
        this.inviter.setMaximumNoDamageTicks(kit.getMaximumNoDamageTicks());
        this.accepting.setMaximumNoDamageTicks(kit.getMaximumNoDamageTicks());

        for(Player online : Bukkit.getOnlinePlayers()){
            if(online.equals(inviter) || online.equals(accepting)) continue;
            inviter.hidePlayer(DuelPlugin.getDuelPlugin(), online);
            accepting.hidePlayer(DuelPlugin.getDuelPlugin(), online);
        }

        bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                counting();
                setSecondsToStart((getSecondsToStart() - 1));
                if(getSecondsToStart() == 0){
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(DuelPlugin.getDuelPlugin(), 0L, 20L);
    }

    public void counting(){
        Utils.sendTitle(inviter, "", "" + (secondsToStart > 3 ? "&e" + secondsToStart : secondsToStart == 3 ? "&c" + secondsToStart : secondsToStart == 2 ? "&6" + secondsToStart : "&aGOO!!!!!!"));
        Utils.sendTitle(accepting, "", "" + (secondsToStart > 3 ? "&e" + secondsToStart : secondsToStart == 3 ? "&c" + secondsToStart : secondsToStart == 2 ? "&6" + secondsToStart : "&aGOO!!!!!!"));
        inviter.playSound(inviter.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
        accepting.playSound(accepting.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
    }

    public void throwPlayers(){
        throwPlayer(inviter);
        throwPlayer(accepting);
    }

    private void throwPlayer(Player player){
        Vector vector = new Vector(0, 1.5, 0);
        player.setVelocity(vector);
    }

    public boolean isInviter(Player player){
        if (inviter.equals(player)) {
            return true;
        }
        return false;
    }

    public void setBorder(Location location, Player player){
        WorldBorderApi worldBorderApi = BorderAPI.getApi();
        worldBorderApi.setBorder(player, 40, location);
    }

    public boolean isFighter(Player player){
        if(player.equals(inviter)){
            return true;
        }
        if(accepting.equals(player)){
            return true;
        }
        return false;
    }
}
