package me.eastcause.duels.database;

import me.eastcause.duels.DuelPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MysqlTimer extends BukkitRunnable {
    @Override
    public void run() {
        if(DuelPlugin.getSql().isConnected()){
            DuelPlugin.getSql().update("DO 1");
        }
    }
}
