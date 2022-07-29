package me.camm.productions.bedwars.Items.SectionInventories.Inventories;


import me.camm.productions.bedwars.Arena.GameRunning.Arena;
import me.camm.productions.bedwars.Arena.Players.BattlePlayer;
import me.camm.productions.bedwars.Items.ItemDatabases.ItemCategory;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.InventoryName;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.InventoryProperty;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.IGameInventory;
import me.camm.productions.bedwars.Util.Helpers.InventoryOperationHelper;
import me.camm.productions.bedwars.Util.Helpers.ItemHelper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCustom;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

/*
 * Unfinished.
 */
public class ActionSelectionInventory extends CraftInventoryCustom implements IGameInventory {

    private final static ItemStack TRACKER_OPTION = ItemHelper.toBarItem(ItemCategory.TRACKER);
    private final static ItemStack CHAT_OPTION = ItemHelper.toSimpleItem(Material.FEATHER, ChatColor.AQUA+"Quick Chat");

    private static enum Slot{
        TRACKER(11, TRACKER_OPTION),
        CHAT(15,CHAT_OPTION);

        public final int slot;
        public final ItemStack stack;

        Slot(int slot, ItemStack stack){
            this.slot = slot;
            this.stack = stack;
        }
    }


    private final Arena arena;

    public ActionSelectionInventory(Arena arena) {
        super(null, InventoryProperty.SMALL_SHOP_SIZE.getValue(), InventoryName.TRACKER.getTitle());
        this.arena = arena;
        init();
    }

    private void init(){
        for (Slot slot: Slot.values())
            setItem(slot.slot, slot.stack);
    }

    @Override
    public boolean equals(Inventory other) {
        return super.equals(other);
    }

    @Override
    public void operate(InventoryClickEvent event) {

        event.setCancelled(InventoryOperationHelper.handleClickAttempt(event, this));
        Map<UUID, BattlePlayer> players = arena.getPlayers();

        UUID id = event.getWhoClicked().getUniqueId();
        BattlePlayer clicked = players.getOrDefault(id, null);
        if (clicked == null)
            return;

       Inventory tracker = clicked.getTeam().getTrackerInventory();
        Inventory chat = (Inventory)arena.getChatInv();

        ItemStack clickedItem = event.getCurrentItem();
        if (ItemHelper.isItemInvalid(clickedItem))
            return;

        Player player = clicked.getRawPlayer();

        if (clickedItem.isSimilar(TRACKER_OPTION))
            player.openInventory(tracker);
            else if (clickedItem.isSimilar(CHAT_OPTION))
                player.openInventory(chat);
    }

    @Override
    public void operate(InventoryDragEvent event) {

        InventoryOperationHelper.didTryToDragIn(event,this);
    }
}
