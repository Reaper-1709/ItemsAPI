package me.reaper_17.itemsapi.Utils;

import com.google.common.collect.Multimap;
import me.reaper_17.itemsapi.Data.AttributeConfigurator;
import me.reaper_17.itemsapi.Data.EnchantmentConfigurator;
import me.reaper_17.itemsapi.Data.ItemType;
import me.reaper_17.itemsapi.ItemsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class ItemConstructer {
    private static int lastSerialNumber = 0;
    private static ArrayList<ItemStack> customItemsList = new ArrayList<>();
    private static NamespacedKey SERIAL_NUMBER_KEY;
    private static NamespacedKey ITEM_TYPE_KEY;
    private static NamespacedKey CUSTOM_BOOLEAN_KEY;

    public static void initializeKeys(ItemsAPI plugin) {
        SERIAL_NUMBER_KEY = new NamespacedKey(plugin, "serial_number");
        ITEM_TYPE_KEY = new NamespacedKey(plugin, "item_type");
        CUSTOM_BOOLEAN_KEY = new NamespacedKey(plugin, "custom_boolean");
    }


    public static ItemStack constructItem(ItemType itemType, Material material, String displayName, String[] lore, EnchantmentConfigurator[] enchants, ItemFlag[] flags, AttributeConfigurator[] attributes, boolean isUnbreakable) {
        ItemStack item = null;
        if (ItemsAPI.getInstance() != null) {
            lastSerialNumber = ItemsAPI.getInstance().getLastSerialNumber();
            item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(displayName);
            ArrayList<String> lores = new ArrayList<>();
            Collections.addAll(lores, lore);
            meta.setLore(lores);
            lastSerialNumber = ItemsAPI.getInstance().getLastSerialNumber();

            meta.getPersistentDataContainer().set(ITEM_TYPE_KEY, PersistentDataType.STRING, itemType.name());
            meta.getPersistentDataContainer().set(SERIAL_NUMBER_KEY, PersistentDataType.INTEGER, lastSerialNumber += 1);
            meta.getPersistentDataContainer().set(CUSTOM_BOOLEAN_KEY, PersistentDataType.BYTE, (byte) 1);

            ItemsAPI.getInstance().setLastSerialNumber(lastSerialNumber);

            for (ItemFlag flag : flags) {
                meta.addItemFlags(flag);
            }

            if (enchants != null) {
                for (EnchantmentConfigurator enchantConfigurator : enchants) {
                    if (enchantConfigurator != null) {
                        Enchantment enchantment = enchantConfigurator.getEnchantment();
                        int level = enchantConfigurator.getLevel();
                        boolean forceLevel = enchantConfigurator.isForceLvl();
                        if (enchantment != null && level > 0) {
                            if (forceLevel) {
                                meta.addEnchant(enchantment, level, true);
                            } else {
                                int maxLevel = enchantment.getMaxLevel();
                                if (level > maxLevel) {
                                    level = maxLevel;
                                }
                                meta.addEnchant(enchantment, level, false);
                            }
                        }
                    }
                }
            }

            if (attributes != null) {
                for (AttributeConfigurator attributeConfigurator : attributes) {
                    if (attributeConfigurator != null) {
                        Attribute attribute = attributeConfigurator.getAttribute();
                        double amount = attributeConfigurator.getAttributeModifier().getAmount();
                        AttributeModifier.Operation operation = attributeConfigurator.getAttributeModifier().getOperation();
                        if (attribute != null) {
                            AttributeModifier attributeModifier = new AttributeModifier(
                                    attributeConfigurator.getAttributeModifier().getUniqueId(),
                                    attributeConfigurator.getAttributeModifier().getName(),
                                    amount,
                                    operation
                            );
                            meta.addAttributeModifier(attribute, attributeModifier);
                        }
                    }
                }
            }
            meta.setUnbreakable(isUnbreakable);

            item.setItemMeta(meta);
        }
        else {
            Bukkit.getLogger().warning("ItemsAPI instance is null, unable to construct custom item.");
        }
        Bukkit.getLogger().info("Item successfully constructed");
        return item;
    }

    public static ItemType getItemType(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.getPersistentDataContainer().has(ITEM_TYPE_KEY, PersistentDataType.STRING)) {
            String itemTypeString = meta.getPersistentDataContainer().get(ITEM_TYPE_KEY, PersistentDataType.STRING);
            try {
                return ItemType.valueOf(itemTypeString);
            } catch (IllegalArgumentException ignored) {
                // hail naw :/
                Bukkit.getLogger().warning("not a valid type of ItemType found.");
            }
        }
        return ItemType.DEFAULT;
    }

    //getters:
    public static int getSerialNumber(ItemStack item){
        if (item.getItemMeta().getPersistentDataContainer().has(SERIAL_NUMBER_KEY, PersistentDataType.INTEGER)) {
            return item.getItemMeta().getPersistentDataContainer().get(SERIAL_NUMBER_KEY, PersistentDataType.INTEGER);
        }
        System.out.println(ChatColor.RED + "Error occurred:Item provided is not a custom item");
        return -1;
    }

    public static Material getMaterial(ItemStack item) {
        return item.getType();
    }

    public static String getDisplayName(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            return meta.getDisplayName();
        }
        return null;
    }

    public static String[] getLore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            ArrayList<String> lores = (ArrayList<String>) meta.getLore();
            if (lores != null) {
                return lores.toArray(new String[0]);
            }
        }
        return new String[0];
    }

    public static ItemStack getCustomItem(int serialNumber) {
        for (ItemStack item : customItemsList) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.getPersistentDataContainer().has(SERIAL_NUMBER_KEY, PersistentDataType.INTEGER)) {
                int itemSerialNumber = meta.getPersistentDataContainer().get(SERIAL_NUMBER_KEY, PersistentDataType.INTEGER);
                if (itemSerialNumber == serialNumber) {
                    return item;
                }
            }
        }
        Bukkit.getLogger().warning("couldnt find item");
        return null;
    }


    public static boolean isUnbreakable(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.isUnbreakable();
    }

    public static Map<Enchantment, Integer> getEnchantments(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        return Objects.requireNonNull(meta).getEnchants();
    }

    public static ArrayList<ItemStack> getCustomItemsList(){
        return customItemsList;
    }

    public static Multimap<Attribute, AttributeModifier> getAttributes(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        return Objects.requireNonNull(meta).getAttributeModifiers();
    }

    public static boolean isCustomItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.getPersistentDataContainer().has(CUSTOM_BOOLEAN_KEY, PersistentDataType.BYTE)) {
            try {
                byte customBooleanValue = meta.getPersistentDataContainer().get(CUSTOM_BOOLEAN_KEY, PersistentDataType.BYTE);
                return customBooleanValue == (byte) 1;
            } catch (NullPointerException ignored) {
                // eww
            }
        }
        return false;
    }


    //setters
    public static void setMaterial(ItemStack item, Material material) {
        item.setType(material);
    }

    public static void setDisplayName(ItemStack item, String displayName) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(displayName);
            item.setItemMeta(meta);
        }
    }

    public static void setLore(ItemStack item, String[] lore) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            ArrayList<String> lores = new ArrayList<>();
            Collections.addAll(lores, lore);
            meta.setLore(lores);
            item.setItemMeta(meta);
        }
    }

    public static void setUnbreakable(ItemStack item, boolean isUnbreakable) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setUnbreakable(isUnbreakable);
            item.setItemMeta(meta);
        }
    }

    public static void setEnchantments(ItemStack item, EnchantmentConfigurator[] enchants) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.getEnchants().clear();
            if (enchants != null) {
                for (EnchantmentConfigurator enchantConfigurator : enchants) {
                    if (enchantConfigurator != null) {
                        Enchantment enchantment = enchantConfigurator.getEnchantment();
                        int level = enchantConfigurator.getLevel();
                        boolean forceLevel = enchantConfigurator.isForceLvl();
                        if (enchantment != null && level > 0) {
                            if (forceLevel) {
                                meta.addEnchant(enchantment, level, true);
                            } else {
                                int maxLevel = enchantment.getMaxLevel();
                                if (level > maxLevel) {
                                    level = maxLevel;
                                }
                                meta.addEnchant(enchantment, level, false);
                            }
                        }
                    }
                }
            }
            item.setItemMeta(meta);
        }
    }

    public static void setAttributes(ItemStack item, AttributeConfigurator[] attributes) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.getAttributeModifiers().clear();
            if (attributes != null) {
                for (AttributeConfigurator attributeConfigurator : attributes) {
                    if (attributeConfigurator != null) {
                        Attribute attribute = attributeConfigurator.getAttribute();
                        double amount = attributeConfigurator.getAttributeModifier().getAmount();
                        AttributeModifier.Operation operation = attributeConfigurator.getAttributeModifier().getOperation();
                        if (attribute != null) {
                            AttributeModifier attributeModifier = new AttributeModifier(
                                    attributeConfigurator.getAttributeModifier().getUniqueId(),
                                    attributeConfigurator.getAttributeModifier().getName(),
                                    amount,
                                    operation
                            );
                            meta.addAttributeModifier(attribute, attributeModifier);
                        }
                    }
                }
            }
            item.setItemMeta(meta);
        }
    }

    public static String getSerialNumbers(){
        String items = "";
        for (ItemStack i : customItemsList){
            if (i.getItemMeta().getPersistentDataContainer().has(SERIAL_NUMBER_KEY, PersistentDataType.INTEGER)) {
                items = items + String.valueOf(i.getItemMeta().getPersistentDataContainer().get(SERIAL_NUMBER_KEY, PersistentDataType.INTEGER)) + "-" + i.getItemMeta().getDisplayName();
            }
        }
        items = "{" + items + "}";
        return items;
    }
}
