package me.camm.productions.bedwars.Items.SectionInventories.InventoryConfigurations;

import org.bukkit.Material;

public enum ActionSelectionConfig {

    TRACKER(11, Material.COMPASS, "Tracker"),
    CHAT(15, Material.FEATHER, "Quick Chat");

    private int slot;
    private Material mat;
    private String name;

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
