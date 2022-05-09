package me.eastcause.duels.repository;

import lombok.Getter;
import lombok.SneakyThrows;
import me.eastcause.duels.DuelPlugin;
import me.eastcause.duels.model.User;
import me.eastcause.duels.model.sidebar.Sidebar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserManager {

    @Getter private static ConcurrentMap<UUID, User> USERS = new ConcurrentHashMap<>();
    @Getter private static ConcurrentMap<UUID, Sidebar> SIDEBARS = new ConcurrentHashMap<>();
    @Getter private static Set<UUID> TO_SAVE = new HashSet<>();

    public static User createUser(Player player){
        User user = new User(player);
        getUSERS().put(user.getUuid(), user);
        DuelPlugin.getSql().update("INSERT INTO `duel_users`(`uuid`, `name`, `points`, `kills`, `deaths`) VALUES ('"+player.getUniqueId().toString()+"','"+player.getName()+"','"+user.getPoints()+"','"+user.getKills()+"','"+user.getDeaths()+"')");
        return user;
    }

    public static void load(){
        try {
            final ResultSet rs = DuelPlugin.getSql().query("SELECT * FROM `duel_users`");
            while (rs.next()) {
                User user = new User(rs);
                getUSERS().put(user.getUuid(), user);
            }
            rs.close();
            System.out.println("Loaded " + getUSERS().size() + " users");
        } catch (SQLException e) {
            System.err.println("Can not load user Error " + e.getMessage());
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static void saveAll(){
        Connection connection = DuelPlugin.getSql().getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE `duel_users` SET `points`=?,`kills`=?,`deaths`=? WHERE `uuid`=?");
        int saved = 0;
        int i = 0;
        for(UUID uuid : getTO_SAVE()){
            if(i >= 50){
                statement.executeBatch();
                statement.close();
                statement = connection.prepareStatement("UPDATE `duel_users` SET `points`=?,`kills`=?,`deaths`=? WHERE `uuid`=?");
                i = 0;
            }
            User user = UserManager.getUser(uuid);
            statement.setInt(1, user.getPoints());
            statement.setInt(2, user.getKills());
            statement.setInt(3, user.getDeaths());
            statement.setString(4, user.getUuid().toString());
            statement.addBatch();
            i++;
            saved ++;
        }
        if(i != 0){
            statement.executeBatch();
            statement.close();
        }
        getTO_SAVE().clear();
        System.out.println("SAVED "+saved+" USERS!");
    }

    private static User getUser(UUID uuid) {
        return getUSERS().getOrDefault(uuid, null);
    }

    public static User getUser(Player player){
        User user = getUSERS().get(player.getUniqueId());
        if(user != null) return user;
        return createUser(player);
    }

}
