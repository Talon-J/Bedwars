package me.camm.productions.bedwars.Items.SectionInventories.Inventories;


import me.camm.productions.bedwars.Arena.GameRunning.Arena;
import me.camm.productions.bedwars.Arena.Players.BattlePlayer;

import me.camm.productions.bedwars.Arena.Teams.BattleTeam;
import me.camm.productions.bedwars.Arena.Teams.TeamColor;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.InventoryName;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.InventoryProperty;
import me.camm.productions.bedwars.Items.ItemDatabases.ItemCategory;

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
public abstract class InventoryOptionable extends CraftInventoryCustom implements IGameInventory
{

    protected final Map<String, BattleTeam> dictionary;
    protected static final int LENGTH = TeamColor.values().length;
    protected static final ItemStack AIR = new ItemStack(Material.AIR);
    protected static final int ROW = InventoryProperty.LARGE_ROW_TWO_START.getValue();

   protected static final ItemStack SEPARATOR = ItemHelper.createGlassPane(ItemCategory.SEPARATOR);



    protected static boolean inflated;
    protected final Arena arena;




    static {
        inflated = false;
    }

    public InventoryOptionable(Arena arena) {
        super(null, InventoryProperty.LARGE_SHOP_SIZE.getValue(), InventoryName.TRACKER.getTitle());
      dictionary = new HashMap<>();
      this.arena = arena;


        for (int slot = InventoryProperty.LARGE_ROW_THREE_START.getValue(); slot <= InventoryProperty.LARGE_ROW_THREE_END.getValue();slot++)
            setItem(slot, SEPARATOR);
    }


    public static void setInflated(boolean inf){
        inflated = inf;
    }

    public void addEntry(@NotNull BattleTeam team){
        TeamColor color = team.getTeamColor();
        dictionary.put(color.format(), team);

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
        updateInventory();
    }

    public void updateInventory(){
        Iterator<BattleTeam> iter = dictionary.values().iterator();

        int slot = 1;
        while (slot < LENGTH && iter.hasNext()) {

            setItem(slot+ROW,AIR);

                BattleTeam team = iter.next();

                /*
                if the team is elim, then return here
                 */


                TeamColor color = team.getTeamColor();

                ItemStack trackStack = ItemHelper.createWool((byte)color.getValue());
                  ItemMeta meta = trackStack.getItemMeta();
                  meta.setDisplayName(color.format());
                  trackStack.setItemMeta(meta);
                  setItem(slot+ROW, trackStack);

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

        Map<UUID, BattlePlayer> players = arena.getPlayers();
       BattlePlayer battlePlayer = players.getOrDefault(event.getWhoClicked().getUniqueId(), null);

       InventoryOperationHelper.handleDefaultRestrictions(event,arena);
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
            event.setCancelled(true);
            removeEntry(preference);
            return;
        }

        handleResult(battlePlayer, preference);


    }

    @Override
    public void operate(InventoryDragEvent event) {

        if (InventoryOperationHelper.didTryToDragIn(event, this)) {
            event.setCancelled(true);
            return;
        }
        InventoryOperationHelper.handleDefaultRestrictions(event, arena);
    }


    protected abstract void handleResult(BattlePlayer initiation, BattleTeam picked);









}
