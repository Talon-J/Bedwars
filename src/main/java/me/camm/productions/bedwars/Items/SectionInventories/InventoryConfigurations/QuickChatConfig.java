package me.camm.productions.bedwars.Items.SectionInventories.InventoryConfigurations;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum QuickChatConfig {


    HELLO(10,ChatColor.WHITE+"Hello!",ChatColor.GOLD +"Hello!",Material.BOOK,null,false, false),
    COMING_TO_BASE(11,ChatColor.WHITE+"I'm coming back to base!",ChatColor.GOLD +"I'm coming to base!",Material.BOOK, null,false,false),
    DEFENDING(12,ChatColor.WHITE+"I'm defending!",ChatColor.GOLD +"I'm defending!",Material.IRON_FENCE, null,false,false),
    CURRENTLY_ATTACKING(13,ChatColor.WHITE+"I'm attacking",ChatColor.GOLD +"I'm attacking ",Material.IRON_SWORD, ChatColor.AQUA+"You can choose which team",true,true),
    RESOURCE_COLLECTING(14,ChatColor.WHITE+"I'm collecting resources",ChatColor.GOLD +"I'm getting ",Material.DIAMOND,ChatColor.AQUA+"You can choose which resource",true,false),
    HAVE_RESOURCES(15,ChatColor.WHITE+"I have resources!",ChatColor.GOLD +"I have resources!",Material.CHEST,null,false,false),

    NEED_RESOURCES(24,ChatColor.WHITE+"We need resources!",ChatColor.GOLD +"We need ",Material.DIAMOND,ChatColor.AQUA+"You can choose which resource", true,false),
    START_ATTACKING(23,ChatColor.WHITE+"Let's Attack!",ChatColor.GOLD +"Attack ",Material.IRON_SWORD,ChatColor.AQUA +"You can choose which team", true,true),
    ALL_RETURN_TO_BASE(21,ChatColor.WHITE+"Return to base!",ChatColor.GOLD +"Get back to base!",Material.BOOK,null,false,false),
    PLEASE_DEFEND(22,ChatColor.WHITE+"Please defend!",ChatColor.GOLD +"Please defend!",Material.IRON_FENCE,null,false,false),
    THANK_YOU(20,ChatColor.WHITE+"Thank you!",ChatColor.GOLD +"Thank you!",Material.BOOK,null,false,false),
    INCOMING_PLAYER(25,ChatColor.WHITE+"Incoming player!",ChatColor.GOLD +"Incoming player!",Material.FEATHER,null,false,false);


   QuickChatConfig(int slot, String name, String message, Material mat, String lore, boolean option, boolean team) {
        this.itemName = name;
        this.slot = slot;
        this.message = message;
        this.mat = mat;
         this.lore = lore;
         this.option = option;
         this.team = team;
    }



    private final String lore;
    private final Material mat;
    private final String itemName;
    private final String message;
    private final int slot;
    private final boolean option;
    private final boolean team;


    public String getLore() {
        return lore;
    }

    public Material getMat() {
        return mat;
    }

    public String getItemName() {
        return itemName;
    }

    public String getMessage() {
        return message;
    }

    public int getSlot() {
        return slot;
    }

    public boolean allowsOptions() {
        return option;
    }

    public boolean isTeamRelated() {
        return team;
    }
}
