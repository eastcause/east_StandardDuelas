package me.eastcause.duels.tasks;

import me.eastcause.duels.repository.UserManager;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveAllTask extends BukkitRunnable {
    @Override
    public void run() {
        UserManager.saveAll();
    }
}
