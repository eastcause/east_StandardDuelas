package me.eastcause.duels.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class Utils {

    public static String color(String message){
        if(message == null) return "";
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String c(String message){
        return color(message);
    }

    public static String fixColor(String message){
        return color(message);
    }

    public static boolean sendMessage(Player player, String message){
        if(player == null) return false;
        player.sendMessage(color(message));
        return true;
    }

    public static List<String> color(List<String> list){
        for(int i = 0; i < list.size(); i++){
            list.set(i, color(list.get(i)));
        }
        return list;
    }

    public static boolean sendTitle(Player player, String title, String subTitle){
        if(player == null){
            return false;
        }
        player.sendTitle(color(title), color(subTitle), 1, 30, 20);
        return true;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
