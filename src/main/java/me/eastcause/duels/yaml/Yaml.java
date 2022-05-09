package me.eastcause.duels.yaml;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

@Data
public abstract class Yaml implements Wad {

    private Plugin plugin;
    private String name;
    private File file;
    private FileConfiguration config;


    public Yaml(Plugin plugin, String name){
        this.plugin = plugin;
        this.name = name;
        checkFiles();
        reloadData();
        topUp();
        saveData();
    }

    @Override
    public void checkFiles() {
        file = new File(getPlugin().getDataFolder(), getName());
        if(!file.exists()){
            file.getParentFile().mkdirs();
            getPlugin().saveResource(getName(), true);
        }
    }

    public void setIfThereIsNo(String path, Object object){
        Object o = config.get(path);
        if(o == null){
            config.set(path, object);
        }
    }

    @Override
    public void reloadData() {
        config = YamlConfiguration.loadConfiguration(getFile());
    }

    @Override
    public boolean saveData() {
        try {
            getConfig().save(getFile());
        }catch (IOException e){
            return false;
        }
        return true;
    }
}
