package me.reaper_17.itemsapi.Data;

import org.bukkit.enchantments.Enchantment;

public class EnchantmentConfigurator {
    private Enchantment enchantment;
    private int level;
    private boolean forceLvl;

    public EnchantmentConfigurator(Enchantment enchantment, int level, boolean forceLvl) {
        this.enchantment = enchantment;
        this.level = level;
        this.forceLvl = forceLvl;
    }


    public Enchantment getEnchantment() {
        return enchantment;
    }

    public int getLevel() {
        return level;
    }

    public boolean isForceLvl() {
        return forceLvl;
    }

    public void setEnchantment(Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setForceLvl(boolean forceLvl) {
        this.forceLvl = forceLvl;
    }
}
