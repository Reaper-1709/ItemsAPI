package me.reaper_17.itemsapi.Events.ProjectileEvents.ProjectileShootEvent;

import me.reaper_17.itemsapi.Utils.ItemConstructer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class CustomProjectileShootListener implements Listener {
    @EventHandler
    public void onPlayerShootCustomProjectile(ProjectileLaunchEvent e){
        Player player = (Player) e.getEntity().getShooter();
        Projectile projectile = e.getEntity();
        if (ItemConstructer.isCustomItem(player.getInventory().getItemInMainHand())){
            CustomProjectileShootEvent customEvent = new CustomProjectileShootEvent(player, projectile);
            if (!customEvent.isCancelled()){
                Bukkit.getServer().getPluginManager().callEvent(customEvent);
            }
            else {
                e.setCancelled(true);
            }
        }
    }
}
