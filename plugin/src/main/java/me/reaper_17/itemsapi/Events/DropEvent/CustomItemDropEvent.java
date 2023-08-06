package me.reaper_17.itemsapi.Events.DropEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class CustomItemDropEvent extends Event {
    public static final HandlerList handlers = new HandlerList();
    private final Player dropper;
    private final ItemStack droppedItem;
    private static boolean isCancelled;

    public CustomItemDropEvent(Player dropper, ItemStack droppedItem) {
        this.dropper = dropper;
        this.droppedItem = droppedItem;
    }

    public Player getDropper() {
        return dropper;
    }

    public ItemStack getDroppedItem() {
        return droppedItem;
    }

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
