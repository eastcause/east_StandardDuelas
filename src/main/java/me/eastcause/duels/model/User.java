package me.eastcause.duels.model;

import lombok.Data;
import me.eastcause.duels.repository.UserManager;
import me.eastcause.duels.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class User {

    private UUID uuid;
    private String name;
    private int points;
    private int kills;
    private int deaths;
    private Map<UUID, Integer> invites = new HashMap<>();
    private long preFight;

    public User(Player player){
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        this.points = 500;
        this.kills = 0;
        this.deaths = 0;
    }

    public User(ResultSet rs) {
        try {
            this.uuid = UUID.fromString(rs.getString("uuid"));
            this.name = rs.getString("name");
            this.points = rs.getInt("points");
            this.kills = rs.getInt("kills");
            this.deaths = rs.getInt("deaths");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setPoints(int points) {
        this.points = points;
        if(!UserManager.getTO_SAVE().contains(uuid)){
            UserManager.getTO_SAVE().add(uuid);
        }
    }

    public void setKills(int kills) {
        this.kills = kills;
        if(!UserManager.getTO_SAVE().contains(uuid)){
            UserManager.getTO_SAVE().add(uuid);
        }
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
        if(!UserManager.getTO_SAVE().contains(uuid)){
            UserManager.getTO_SAVE().add(uuid);
        }
    }

    public double getKd() {
        if (this.getKills() == 0 && this.getDeaths() == 0) {
            return 0.0;
        }
        else if (this.getKills() > 0 && this.getDeaths() == 0) {
            return this.getKills() + 0.0;
        }
        else if (this.getDeaths() > 0 && this.getKills() == 0) {
            return -this.getDeaths() + 0.0;
        }
        else {
            return Utils.round((double) this.getKills() / (double) this.getDeaths(), 2);
        }
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(uuid);
    }
}
