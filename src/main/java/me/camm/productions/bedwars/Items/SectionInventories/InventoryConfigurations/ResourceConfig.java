package me.camm.productions.bedwars.Items.SectionInventories.InventoryConfigurations;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum ResourceConfig {

    EMERALD(ChatColor.GREEN+"Emerald", Material.EMERALD),
    DIAMOND(ChatColor.AQUA+"Diamond",Material.DIAMOND),
    GOLD(ChatColor.YELLOW+"Gold Ingot", Material.GOLD_INGOT),
    IRON(ChatColor.GRAY+"Iron Ingot", Material.IRON_INGOT);

    private final String name;
    private final Material mat;

    ResourceConfig(String name, Material mat) {
        this.name = name;
        this.mat = mat;
    }

    public String getName() {
        return name;
    }

    public Material getMat() {
        return mat;
    }

    public ItemStack create(){
        ItemStack stack = new ItemStack(mat);
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        return stack;
    }
}
