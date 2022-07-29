package me.camm.productions.bedwars.Listeners;

import me.camm.productions.bedwars.Arena.GameRunning.Arena;
import me.camm.productions.bedwars.Arena.GameRunning.GameRunner;
import me.camm.productions.bedwars.Arena.Players.BattlePlayer;
import me.camm.productions.bedwars.Arena.Players.Managers.PlayerInventoryManager;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.InventoryName;
import me.camm.productions.bedwars.Items.SectionInventories.Inventories.TeamJoinInventory;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.IGameInventory;
import me.camm.productions.bedwars.Util.Helpers.ChatSender;
import me.camm.productions.bedwars.Util.Helpers.InventoryOperationHelper;
import me.camm.productions.bedwars.Util.Helpers.ItemHelper;
import org.bukkit.GameMode;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryListener implements Listener {

    private final Arena arena;
    private final GameRunner runner;
    private final HashMap<String, InventoryName> titles;
    private final Map<UUID, BattlePlayer> registeredPlayers;
    private final IGameInventory joinInventory;
    private final ChatSender sender;



    public InventoryListener(GameRunner runner){
        sender = ChatSender.getInstance();
        this.arena = runner.getArena();
        this.runner = runner;
        registeredPlayers = arena.getPlayers();
        titles = new HashMap<>();
        InventoryName[] names = InventoryName.values();
        for (InventoryName name: names)
            titles.put(name.getTitle(),name);
        this.joinInventory = new TeamJoinInventory(arena, runner);
    }

    public IGameInventory getJoinInventory(){
        return joinInventory;
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)  //for joining teams / other things
    {

        if (event instanceof InventoryCreativeEvent)
            return;

        if (event.getClickedInventory()==null||event.getClickedInventory().getTitle()==null) {
            return;
        }

        HumanEntity player = event.getWhoClicked();
        BattlePlayer battlePlayer = registeredPlayers.getOrDefault(player.getUniqueId(), null);
        Inventory inv = event.getClickedInventory();

        if (battlePlayer == null || inv.equals(joinInventory))
        {
            joinInventory.operate(event);
            return;
        }

        Map<Integer, IGameInventory> monitors = battlePlayer.getAccessibleInventories();

        Inventory clicked = event.getClickedInventory();
        Inventory top = event.getView().getTopInventory();

        if (!clicked.equals(top) && runner.isRunning()) {
            InventoryOperationHelper.handleDefaultRestrictions(event, arena);
            return;
        }

        IGameInventory gameInventory = monitors.getOrDefault(clicked.hashCode(),null);


        if (gameInventory == null)
            return;

        gameInventory.operate(event);



        boolean debug = true;
        if (debug)
            return;

        /////////////////////

        //If the clicked inventory is not registered as a known inventory
        if (!titles.containsKey(""))
        {

            if (!registeredPlayers.containsKey(player.getUniqueId()))
                return;



            //operate on restrictions to ensure that they didn't put a restricted item somewhere
            InventoryOperationHelper.handleDefaultRestrictions(event, arena);

            //if the player's inventory is not the clicked inventory, then return.
            if (!player.getInventory().equals(event.getClickedInventory()))
                return;

            //if the enderchest is the clicked inv, then return.
            if (player.getEnderChest().equals(event.getClickedInventory()))
                return;

            //If the player has clicked their own inv or their enderchest inv.

            ItemStack stack = event.getCurrentItem();
            if (ItemHelper.isItemInvalid(stack))
                return;

            //If the player has attempted to take off their armor, cancel the event.
            //So it seems that there is a glitch with players being
            //able to take it off in creative.
            //Shouldn't be an issue though, since everyone should be
            //in survival.
            if (ItemHelper.isArmor(stack.getType()))
            {
                GameMode mode = player.getGameMode();
                if (mode != GameMode.CREATIVE && mode != GameMode.SPECTATOR) {
                    event.setCancelled(true);
                    return;
                }
            }


            //if it is a top inv
            Inventory topInventory = event.getInventory();

            boolean valid = false;
            if (joinInventory.equals(topInventory)){
                event.setCancelled(true);
                valid = true;
            }


            PlayerInventoryManager sectionManager = battlePlayer.getShopManager();
            if (sectionManager == null)
                return;

            Inventory sectionInv = sectionManager.isSectionInventory(topInventory);

            if (sectionInv == null && !valid)
                return;

            if (InventoryOperationHelper.handleClickAttempt(event,sectionInv)) {
                event.setCancelled(true);
                return;
            }


        }

        InventoryName inventoryName = titles.get("");
        if (inventoryName == null)
            return;

        if (inventoryName == InventoryName.TEAM_JOIN) {
             //   addPlayerToTeam(event);
        }
        else {
            switch (inventoryName) {

                case TEAM_BUY:
                    InventoryOperationHelper.sellTeamBuy(event, arena);
                    break;

                case EDIT_QUICKBUY:
                    InventoryOperationHelper.operateInventoryEdit(event, arena);
                    break;

                case HOTBAR_MANAGER:
                    InventoryOperationHelper.operateHotBarClick(event, arena);
                    break;

                case TRACKER:
                    break;

                default:
                    InventoryOperationHelper.sellQuickbuy(event, arena, runner.isInflated());
                    //do the rest of the invs here.
            }
        }

        //maybe use a switch statement here for the titles

    }//method




        /*
    @Author CAMM
    This method handles the case of a player clicking on one of the inventories present in the game.
     */


    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event)
    {
        if (InventoryOperationHelper.didTryToDragIn(event, (Inventory)joinInventory)) {
            event.setCancelled(true);
        }

        HumanEntity entity = event.getWhoClicked();
        if (!registeredPlayers.containsKey(entity.getUniqueId()))
            return;

        BattlePlayer player = registeredPlayers.get(entity.getUniqueId());
        Inventory inv = event.getInventory();

        Inventory section = player.getShopManager().isSectionInventory(inv);

        if (InventoryOperationHelper.didTryToDragIn(event, section)) {
            event.setCancelled(true);
            return;
        }

        if (InventoryOperationHelper.didTryToDragIn(event, (Inventory)player.getTeam().getTeamInventory())) {
            event.setCancelled(true);
            return;
        }

        if (InventoryOperationHelper.didTryToDragIn(event, player.getQuickEditor())) {
            event.setCancelled(true);
            return;
        }

        InventoryOperationHelper.handleDefaultRestrictions(event,arena);

        if (player.getBarManager().invEquals(event.getView().getTopInventory()))
            InventoryOperationHelper.operateHotBarDrag(event, arena);

    }





}
