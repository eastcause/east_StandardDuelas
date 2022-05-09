package me.eastcause.duels.yaml.impl;

import me.eastcause.duels.yaml.Yaml;
import org.bukkit.plugin.Plugin;

public class ConfigYaml extends Yaml {
    public ConfigYaml(Plugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    public void topUp() {
        setIfThereIsNo("database.mysql.host", "localhost");
        setIfThereIsNo("database.mysql.port", 3306);
        setIfThereIsNo("database.mysql.user", "root");
        setIfThereIsNo("database.mysql.password", "");
        setIfThereIsNo("database.mysql.database", "standard");
        setIfThereIsNo("database.sqlite.database", "standard");
        setIfThereIsNo("database.use", "SQLITE");
    }
}
