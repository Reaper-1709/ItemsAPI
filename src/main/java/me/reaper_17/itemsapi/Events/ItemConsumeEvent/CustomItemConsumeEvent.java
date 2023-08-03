package me.reaper_17.itemsapi.Events.ItemConsumeEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class CustomItemConsumeEvent extends Event {
    public static final HandlerList handlers = new HandlerList();
    private final ItemStack itemStack;
    private final Player consumer;

    public CustomItemConsumeEvent(ItemStack itemStack, Player consumer) {
        this.itemStack = itemStack;
        this.consumer = consumer;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Player getConsumer() {
        return consumer;
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
