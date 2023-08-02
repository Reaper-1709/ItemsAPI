package me.reaper_17.itemsapi.Events.DamageEvent;

import me.reaper_17.itemsapi.Utils.ItemConstructer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class CustomItemDamagePlayerListener implements Listener {

    @EventHandler
    public void onPlayerAttackWithCustomItem(EntityDamageByEntityEvent e){
        if (e.getEntity().getType() == EntityType.PLAYER){
            Player damagedPlayer = (Player) e.getEntity();
            Entity damagerE = e.getDamager();
            if (damagerE instanceof Player){
                Player damager = (Player) e.getDamager();
                if (ItemConstructer.isCustomItem(damager.getInventory().getItemInMainHand())){
                    CustomItemDamagePlayer customEvent = new CustomItemDamagePlayer(damager, damagedPlayer, e.getDamage(), damager.getInventory().getItemInMainHand());
                    if (!customEvent.isCancelled()){
                        Bukkit.getServer().getPluginManager().callEvent(customEvent);
                    }
                    else {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
