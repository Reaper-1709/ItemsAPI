package me.reaper_17.itemsapi.Events.DropEvent;

import me.reaper_17.itemsapi.Utils.ItemConstructer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class CustomItemDropListener implements Listener {

    @EventHandler
    public void onPlayerDropCustomItem(PlayerDropItemEvent e){
        if (ItemConstructer.isCustomItem((ItemStack) e.getItemDrop())){
            CustomItemDropEvent customEvent = new CustomItemDropEvent(e.getPlayer(), (ItemStack) e.getItemDrop());
            if (!customEvent.isCancelled()){
                Bukkit.getServer().getPluginManager().callEvent(customEvent);
            }
            else {
                e.setCancelled(true);
            }
        }
    }
}
