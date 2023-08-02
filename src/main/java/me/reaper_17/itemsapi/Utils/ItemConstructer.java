package me.reaper_17.itemsapi.Utils;

import com.google.common.collect.Multimap;
import me.reaper_17.itemsapi.Data.AttributeConfigurator;
import me.reaper_17.itemsapi.Data.EnchantmentConfigurator;
import me.reaper_17.itemsapi.Data.ItemType;
import me.reaper_17.itemsapi.ItemsAPI;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ItemConstructer {
    private static int lastSerialNumber = 0;
    private static final NamespacedKey SERIAL_NUMBER_KEY = new NamespacedKey(ItemsAPI.getInstance(), "serial_number");
    private static final NamespacedKey ITEM_TYPE_KEY = new NamespacedKey(ItemsAPI.getInstance(), "item_type");
    private static final NamespacedKey CUSTOM_BOOLEAN_KEY = new NamespacedKey(ItemsAPI.getInstance(), "custom_boolean");


    public static ItemStack constructItem(ItemType itemType, Material material, String displayName, String[] lore, EnchantmentConfigurator[] enchants, ItemFlag[] flags, AttributeConfigurator[] attributes, boolean isUnbreakable){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        ArrayList<String> lores = new ArrayList<>();
        Collections.addAll(lores, lore);
        meta.setLore(lores);

        meta.getPersistentDataContainer().set(ITEM_TYPE_KEY, PersistentDataType.STRING, itemType.name());
        meta.getPersistentDataContainer().set(SERIAL_NUMBER_KEY, PersistentDataType.INTEGER, lastSerialNumber += 1);
        meta.getPersistentDataContainer().set(CUSTOM_BOOLEAN_KEY, PersistentDataType.BYTE, (byte) 1);

        for (ItemFlag flag : flags){
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
    public static int getSerialNumber(){
        return lastSerialNumber;
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

    public static boolean isUnbreakable(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.isUnbreakable();
    }

    public static java.util.Map<Enchantment, Integer> getEnchantments(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        return meta.getEnchants();
    }

    public static Multimap<Attribute, AttributeModifier> getAttributes(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        return meta.getAttributeModifiers();
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
}