package me.camm.productions.bedwars.Items.SectionInventories.InventoryConfigurations;

import org.bukkit.Material;

public enum QuickChatConfig {


    HELLO(1,"Hello","Hello",Material.BOOK,null),
    COMING_TO_BASE(2,"I'm coming back to base!","abc",Material.BOOK, null),
    DEFENDING(3,"","",Material.BOOK, null),
    CURRENTLY_ATTACKING(4,"","",Material.BOOK, null),
    RESOURCE_COLLECTING(5,"","",Material.DIAMOND,null),
    NEED_RESOURCES(6,"","",Material.DIAMOND,null),
    START_ATTACKING(7,"","",Material.IRON_SWORD,null),
    ALL_RETURN_TO_BASE(8,"","",Material.BOOK,null),
    PLEASE_DEFEND(9,"","",Material.BOOK,null),
    THANK_YOU(10,"","",Material.BOOK,null),
    HAVE_RESOURCES(11,"","",Material.BOOK,null),
    INCOMING_PLAYER(12,"","",Material.FEATHER,null);


   QuickChatConfig(int slot, String name, String message, Material mat, String lore) {
        this.itemName = name;
        this.slot = slot;
        this.message = message;
        this.mat = mat;
         this.lore = lore;
    }



    private final String lore;
    private final Material mat;
    private final String itemName;
    private final String message;
    private final int slot;


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
}
