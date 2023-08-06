package me.reaper_17.itemsapi.Events.ItemDamageEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class CustomItemDamageEvent extends Event {
    public static final HandlerList handlers = new HandlerList();
    private final ItemStack item;
    private final Player player;
    private final int damage;

    public CustomItemDamageEvent(ItemStack item, Player player, int damage) {
        this.item = item;
        this.player = player;
        this.damage = damage;
    }

    public ItemStack getItem() {
        return item;
    }

    public Player getPlayer() {
        return player;
    }

    public int getDamage() {
        return damage;
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
