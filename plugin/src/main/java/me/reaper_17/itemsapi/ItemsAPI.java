package me.reaper_17.itemsapi;

import me.reaper_17.itemsapi.Data.AttributeConfigurator;
import me.reaper_17.itemsapi.Data.EnchantmentConfigurator;
import me.reaper_17.itemsapi.Data.ItemType;
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
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import static org.bukkit.persistence.PersistentDataType.INTEGER;

public final class ItemsAPI extends JavaPlugin {
    private static ItemsAPI instance;
    private File configFile;
    private boolean printCustomItemsSerialNumber = true;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        String jdbcUrl = "jdbc:mysql://localhost:3306/itemsapi";
        String user = "root";
        String password = "priyan170109";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, user, password)) {
            String selectQuery = "SELECT * FROM custom_items";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int serialNumber = resultSet.getInt("SerialNumber");
                    String itemType = resultSet.getString("ItemType");
                    String material = resultSet.getString("Material");
                    String displayName = resultSet.getString("DisplayName");
                    String lore = resultSet.getString("Lore");
                    String enchants = resultSet.getString("Enchants");
                    String itemFlags = resultSet.getString("ItemFlags");
                    String attributes = resultSet.getString("Attributes");
                    boolean isUnbreakable = resultSet.getBoolean("IsUnbreakable");

                    lore = lore.replace("{", "").replace("}", "");
                    String[] lores = lore.split(", ");

                    enchants = enchants.replace("{", "").replace("}", "");
                    String[] enchConfStr = enchants.split(", ");

                    String[] attrConfStr = attributes.split(", ");
                    AttributeConfigurator[] attributesArray = new AttributeConfigurator[attrConfStr.length];
                    int attrIndex = 0;
                    for (String attr : attrConfStr) {
                        String[] attrParts = attr.split("-");
                        if (attrParts.length == 3) {
                            String attrName = attrParts[0];
                            double amount = Double.parseDouble(attrParts[1]);
                            AttributeModifier.Operation operation = AttributeModifier.Operation.valueOf(attrParts[2]);
                            AttributeConfigurator attrConfig = new AttributeConfigurator(
                                    Attribute.valueOf(attrName),
                                    new AttributeModifier(UUID.randomUUID(), "", amount, operation)
                            );
                            attributesArray[attrIndex++] = attrConfig;
                        }
                    }

                    EnchantmentConfigurator[] enchantsArray = new EnchantmentConfigurator[enchConfStr.length];
                    int enchIndex = 0;
                    for (String ench : enchConfStr) {
                        String[] enchParts = ench.split("-");
                        if (enchParts.length == 3) {
                            String enchName = enchParts[0];
                            int level = Integer.parseInt(enchParts[1]);
                            boolean forceLevel = enchParts[2].equals("forced");
                            EnchantmentConfigurator enchConfig = new EnchantmentConfigurator(
                                    Enchantment.getByName(enchName),
                                    level,
                                    forceLevel
                            );
                            enchantsArray[enchIndex++] = enchConfig;
                        }
                    }

                    Material mat = Material.valueOf(material);
                    ItemType itemTypeEnum = ItemType.valueOf(itemType);
                    ItemFlag[] flags = Arrays.stream(itemFlags.split(", ")).map(ItemFlag::valueOf).toArray(ItemFlag[]::new);

                    ItemStack customItem = ItemConstructer.constructItem(itemTypeEnum, mat, displayName, lores, enchantsArray, flags, attributesArray, isUnbreakable);
                }
            }
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Unable to connect to database " + e.getMessage());
        }

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

        ItemConstructer.initializeKeys(this);
        Bukkit.getLogger().info("ItemsAPI started");
        Bukkit.getLogger().info(instance.toString());
    }
    public void onDisable() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/itemsapi";
        String user = "root";
        String password = "priyan170109";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, user, password)) {
            for (ItemStack i : ItemConstructer.getCustomItemsList()) {
                ItemMeta meta = i.getItemMeta();
                int serialNumber = Objects.requireNonNull(meta).getPersistentDataContainer().get(new NamespacedKey(this, "serial_number"), INTEGER);
                String itemType = meta.getPersistentDataContainer().get(new NamespacedKey(this, "item_type"), PersistentDataType.STRING);
                String material = i.getType().toString();
                String displayName = meta.getDisplayName();
                String lore = "{}";
                if (meta.hasLore()) {
                    lore = String.join(", ", meta.getLore());
                }
                String enchants = meta.getPersistentDataContainer().get(new NamespacedKey(this, "enchants_conf"), PersistentDataType.STRING);
                String itemFlags = String.join(", ", meta.getItemFlags().stream().map(ItemFlag::toString).toArray(String[]::new));
                String attributes = meta.getPersistentDataContainer().get(new NamespacedKey(this, "attributes_conf"), PersistentDataType.STRING);
                boolean isUnbreakable = meta.isUnbreakable();

                String insertQuery = "INSERT INTO custom_items (serial_number, item_type, material, display_name, lore, enchants, item_flags, attributes, is_unbreakable) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setInt(1, serialNumber);
                    preparedStatement.setString(2, itemType);
                    preparedStatement.setString(3, material);
                    preparedStatement.setString(4, displayName);
                    preparedStatement.setString(5, lore);
                    preparedStatement.setString(6, enchants);
                    preparedStatement.setString(7, itemFlags);
                    preparedStatement.setString(8, attributes);
                    preparedStatement.setBoolean(9, isUnbreakable);

                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Unable to connect to database + " + e.getMessage());
        }
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
    //todo: fix all errors