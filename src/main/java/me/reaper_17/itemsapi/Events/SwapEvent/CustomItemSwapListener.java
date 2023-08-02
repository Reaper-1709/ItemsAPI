package me.reaper_17.itemsapi.Events.SwapEvent;

import me.reaper_17.itemsapi.Utils.ItemConstructer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class CustomItemSwapListener implements Listener {

    @EventHandler
    public void onPlayerSwapItem(PlayerItemHeldEvent e){
        Player player = e.getPlayer();
        ItemStack newItem = player.getInventory().getItem(e.getNewSlot());
        if (ItemConstructer.isCustomItem(newItem)){
            CustomItemSwap customEvent = new CustomItemSwap(player, newItem);
            if (!customEvent.isCancelled()) {
                Bukkit.getServer().getPluginManager().callEvent(customEvent);
            }
            else {
                e.setCancelled(true);
            }
        }
    }
}
