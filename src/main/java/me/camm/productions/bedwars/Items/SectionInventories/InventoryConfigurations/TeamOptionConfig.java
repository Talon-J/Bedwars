package me.camm.productions.bedwars.Items.SectionInventories.InventoryConfigurations;

import me.camm.productions.bedwars.Util.Helpers.ItemHelper;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum TeamOptionConfig {

    RETURN(Material.ARROW, new int[]{31}, ChatColor.WHITE+"Return to Selection"),
    SEPARATOR(Material.STAINED_GLASS_PANE,new int[]{18,19,20,21,22,23,24,25,26},ChatColor.DARK_GRAY+"\u21e7Options "+ChatColor.GRAY+"\u21e9 Return");

    private final Material mat;
    private final int[] slots;
    private final String name;

    TeamOptionConfig(Material mat, int[] slots, String name) {
        this.name = name;
        this.mat = mat;
        this.slots = slots;
    }

    public Material getMat() {
        return mat;
    }

    public int[] getSlots() {
        return slots;
    }

    public String getName() {
        return name;
    }

    public ItemStack create(){
        if (mat == Material.STAINED_GLASS_PANE){
            return ItemHelper.addName(ItemHelper.createColoredGlass(mat, DyeColor.GRAY),name);
        }
        else return ItemHelper.toSimpleItem(mat, name);
    }
}
