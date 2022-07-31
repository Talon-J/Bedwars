package me.camm.productions.bedwars.Items.SectionInventories.Inventories;

import me.camm.productions.bedwars.Arena.GameRunning.Arena;
import me.camm.productions.bedwars.Arena.Players.BattlePlayer;
import me.camm.productions.bedwars.Arena.Players.Managers.PlayerTrackerManager;
import me.camm.productions.bedwars.Arena.Teams.BattleTeam;
import me.camm.productions.bedwars.Items.ItemDatabases.ItemCategory;
import me.camm.productions.bedwars.Items.ItemDatabases.ShopItem;
import me.camm.productions.bedwars.Util.Helpers.ItemHelper;
import org.bukkit.inventory.ItemStack;

public class TrackerInventory extends InventoryOptionable {

    private static final ItemStack ITEM = ItemHelper.toBarItem(ItemCategory.TRACKER);
    private PlayerTrackerManager manager;
    private static final ShopItem TRACKER = ShopItem.TRACKER_ITEM;

    public TrackerInventory(Arena arena) {
        super(arena);
    }


    public void setManager(PlayerTrackerManager manager) {
        this.manager = manager;
    }


    @Override
    protected void handleResult(BattlePlayer battlePlayer, BattleTeam preference) {


        if (manager == null)
            return;

        //the tracker item
        if (!battlePlayer.getRawPlayer().getInventory().contains(ITEM)) {
            return;
        }

        int price = inflated ? TRACKER.inflatedPrice : TRACKER.cost;
        boolean paid = ItemHelper.didPay(battlePlayer, ShopItem.TRACKER_ITEM.costMaterial,price);

        if (paid) {
            battlePlayer.setTracking(preference);
            manager.addEntry(battlePlayer);
            //then we start tracking the team here.
        }
    }
}
