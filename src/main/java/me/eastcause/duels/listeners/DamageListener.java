package me.eastcause.duels.listeners;

import me.eastcause.duels.DuelPlugin;
import me.eastcause.duels.model.Duel;
import me.eastcause.duels.model.Kit;
import me.eastcause.duels.model.User;
import me.eastcause.duels.model.sidebar.Sidebar;
import me.eastcause.duels.repository.DuelManager;
import me.eastcause.duels.repository.KitManager;
import me.eastcause.duels.repository.UserManager;
import me.eastcause.duels.utils.FireworkUtil;
import me.eastcause.duels.utils.RankUtil;
import me.eastcause.duels.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            Duel duel = DuelManager.getDuel(player.getUniqueId());
            if(duel == null){
                event.setCancelled(true);
                return;
            }
            if(event.getFinalDamage() >= player.getHealth()){
                event.setDamage(0.0);
                boolean inviter = duel.isInviter(player);
                User ui = UserManager.getUser(duel.getInviter());
                User ua = UserManager.getUser(duel.getAccepting());
                int p = RankUtil.getPointsForAttacker((inviter ? ui.getPoints() : ua.getPoints()));
                ui.setPreFight(System.currentTimeMillis() + 1000);
                ua.setPreFight(System.currentTimeMillis() + 1000);
                if(inviter) {
                    FireworkUtil.fireworks(ua.getPlayer().getLocation(), 3);
                    ui.setPoints((ui.getPoints() - (p / 2)));
                    ua.setPoints((ua.getPoints() + p));
                    ui.setDeaths((ui.getDeaths() + 1));
                    ua.setKills((ua.getKills() + 1));
                }else{
                    FireworkUtil.fireworks(ui.getPlayer().getLocation(), 3);
                    ui.setPoints((ui.getPoints() + p));
                    ua.setPoints((ua.getPoints() - (p/2)));
                    ua.setDeaths((ua.getDeaths() + 1));
                    ui.setKills((ui.getKills() + 1));
                }
                for(Player hidden : duel.getInviter().spigot().getHiddenPlayers()){
                    duel.getInviter().showPlayer(DuelPlugin.getDuelPlugin(), hidden);
                }
                for(Player hidden : duel.getAccepting().spigot().getHiddenPlayers()){
                    duel.getAccepting().showPlayer(DuelPlugin.getDuelPlugin(), hidden);
                }
                Sidebar sidebar = UserManager.getSIDEBARS().get(duel.getInviter().getUniqueId());
                sidebar.update();
                Sidebar sidebar2 = UserManager.getSIDEBARS().get(duel.getAccepting().getUniqueId());
                sidebar2.update();
                Bukkit.broadcastMessage(Utils.color("&6Gracz: &7"+(inviter ? duel.getAccepting().getName() : duel.getInviter().getName())+" &8(&a+&7"+p+"&8) &6wygral pojedynek z graczem: &7"+(inviter ? duel.getInviter().getName() : duel.getAccepting().getName())+" &8(&c-&7"+(p/2)+"&8) &8(&6"+(inviter ? Utils.round(duel.getAccepting().getHealth()/2, 1) : Utils.round(duel.getInviter().getHealth()/2, 1))+"&c❤&8)"));
                DuelManager.removeBorder(duel.getAccepting());
                DuelManager.removeBorder(duel.getInviter());
                DuelManager.join(duel.getAccepting(), false);
                DuelManager.join(duel.getInviter(), false);
                duel.throwPlayers();
                DuelManager.removeDuel(duel);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1F, 1F);
            }
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player){
            Player player = (Player) event.getDamager();
            if(event.getEntity() instanceof Player){
                event.setCancelled(true);
                Player entity = (Player) event.getEntity();
                User u = UserManager.getUser(player);
                User d = UserManager.getUser(entity);
                if(u.getPreFight() >= System.currentTimeMillis()){
                    return;
                }
                if(d.getPreFight() >= System.currentTimeMillis()){
                    return;
                }
                Duel pduel = DuelManager.getDuel(player.getUniqueId());
                if(pduel != null){
                    if(pduel.isFighter(player)){
                        if(pduel.getSecondsToStart() <= 0){
                            event.setCancelled(false);
                        }
                    }
                    return;
                }
                Duel oduel = DuelManager.getDuel(entity.getUniqueId());
                if(oduel != null){
                    Utils.sendMessage(player, "&cTen gracz juz sie pojedynkuje!");
                    return;
                }
                Kit kit = KitManager.getKitBySlot((player.getInventory().getHeldItemSlot() + 1));
                if(kit == null){
                    Utils.sendMessage(player, "&aWybierz kit aby zaprosic gracza do pojedynku!");
                    return;
                }
                if(d.getInvites().containsKey(u.getUuid())) {
                    int i = d.getInvites().get(u.getUuid());
                    Kit k = KitManager.getKitBySlot(i);
                    d.getInvites().clear();
                    u.getInvites().clear();
                    Utils.sendMessage(player, "&aZaakceptowales pojedynek na: " + k.getName());
                    Utils.sendMessage(entity, "&aGracz: &6"+player.getName()+" &azaakceptowal pojedynek!");
                    entity.playSound(entity.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
                    player.playSound(entity.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
                    DuelManager.createDuel(entity, player, 5, entity.getLocation().getBlock().getLocation().clone(), k);
                }else{
                    int k = u.getInvites().getOrDefault(d.getUuid(), -1);
                    if(k == -1) {
                        Utils.sendMessage(player, "&aZaprosiles gracza: &6" + entity.getName() + " &ado pojedynku na zestaw: &7" + kit.getName());
                        Utils.sendMessage(entity, "&aOtrzymales zaproszenie od: &6" + player.getName() + " &ado pojedynku na zestaw: &7" + kit.getName());
                        u.getInvites().put(d.getUuid(), kit.getSlot());
                    }else{
                        Utils.sendMessage(player, "&cZaprosiles juz tego gracza na pojedynek!");
                    }
                    return;
                }
            }
        }
    }
}
