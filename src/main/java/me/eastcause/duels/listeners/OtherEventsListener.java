package me.eastcause.duels.listeners;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class OtherEventsListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        event.setCancelled(true);
    }
}
