package me.reaper_17.itemsapi.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class CustomItemRightClick extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Player clicker;
    private final ItemStack clickedItem;
    private boolean isCancelled = false;
    private boolean consumeItem = false;

    public CustomItemRightClick(Player clicker, ItemStack clickedItem) {
        this.clicker = clicker;
        this.clickedItem = clickedItem;
    }

    public Player getClicker() {
        return clicker;
    }

    public ItemStack getClickedItem() {
        return clickedItem;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public boolean shouldConsumeItem() {
        return consumeItem;
    }

    public void setConsumeItem(boolean consume) {
        consumeItem = consume;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
