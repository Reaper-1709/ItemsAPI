package me.reaper_17.itemsapi;

import me.reaper_17.itemsapi.Events.ClickEvents.RightClickEvent.CustomItemRightClickListener;
import me.reaper_17.itemsapi.Events.DamageEvent.CustomItemDamagePlayerEvent;
import me.reaper_17.itemsapi.Events.DamageEvent.CustomItemDamagePlayerListener;
import me.reaper_17.itemsapi.Events.DropEvent.CustomItemDropListener;
import me.reaper_17.itemsapi.Events.InventoryEvents.InventoryClickEvent.CustomItemMoveInInventoryListener;
import me.reaper_17.itemsapi.Events.ItemDamageEvent.CustomItemDamageListener;
import me.reaper_17.itemsapi.Events.PlayerKillEvents.CustomItemKillPlayerListener;
import me.reaper_17.itemsapi.Events.ProjectileEvents.ProjectileHitEvent.CustomProjectileHitListener;
import me.reaper_17.itemsapi.Events.ProjectileEvents.ProjectileShootEvent.CustomProjectileShootListener;
import me.reaper_17.itemsapi.Events.SwapEvent.CustomItemSwapListener;
import me.reaper_17.itemsapi.Events.ClickEvents.LeftClickEvent.CustomItemleftClickListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemsAPI extends JavaPlugin {
    private static ItemsAPI instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        Bukkit.getServer().getPluginManager().registerEvents(new CustomItemRightClickListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CustomItemSwapListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CustomItemleftClickListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CustomItemDamagePlayerListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CustomItemDropListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CustomItemMoveInInventoryListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CustomItemleftClickListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CustomItemDamageListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CustomItemKillPlayerListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CustomProjectileHitListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CustomProjectileShootListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ItemsAPI getInstance() {
        return instance;
    }
}
