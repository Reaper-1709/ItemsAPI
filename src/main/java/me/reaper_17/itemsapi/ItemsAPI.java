package me.reaper_17.itemsapi;

import me.reaper_17.itemsapi.Events.ClickEvents.RightClickEvent.CustomItemRightClickListener;
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
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ItemsAPI getInstance() {
        return instance;
    }
}
