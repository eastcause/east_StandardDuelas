package com.github.yannicklamprecht.worldborder.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface IWorldBorder {

    Position getCenter();

    void setCenter(Position center);

    Position getMin();

    Position getMax();

    double getSize();

    void setSize(double radius);

    int getDamageBufferInBlocks();

    void setDamageBufferInBlocks(int blocks);

    void setDamageBufferInBlocks(double blocks);

    double getDamagePerSecondPerBlock();

    void setDamagePerSecondPerBlock(double damage);

    /**
     * Typing error use {@link IWorldBorder#setDamagePerSecondPerBlock(double)}
     *
     * @param damage the damage that should be dealt each second per block
     */
    @Deprecated
    void setDamagerPerSecondPerBlock(double damage);

    int getWarningTimerInSeconds();

    void setWarningTimeInSeconds(int seconds);

    int getWarningDistanceInBlocks();

    void setWarningDistanceInBlocks(int blocks);

    boolean isInBounds(Location location);

    void lerp(double oldSize, double newSize, long time);

    void send(Player player, WorldBorderAction worldBorderAction);
}
