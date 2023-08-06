package me.reaper_17.itemsapi.Events.InventoryEvents.InventoryClickEvent;

import me.reaper_17.itemsapi.Utils.ItemConstructer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class CustomItemMoveInInventoryListener implements Listener {

    @EventHandler
    public void onPlayerMoveCustomItemInInventory(InventoryClickEvent e){
        if (ItemConstructer.isCustomItem(Objects.requireNonNull(e.getCurrentItem()))){
            if (!CustomItemMoveInInventoryEvent.isCancelled()){
                CustomItemMoveInInventoryEvent customEvent = new CustomItemMoveInInventoryEvent((Player) e.getWhoClicked(), e.getCurrentItem(), e.getClickedInventory(), e.getSlot());
                if (!CustomItemMoveInInventoryEvent.isCancelled()){
                    Bukkit.getServer().getPluginManager().callEvent(customEvent);
                }
                else {
                    e.setCancelled(true);
                }
            }
        }
    }
}
