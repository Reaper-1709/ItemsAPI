package me.reaper_17.itemsapi.Events.ProjectileEvents.ProjectileHitEvent;

import me.reaper_17.itemsapi.Utils.ItemConstructer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class CustomProjectileHitListener implements Listener {

    @EventHandler
    public void onCustomProjectileHitEntity(ProjectileHitEvent e){
        Projectile projectile = e.getEntity();
        Player shooter = (Player) e.getEntity().getShooter();
        Entity shotEntity = e.getHitEntity();
        if (ItemConstructer.isCustomItem(shooter.getInventory().getItemInMainHand())){
            CustomProjectileHitEvent customEvent = new CustomProjectileHitEvent(shooter, projectile, shotEntity);
            if (!customEvent.isCancelled()){
                Bukkit.getServer().getPluginManager().callEvent(customEvent);
            }
            else {
                e.setCancelled(true);
            }
        }
    }
}
