package me.eastcause.duels;

import com.github.yannicklamprecht.worldborder.plugin.WorldBorderPlugin;
import lombok.Getter;
import me.eastcause.duels.configuration.Config;
import me.eastcause.duels.database.impl.MySQL;
import me.eastcause.duels.database.SQL;
import me.eastcause.duels.database.impl.SQLite;
import me.eastcause.duels.listeners.DamageListener;
import me.eastcause.duels.listeners.OtherEventsListener;
import me.eastcause.duels.listeners.PlayerJoinListener;
import me.eastcause.duels.model.sidebar.Sidebar;
import me.eastcause.duels.repository.DuelManager;
import me.eastcause.duels.repository.KitManager;
import me.eastcause.duels.repository.UserManager;
import me.eastcause.duels.tasks.SaveAllTask;
import me.eastcause.duels.yaml.YamlManager;
import me.eastcause.duels.yaml.impl.ConfigYaml;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class DuelPlugin extends JavaPlugin {

    @Getter private static DuelPlugin duelPlugin;
    @Getter private static SQL sql;

    @Override
    public void onEnable() {
        duelPlugin = this;
        YamlManager.registerYamls(new ConfigYaml(this, "config.yml"));
        Config.load();
        if(Config.DATABASE_USE_MYSQL) {
            sql = new MySQL(Config.DATABASE_MYSQL_HOST, Config.DATABASE_MYSQL_PORT, Config.DATABASE_MYSQL_USER, Config.DATABASE_MYSQL_PASSWORD, Config.DATABASE_MYSQL_PASSWORD);
        }else{
            sql = new SQLite(Config.DATABASE_SQLITE_DATABASE);
        }
        new WorldBorderPlugin().init(this);
        KitManager.load();
        UserManager.load();
        registerListeners(new DamageListener(), new PlayerJoinListener(), new OtherEventsListener());

        (new SaveAllTask()).runTaskTimerAsynchronously(this, 100L, 100L);

        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){
                    DuelManager.join(player, true);
                    Sidebar sidebar = new Sidebar(player);
                    UserManager.getSIDEBARS().put(player.getUniqueId(), sidebar);
                }
            }
        }.runTaskLater(this, 1L);
    }

    @Override
    public void onDisable() {
        UserManager.saveAll();
        sql.disconnect();
    }

    public void registerListener(Listener listener){
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public void registerListeners(Listener... listeners){
        Arrays.asList(listeners).forEach(this::registerListener);
    }
}
