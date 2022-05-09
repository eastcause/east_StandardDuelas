package me.eastcause.duels.listeners;

import me.eastcause.duels.DuelPlugin;
import me.eastcause.duels.model.Duel;
import me.eastcause.duels.model.User;
import me.eastcause.duels.model.sidebar.Sidebar;
import me.eastcause.duels.repository.DuelManager;
import me.eastcause.duels.repository.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        User user = UserManager.getUser(player);
        if(user == null) {
            UserManager.createUser(player);
        }
        Sidebar sidebar = new Sidebar(player);
        UserManager.getSIDEBARS().put(player.getUniqueId(), sidebar);
        DuelManager.join(player, true);
        for(Duel duel : DuelManager.getDUELS().values()){
            duel.getAccepting().hidePlayer(DuelPlugin.getDuelPlugin(), player);
            duel.getInviter().hidePlayer(DuelPlugin.getDuelPlugin(), player);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        UserManager.getSIDEBARS().remove(event.getPlayer().getUniqueId());
        Duel duel = DuelManager.getDuel(event.getPlayer().getUniqueId());
        if(duel != null){
            event.getPlayer().damage(1000.0);
        }
    }
}
