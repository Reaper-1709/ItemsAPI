package me.reaper_17.itemsapi.Events.ProjectileEvents.ProjectileHitEvent;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomProjectileHitEvent extends Event {
    public static final HandlerList handlers = new HandlerList();
    private final Player shooter;
    private final Projectile projectile;
    private final Entity shotEntity;

    public CustomProjectileHitEvent(Player shooter, Projectile projectile, Entity shotEntity) {
        this.shooter = shooter;
        this.projectile = projectile;
        this.shotEntity = shotEntity;
    }

    public Player getShooter() {
        return shooter;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public Entity getShotEntity() {
        return shotEntity;
    }

    private static boolean isCancelled;

    public boolean isCancelled() {
        return isCancelled;
    }

    public static void setCancelled(boolean cancelled){
        isCancelled = cancelled;
    }

    public HandlerList getHandlers(){
        return handlers;
    }
    public static HandlerList getHandlerList(){
        return handlers;
    }
}
