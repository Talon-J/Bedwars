package me.camm.productions.bedwars.Items.SectionInventories.Inventories;


import me.camm.productions.bedwars.Arena.GameRunning.Arena;
import me.camm.productions.bedwars.Arena.Players.BattlePlayer;
import me.camm.productions.bedwars.Arena.Players.Managers.PlayerTrackerManager;
import me.camm.productions.bedwars.Arena.Teams.BattleTeam;
import me.camm.productions.bedwars.Arena.Teams.TeamColor;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.InventoryName;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.InventoryProperty;
import me.camm.productions.bedwars.Items.ItemDatabases.ItemCategory;

import me.camm.productions.bedwars.Items.ItemDatabases.ShopItem;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.IGameInventory;
import me.camm.productions.bedwars.Util.Helpers.InventoryOperationHelper;
import me.camm.productions.bedwars.Util.Helpers.ItemHelper;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCustom;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;


/*
 * @author CAMM
 * This inventory models a tracker inventory for the player (where the player determines who to track)
 */
public class TrackerSectionInventory extends CraftInventoryCustom implements IGameInventory
{

    private final Map<String, BattleTeam> dictionary;
    private static final int LENGTH = TeamColor.values().length;
    private static final ItemStack AIR = new ItemStack(Material.AIR);
    private static final int ROW = InventoryProperty.LARGE_ROW_TWO_START.getValue();
    private static final ItemStack SEPARATOR = ItemHelper.createGlassPane(ItemCategory.SEPARATOR);
    private static boolean inflated;

    private static final ShopItem TRACKER = ShopItem.TRACKER_NAV;
    private final Arena arena;
    private PlayerTrackerManager manager;

    private static final ItemStack ITEM = ItemHelper.toBarItem(ItemCategory.TRACKER);

    static {
        inflated = false;
    }

    public TrackerSectionInventory(Arena arena) throws IllegalStateException {
        super(null, InventoryProperty.MEDIUM_SHOP_SIZE.getValue(), InventoryName.TRACKER.getTitle());
      dictionary = new HashMap<>();
      this.arena = arena;


      if (SEPARATOR==null)
          throw new IllegalStateException("Separators should not be null!");
    }


    public void setManager(PlayerTrackerManager manager) {
        this.manager = manager;
    }


    public static void setInflated(boolean inf){
        inflated = inf;
    }

    public void addEntry(@NotNull BattleTeam team){
        TeamColor color = team.getTeamColor();
        dictionary.put(color.getChatColor()+color.getName(), team);
    }

    public void addEntries(Collection<BattleTeam> teams, BattleTeam exclusion){
        for (BattleTeam next : teams) {
            if (next.equals(exclusion))
                continue;

            addEntry(next);
        }

    }


    public void removeEntry(@NotNull BattleTeam team){
        dictionary.remove(team.getTeamColor().getName());
    }

    public void updateInventory(){
        Iterator<BattleTeam> iter = dictionary.values().iterator();

        int slot = 1;
        while (slot < LENGTH && iter.hasNext()) {

            setItem(slot+ROW,AIR);

                BattleTeam team = iter.next();
                TeamColor color = team.getTeamColor();

                ItemStack trackStack = ItemHelper.createColoredGlass(Material.STAINED_GLASS_PANE,color.getDye());
              if (trackStack!=null) {
                  ItemMeta meta = trackStack.getItemMeta();
                  meta.setDisplayName(color.getChatColor()+color.getName());
                  trackStack.setItemMeta(meta);
                  setItem(slot+ROW, trackStack);
              }
              else throw new IllegalStateException("A stack for a registered team is null!");

            slot++;
        }
    }


    @Override
    public boolean equals(Inventory other)
    {
        return super.equals(other);
    }

    @Override
    public void operate(InventoryClickEvent event) {


        if (manager == null)
            return;

        Map<UUID, BattlePlayer> players = arena.getPlayers();
       BattlePlayer battlePlayer = players.getOrDefault(event.getWhoClicked().getUniqueId(), null);

       event.setCancelled(InventoryOperationHelper.handleClickAttempt(event, this));

       ItemStack clicked = event.getCurrentItem();
       if (ItemHelper.isItemInvalid(clicked))
           return;


       String name = clicked.getItemMeta().getDisplayName();
       if (name == null)
           return;


       BattleTeam preference = dictionary.getOrDefault(name, null);

        if (preference == null)
            return;

        if (preference.isEliminated()) {
            removeEntry(preference);
            return;
        }

        //the tracker item
        if (!battlePlayer.getRawPlayer().getInventory().contains(ITEM)) {
            return;
        }

        int price = inflated ? TRACKER.inflatedPrice : TRACKER.cost;
       boolean paid = ItemHelper.didPay(battlePlayer, ShopItem.TRACKER_NAV.costMaterial,price);

       if (paid) {
           battlePlayer.setTracking(preference);
           manager.addEntry(battlePlayer);
           //then we start tracking the team here.
       }


    }

    @Override
    public void operate(InventoryDragEvent event) {
        InventoryOperationHelper.handleDefaultRestrictions(event, arena);
    }



}
