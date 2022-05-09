package me.eastcause.duels.configuration;

import me.eastcause.duels.yaml.YamlManager;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public static double DEFAULT_ATTACK_SPEED = 4;
    public static String DATABASE_MYSQL_HOST;
    public static int DATABASE_MYSQL_PORT;
    public static String DATABASE_MYSQL_USER;
    public static String DATABASE_MYSQL_PASSWORD;
    public static String DATABASE_MYSQL_DATABASE;
    public static String DATABASE_SQLITE_DATABASE;
    public static boolean DATABASE_USE_MYSQL;

    public static void load(){
        FileConfiguration config = YamlManager.getConfig("config.yml");
        DATABASE_MYSQL_HOST = config.getString("database.mysql.host");
        DATABASE_MYSQL_PORT = config.getInt("database.mysql.port");
        DATABASE_MYSQL_USER = config.getString("database.mysql.user");
        DATABASE_MYSQL_PASSWORD = config.getString("database.mysql.password");
        DATABASE_MYSQL_DATABASE = config.getString("database.mysql.database");
        DATABASE_SQLITE_DATABASE = config.getString("database.sqlite.database");
        DATABASE_USE_MYSQL = (config.getString("database.use").equalsIgnoreCase("MYSQL"));
    }

}
