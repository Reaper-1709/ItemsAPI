package me.reaper_17.itemsapi.Events.DamageEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class CustomItemDamagePlayer extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Player damager;
    private final Player damagedPlayer;
    private final double damage;
    private final ItemStack weapon;
    private static boolean isCancelled;

    public CustomItemDamagePlayer(Player damager, Player damagedPlayer, double damage, ItemStack weapon) {
        this.damager = damager;
        this.damagedPlayer = damagedPlayer;
        this.damage = damage;
        this.weapon = weapon;
    }
    public Player getDamager() {
        return damager;
    }

    public Player getDamagedPlayer() {
        return damagedPlayer;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public static void setCancelled(boolean cancelled){
        isCancelled = cancelled;
    }
    public double getDamage() {
        return damage;
    }

    public ItemStack getWeapon() {
        return weapon;
    }

    public HandlerList getHandlers(){
        return handlers;
    }
    public static HandlerList getHandlerList(){
        return handlers;
    }
}
