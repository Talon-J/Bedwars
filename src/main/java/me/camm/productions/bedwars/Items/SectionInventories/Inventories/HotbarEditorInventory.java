package me.camm.productions.bedwars.Items.SectionInventories.Inventories;

import me.camm.productions.bedwars.Arena.GameRunning.Arena;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.IGameInventory;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.InventoryName;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.InventoryProperty;
import me.camm.productions.bedwars.Util.Helpers.InventoryOperationHelper;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCustom;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

public class HotbarEditorInventory extends CraftInventoryCustom implements IGameInventory {

    private final Arena arena;
    public HotbarEditorInventory(Arena arena) {
        super(null, InventoryProperty.SHOP_SIZE.getValue(), InventoryName.HOTBAR_MANAGER.getTitle());
        this.arena = arena;
    }

    @Override
    public boolean equals(Inventory other) {
        return super.equals(other);
    }

    @Override
    public void operate(InventoryClickEvent event) {
        InventoryOperationHelper.operateHotBarClick(event, arena);
    }

    @Override
    public void operate(InventoryDragEvent event) {


        if (InventoryOperationHelper.didTryToDragIn(event, this)) {
            event.setCancelled(true);

            return;
        }
        InventoryOperationHelper.handleDefaultRestrictions(event, arena);

    }
}
