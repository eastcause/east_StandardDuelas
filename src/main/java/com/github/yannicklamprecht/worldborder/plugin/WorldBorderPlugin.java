package com.github.yannicklamprecht.worldborder.plugin;

import com.github.yannicklamprecht.worldborder.api.BorderAPI;
import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by ysl3000
 */
public class WorldBorderPlugin {

    public void init(JavaPlugin javaPlugin) {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace('.', ',').split(",")[3];
        System.out.println("Version: " + version);
        WorldBorderApi worldBorderApi;
        switch (version) {
            case "v1_16_R3":
                worldBorderApi = new com.github.yannicklamprecht.worldborder.v1_16_R3.Impl();
                break;
            default: {
                System.err.println("Unsupported version of Minecraft");
                Bukkit.getPluginManager().disablePlugin(javaPlugin);
                return;
            }
        }

        worldBorderApi = new PersistenceWrapper(javaPlugin, worldBorderApi);

        BorderAPI.setWorldBorderApi(worldBorderApi);
    }

}
