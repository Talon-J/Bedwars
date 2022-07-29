package me.camm.productions.bedwars.Items.SectionInventories.Inventories;

import me.camm.productions.bedwars.Arena.GameRunning.Arena;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.IGameInventory;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.InventoryName;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.InventoryProperty;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCustom;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class QuickChatInventory extends CraftInventoryCustom implements IGameInventory {

    public QuickChatInventory(Arena arena) {
        super(null, InventoryProperty.MEDIUM_SHOP_SIZE.getValue(), InventoryName.QUICK_CHAT.getTitle());
    }


    @Override
    public boolean equals(Inventory other) {
        return super.equals(other);
    }

    @Override
    public void operate(InventoryClickEvent event) {

        event.setCancelled(true);

        event.getWhoClicked().closeInventory();

        event.getWhoClicked().sendMessage("Not implemented yet");

    }

    @Override
    public void operate(InventoryDragEvent event) {
        event.setCancelled(true);
    }
}
