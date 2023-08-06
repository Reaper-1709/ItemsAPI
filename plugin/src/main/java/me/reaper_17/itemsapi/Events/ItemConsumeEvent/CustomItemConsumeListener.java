package me.reaper_17.itemsapi.Events.ItemConsumeEvent;

import me.reaper_17.itemsapi.Utils.ItemConstructer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class CustomItemConsumeListener implements Listener {

    @EventHandler
    public void onPlayerConsumeCustomItem(PlayerItemConsumeEvent e){
        Player player = e.getPlayer();
        if (ItemConstructer.isCustomItem(e.getItem())){
            CustomItemConsumeEvent customEvent = new CustomItemConsumeEvent(e.getItem(), player);
            if (!customEvent.isCancelled()){
                Bukkit.getServer().getPluginManager().callEvent(customEvent);
            }
            else {
                e.setCancelled(true);
            }
        }
    }
}
