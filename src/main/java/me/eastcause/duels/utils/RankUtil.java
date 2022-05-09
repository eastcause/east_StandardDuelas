package me.eastcause.duels.utils;

public class RankUtil {

    public static int getPointsForAttacker(int victimPoints){
        if(victimPoints <= 0){
            return 0;
        }
        if(victimPoints <= 50){
            return 1;
        }
        int a = victimPoints / 20;
        if(a > 100){
            a = 100;
        }
        return a;
    }
}
