package me.camm.productions.bedwars.Items.SectionInventories.Inventories;

import me.camm.productions.bedwars.Items.SectionInventories.Templates.IGameInventory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCustom;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class HotbarEditorInventory extends CraftInventoryCustom implements IGameInventory {

    public HotbarEditorInventory(InventoryHolder owner, int size, String title) {
        super(owner, size, title);
    }

    @Override
    public boolean equals(Inventory other) {
        return super.equals(other);
    }

    @Override
    public void operate(InventoryClickEvent event) {

    }

    @Override
    public void operate(InventoryDragEvent event) {

    }
}
