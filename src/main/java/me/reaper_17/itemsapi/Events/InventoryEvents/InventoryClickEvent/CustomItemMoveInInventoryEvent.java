package me.reaper_17.itemsapi.Events.InventoryEvents.InventoryClickEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CustomItemMoveInInventoryEvent extends Event {
    public static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final ItemStack itemStack;
    private final Inventory playerInventory;
    private final int slot;

    public CustomItemMoveInInventoryEvent(Player player, ItemStack itemStack, Inventory playerInventory, int slot) {
        this.player = player;
        this.itemStack = itemStack;
        this.playerInventory = playerInventory;
        this.slot = slot;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Inventory getPlayerInventory() {
        return playerInventory;
    }

    public int getSlot(){
        return slot;
    }

    private static boolean isCancelled;

    public static boolean isCancelled() {
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
