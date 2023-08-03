package me.reaper_17.itemsapi.Events.ProjectileEvents.ProjectileShootEvent;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomProjectileShootEvent extends Event {
    public static final HandlerList handlers = new HandlerList();
    private final Player shooter;
    private final Projectile projectile;

    public CustomProjectileShootEvent(Player shooter, Projectile projectile) {
        this.shooter = shooter;
        this.projectile = projectile;
    }

    public Player getShooter() {
        return shooter;
    }

    public Projectile getProjectile() {
        return projectile;
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
