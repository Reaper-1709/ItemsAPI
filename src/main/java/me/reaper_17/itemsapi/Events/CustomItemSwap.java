package me.reaper_17.itemsapi.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class CustomItemSwap extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final ItemStack swappedItem;
    private boolean isCancelled = false;

    public CustomItemSwap(Player player, ItemStack swappedItem) {
        this.player = player;
        this.swappedItem = swappedItem;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getSwappedItem() {
        return swappedItem;
    }
}
