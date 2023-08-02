package me.reaper_17.itemsapi.Events.ClickEvents.LeftClickEvent;

import me.reaper_17.itemsapi.Events.ClickEvents.RightClickEvent.CustomItemRightClick;
import me.reaper_17.itemsapi.Utils.ItemConstructer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CustomItemleftClickListener implements Listener {

    @EventHandler
    public void onCustomItemRightClick(PlayerInteractEvent e){
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
            if (ItemConstructer.isCustomItem(e.getPlayer().getInventory().getItemInMainHand())){
                CustomItemRightClick customEvent = new CustomItemRightClick(e.getPlayer(), e.getPlayer().getInventory().getItemInMainHand());

                if (!customEvent.isCancelled()) {
                    Bukkit.getServer().getPluginManager().callEvent(customEvent);
                    e.setCancelled(true);
                }
                else {
                    e.setCancelled(true);
                }

                if (customEvent.shouldConsumeItem()) {
                    e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1); // Consume the item
                }
            }
        }
    }
}
