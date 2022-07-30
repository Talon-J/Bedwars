package me.camm.productions.bedwars.Items.SectionInventories.Inventories;

import me.camm.productions.bedwars.Arena.GameRunning.Arena;
import me.camm.productions.bedwars.Items.SectionInventories.InventoryConfigurations.QuickChatConfig;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.IGameInventory;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.InventoryName;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.InventoryProperty;
import me.camm.productions.bedwars.Util.Helpers.InventoryOperationHelper;
import me.camm.productions.bedwars.Util.Helpers.ItemHelper;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCustom;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class QuickChatInventory extends CraftInventoryCustom implements IGameInventory {

    private final Arena arena;
    public QuickChatInventory(Arena arena) {
        super(null, InventoryProperty.LARGE_SHOP_SIZE.getValue(), InventoryName.QUICK_CHAT.getTitle());
        this.arena = arena;
        init();
    }


    private void init(){

        for (QuickChatConfig config: QuickChatConfig.values()) {

            ItemStack stack = ItemHelper.toSimpleItem(config.getMat(), config.getItemName());
            if (config.getLore()!= null) {
                ItemHelper.addLore(stack, config.getLore());
            }
            setItem(config.getSlot(), stack);
        }

    }


    @Override
    public boolean equals(Inventory other) {
        return super.equals(other);
    }

    @Override
    public void operate(InventoryClickEvent event) {
        event.setCancelled(true);
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
