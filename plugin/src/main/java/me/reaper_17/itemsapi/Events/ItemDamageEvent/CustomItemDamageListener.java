package me.reaper_17.itemsapi.Events.ItemDamageEvent;

import me.reaper_17.itemsapi.Utils.ItemConstructer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class CustomItemDamageListener implements Listener {
    @EventHandler
    public void onPlayerDamageCustomItem(PlayerItemDamageEvent e){
        if (ItemConstructer.isCustomItem(e.getItem())){
            CustomItemDamageEvent customEvent = new CustomItemDamageEvent(e.getItem(), e.getPlayer(), e.getDamage());
            if (!customEvent.isCancelled()){
                Bukkit.getServer().getPluginManager().callEvent(customEvent);
            }
            else {
                e.setCancelled(true);
            }
        }
    }
}
