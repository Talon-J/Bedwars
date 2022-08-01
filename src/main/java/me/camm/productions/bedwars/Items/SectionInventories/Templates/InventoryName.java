package me.camm.productions.bedwars.Items.SectionInventories.Templates;

import org.bukkit.ChatColor;

/**
 * @author CAMM
 * Possible names for the inventories
 */
public enum InventoryName
{
    QUICK_BUY("Quick Buy"),
    TEAM_JOIN(ChatColor.DARK_AQUA+""+ChatColor.BOLD+"Join a Team"),
    TEAM_BUY("Team Upgrades"),
    BLOCKS("Blocks"),
    MELEE("Melee"),
    ARMOR("Armor"),
    TOOLS("Tools"),
    RANGED("Ranged"),
    POTION("Potions"),
    TRACKER("Tracker"),
    SELECT_OPTION("Select an Option"),
    EDIT_QUICKBUY("Adding item to Quick Buy..."),
    HOTBAR_MANAGER("Hotbar Manager"),
    TRACKER_COMMS("Tracker and Communications"),
    QUICK_CHAT("Quick Chat"),
    UTILITY("Utility");

    private final String title;

    InventoryName(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
}
