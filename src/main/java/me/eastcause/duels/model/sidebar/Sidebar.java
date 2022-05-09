package me.eastcause.duels.model.sidebar;

import lombok.Data;
import me.eastcause.duels.model.User;
import me.eastcause.duels.repository.UserManager;
import me.eastcause.duels.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class Sidebar {

    private Player player;
    private Objective objective;
    private HashMap<Team, TeamScore> scores = new LinkedHashMap<>();

    public Sidebar(Player player){
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        player.setScoreboard(scoreboard);
        this.player = player;
        sendSidebar();
    }

    private void sendSidebar(){
        this.objective = null;
        for(Team team : getPlayer().getScoreboard().getTeams()){
            if(team.getName().startsWith("eastscore")){
                team.unregister();
            }
        }
        getScores().clear();
        Objective objective = getPlayer().getScoreboard().getObjective("eaststats");
        if(objective != null){
            objective.unregister();
        }
        setObjective(getPlayer().getScoreboard().registerNewObjective("eastscore", "Dummy"));
        getObjective().setDisplaySlot(DisplaySlot.SIDEBAR);
        getObjective().setDisplayName(Utils.color("&e&lSTANDARD"));
        newTeam("eastscore1", "&1", -1, TeamScore.EMPTY);
        newTeam("eastscore2", "&f Nick: ", -2, TeamScore.NICK);
        newTeam("eastscore3", "&2", -3, TeamScore.EMPTY);
        newTeam("eastscore4", "&f Punkty: ", -4, TeamScore.POINTS);
        newTeam("eastscore5", "&f Kille: ", -5, TeamScore.KILLS);
        newTeam("eastscore6", "&f Dedy: ", -6, TeamScore.DEATHS);
        newTeam("eastscore7", "&f K/D ratio: ", -7, TeamScore.KD);
        newTeam("eastscore8", "&3", -8, TeamScore.EMPTY);
        newTeam("eastscore9", "&eeastcause.pl", -9, TeamScore.EMPTY);
    }

    public Team newTeam(String id, String text, int score, TeamScore scoreValue) {
        User user = UserManager.getUser(player);
        Team team = getPlayer().getScoreboard().registerNewTeam(id);
        team.addEntry(Utils.color(text));
        team.setPrefix("");
        if(scoreValue.equals(TeamScore.NICK)) {
            if(team.getSuffix().length() == 0){
                team.setSuffix(Utils.color("&6" + player.getName()));
            }
        }else if(scoreValue.equals(TeamScore.POINTS)){
            team.setSuffix(Utils.color("&b" + user.getPoints()));
        }else if(scoreValue.equals(TeamScore.KILLS)){
            team.setSuffix(Utils.color("&b" + user.getKills()));
        }else if(scoreValue.equals(TeamScore.DEATHS)){
            team.setSuffix(Utils.color("&b" + user.getDeaths()));
        }else if(scoreValue.equals(TeamScore.KD)){
            team.setSuffix(Utils.color("&b" + user.getKd()));
        }else{
            team.setSuffix(Utils.color("&7"));
        }
        getObjective().getScore(Utils.color(text)).setScore(score);
        getScores().put(team, scoreValue);
        return team;
    }

    public void update(){
        for(Map.Entry<Team, TeamScore> entry : getScores().entrySet()){
            TeamScore scoreValue = entry.getValue();
            Team team = entry.getKey();
            User user = UserManager.getUser(player);
            if(scoreValue.equals(TeamScore.POINTS)){
                team.setSuffix(Utils.color("&b" + user.getPoints()));
            }else if(scoreValue.equals(TeamScore.KILLS)){
                team.setSuffix(Utils.color("&b" + user.getKills()));
            }else if(scoreValue.equals(TeamScore.DEATHS)){
                team.setSuffix(Utils.color("&b" + user.getDeaths()));
            }else if(scoreValue.equals(TeamScore.KD)) {
                team.setSuffix(Utils.color("&b" + user.getKd()));
            }
        }
    }

}
