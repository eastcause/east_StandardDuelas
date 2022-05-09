package com.github.yannicklamprecht.worldborder.v1_16_R3;

import com.github.yannicklamprecht.worldborder.api.AbstractWorldBorder;
import com.github.yannicklamprecht.worldborder.api.Position;
import com.github.yannicklamprecht.worldborder.api.WorldBorderAction;
import net.minecraft.server.v1_16_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldBorder;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class WorldBorder extends AbstractWorldBorder {

    private final net.minecraft.server.v1_16_R3.WorldBorder handle;

    public WorldBorder(Player player) {
        this(new net.minecraft.server.v1_16_R3.WorldBorder());
        this.handle.world = ((CraftWorld)player.getWorld()).getHandle();
    }

    public WorldBorder(World world) {
        this(((CraftWorld) world).getHandle().getWorldBorder());
    }

    private WorldBorder(net.minecraft.server.v1_16_R3.WorldBorder worldBorder) {
        super(
                () -> new Position(worldBorder.getCenterX(), worldBorder.getCenterZ()),
                (position -> worldBorder.setCenter(position.getX(), position.getZ())),
                () -> new Position(worldBorder.e(), worldBorder.f()),
                () -> new Position(worldBorder.g(), worldBorder.h()),
                worldBorder::getSize,
                worldBorder::setSize,
                () -> (int) worldBorder.getDamageBuffer(),
                worldBorder::setDamageBuffer,
                worldBorder::setDamageBuffer,
                worldBorder::getDamageAmount,
                worldBorder::setDamageAmount,
                worldBorder::getWarningTime,
                worldBorder::setWarningTime,
                worldBorder::getWarningDistance,
                worldBorder::setWarningDistance,
                (Location location) -> worldBorder.isInBounds(new ChunkCoordIntPair(location.getBlockX(), location.getBlockZ())),
                worldBorder::transitionSizeBetween
        );
        this.handle = worldBorder;
    }

    @Override
    public void send(Player player, WorldBorderAction worldBorderAction) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldBorder(handle, PacketPlayOutWorldBorder.EnumWorldBorderAction.valueOf(worldBorderAction.name())));
    }
}