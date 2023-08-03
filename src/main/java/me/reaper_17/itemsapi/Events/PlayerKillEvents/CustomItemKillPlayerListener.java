package me.reaper_17.itemsapi.Events.PlayerKillEvents;

import me.reaper_17.itemsapi.Utils.ItemConstructer;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class CustomItemKillPlayerListener implements Listener {

    @EventHandler
    public void onPlayerKillPlayerUsingCustomItem(PlayerDeathEvent e){
        Player killedPlayer = e.getEntity();
        Player killer = e.getEntity().getKiller();
        if (killer.getType() == EntityType.PLAYER){
            if (ItemConstructer.isCustomItem(killer.getInventory().getItemInMainHand())){
                CustomItemKillPlayerEvent customEvent = new CustomItemKillPlayerEvent(killer, killedPlayer, killer.getInventory().getItemInMainHand());
                if (!customEvent.isCancelled()){
                    Bukkit.getServer().getPluginManager().callEvent(customEvent);
                }
                else {
                    // cant cancel this event xO
                }
            }
        }
    }
}
