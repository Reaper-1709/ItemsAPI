package me.reaper_17.itemsapi.Events.PlayerKillEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class CustomItemKillPlayerEvent extends Event {
    public static final HandlerList handlers = new HandlerList();
    private final Player killer;
    private final Player killedPlayer;
    private final ItemStack item;

    public CustomItemKillPlayerEvent(Player killer, Player killedPlayer, ItemStack item) {
        this.killer = killer;
        this.killedPlayer = killedPlayer;
        this.item = item;
    }


    public Player getKiller() {
        return killer;
    }

    public Player getKilledPlayer() {
        return killedPlayer;
    }

    public ItemStack getItem() {
        return item;
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
