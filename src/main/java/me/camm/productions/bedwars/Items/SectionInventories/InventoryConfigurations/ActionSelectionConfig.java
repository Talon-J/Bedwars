package me.camm.productions.bedwars.Items.SectionInventories.InventoryConfigurations;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum ActionSelectionConfig {

    TRACKER(15, Material.COMPASS, ChatColor.WHITE+"Tracker"),
    CHAT(11, Material.EMERALD, ChatColor.WHITE+"Quick Chat");

    private final int slot;
    private final Material mat;
    private final String name;

    ActionSelectionConfig(int slot, Material mat, String name) {

        this.slot = slot;
        this.mat = mat;
        this.name = name;


    }

    public int getSlot() {
        return slot;
    }

    public Material getMat() {
        return mat;
    }

    public String getName() {
        return name;
    }
}
