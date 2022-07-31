package me.camm.productions.bedwars.Items.SectionInventories.InventoryConfigurations;

import org.bukkit.Material;

public enum QuickChatConfig {


    HELLO(1,"Hello","Hello",Material.BOOK,null,false, false),
    COMING_TO_BASE(2,"I'm coming back to base!","I'm coming to base!",Material.BOOK, null,false,false),
    DEFENDING(3,"I'm defending!","I'm defending!",Material.BOOK, null,false,false),
    CURRENTLY_ATTACKING(4,"I'm attacking","I'm attacking ",Material.BOOK, "You can choose which team",true,true),
    RESOURCE_COLLECTING(5,"I'm getting resources","I'm getting ",Material.DIAMOND,"You can choose which resource",true,false),
    NEED_RESOURCES(6,"We need resources!","We need ",Material.DIAMOND,"You can choose which resource", true,false),
    START_ATTACKING(7,"Attack!","Attack ",Material.IRON_SWORD,"You can choose which team", true,true),
    ALL_RETURN_TO_BASE(8,"Return to base!","Get back to base!",Material.BOOK,null,false,false),
    PLEASE_DEFEND(9,"Please defend!","Please defend!",Material.BOOK,null,false,false),
    THANK_YOU(10,"Thank you!","Thank you!",Material.BOOK,null,false,false),
    HAVE_RESOURCES(11,"I have resources!","I have resources!",Material.BOOK,null,false,false),
    INCOMING_PLAYER(12,"Incoming player!","Incoming player!",Material.FEATHER,null,false,false);


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
