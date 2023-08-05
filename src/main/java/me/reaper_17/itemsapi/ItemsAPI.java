package me.reaper_17.itemsapi;

import me.reaper_17.itemsapi.Events.ClickEvents.RightClickEvent.CustomItemRightClickListener;
import me.reaper_17.itemsapi.Events.DamageEvent.CustomItemDamagePlayerListener;
import me.reaper_17.itemsapi.Events.DropEvent.CustomItemDropListener;
import me.reaper_17.itemsapi.Events.InventoryEvents.InventoryClickEvent.CustomItemMoveInInventoryListener;
import me.reaper_17.itemsapi.Events.ItemDamageEvent.CustomItemDamageListener;
import me.reaper_17.itemsapi.Events.PlayerKillEvents.CustomItemKillPlayerListener;
import me.reaper_17.itemsapi.Events.ProjectileEvents.ProjectileHitEvent.CustomProjectileHitListener;
import me.reaper_17.itemsapi.Events.ProjectileEvents.ProjectileShootEvent.CustomProjectileShootListener;
import me.reaper_17.itemsapi.Events.SwapEvent.CustomItemSwapListener;
import me.reaper_17.itemsapi.Events.ClickEvents.LeftClickEvent.CustomItemleftClickListener;
import me.reaper_17.itemsapi.Utils.ItemConstructer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class ItemsAPI extends JavaPlugin {
    private static ItemsAPI instance;
    private File configFile;
    private boolean printCustomItemsSerialNumber = true;
    private FileConfiguration config;

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

        if (printCustomItemsSerialNumber) {
            Bukkit.getLogger().info("Current custom items and serial numbers: " + ItemConstructer.getSerialNumbers());
        }
        // to persist lastSerialNumber
        configFile = new File(getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
        if (!configFile.exists()) {
            config.set("lastSerialNumber", 0);
            saveConfig();
        }
        ItemConstructer.initializeKeys(this);
        Bukkit.getLogger().info("ItemsAPI started");
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public int getLastSerialNumber() {
        return config.getInt("lastSerialNumber");
    }

    public void setLastSerialNumber(int value) {
        config.set("lastSerialNumber", value);
        saveConfig();
    }

    public boolean isPrintCustomItemsSerialNumber() {
        return printCustomItemsSerialNumber;
    }

    public void setPrintCustomItemsSerialNumber(boolean printCustomItemsSerialNumber) {
        this.printCustomItemsSerialNumber = printCustomItemsSerialNumber;
    }

    public static ItemsAPI getInstance() {
        return instance;
    }
}
    //todo: add all values to an sql database
    //todo: fix all errors
    //todo: become a dev at fakepixel :tada: