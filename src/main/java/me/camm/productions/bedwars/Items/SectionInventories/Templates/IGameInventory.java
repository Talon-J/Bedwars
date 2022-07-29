package me.camm.productions.bedwars.Items.SectionInventories.Templates;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface IGameInventory {

    public boolean equals(Inventory other);

    public void operate(InventoryClickEvent event);
    public void operate(InventoryDragEvent event);


    public void setItem(int slot, ItemStack stack);

    public ItemStack getItem(int slot);
}
