package me.camm.productions.bedwars.Items.SectionInventories.Inventories;

import com.google.common.collect.HashBasedTable;
import me.camm.productions.bedwars.Arena.GameRunning.Arena;
import me.camm.productions.bedwars.Arena.Players.BattlePlayer;
import me.camm.productions.bedwars.Items.SectionInventories.InventoryConfigurations.QuickChatConfig;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.IGameInventory;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.InventoryName;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.InventoryProperty;
import me.camm.productions.bedwars.Util.Helpers.InventoryOperationHelper;
import me.camm.productions.bedwars.Util.Helpers.ItemHelper;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCustom;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QuickChatInventory extends CraftInventoryCustom implements IGameInventory {

    private final Arena arena;
    private static final Map<UUID, Tuple<String, BattlePlayer>> playerOptions = new HashMap<>();
    private HashBasedTable<String, Material, QuickChatConfig> configItems;
    public QuickChatInventory(Arena arena) {
        super(null, InventoryProperty.LARGE_SHOP_SIZE.getValue(), InventoryName.QUICK_CHAT.getTitle());
        this.arena = arena;
        init();
    }


    private void init(){

        configItems = HashBasedTable.create();

        for (QuickChatConfig config: QuickChatConfig.values()) {

            Material mat = config.getMat();
            String name = config.getItemName();

            configItems.put(name, mat, config);

            ItemStack stack = ItemHelper.toSimpleItem(mat, name);
            if (config.getLore()!= null) {
                ItemHelper.addLore(stack, config.getLore());
            }
            setItem(config.getSlot(), stack);
        }

    }

    public static void complete(UUID id, String completion) {

        Tuple<String, BattlePlayer> set = playerOptions.getOrDefault(id, null);
        if (set == null)
            return;

        BattlePlayer player = set.b();
        String finish = set.a();

        player.getTeam().sendTeamMessage("complete test  - "+finish + completion);

    }


    private void send(BattlePlayer player, String message){
        player.getTeam().sendTeamMessage("test - "+message);
    }


    public static void add(BattlePlayer player, String clicked){
        playerOptions.put(player.getUUID(), new Tuple<>(clicked, player));
    }





    @Override
    public boolean equals(Inventory other) {
        return super.equals(other);
    }

    @Override
    public void operate(InventoryClickEvent event) {

        if (InventoryOperationHelper.handleClickAttempt(event, this)) {
            event.setCancelled(true);
            return;
        }

        BattlePlayer battlePlayer = arena.getPlayers().getOrDefault(event.getWhoClicked().getUniqueId(), null);

        if (battlePlayer == null)
            return;


       ItemStack clicked = event.getCurrentItem();
        if (ItemHelper.isItemInvalid(clicked)) {
            return;
        }

        ItemMeta meta = clicked.getItemMeta();
        String name = meta.getDisplayName();
        Material type = clicked.getType();

        if (type == null)
            return;

        if (name == null)
            return;

        if (!configItems.contains(name, type))
            return;
        ///check if it is a return to selection inv


        QuickChatConfig option = configItems.get(name, type);

        if (option.allowsOptions()) {

            add(battlePlayer,option.getMessage());
            event.setCancelled(true);
            //open an inventory here

            if (option.isTeamRelated()) {
                ///populate with team stuff
                //bp.getOption().populateTeams()
            }
            else  {
                //populate with resource stuff
                //bp.getOption().populateResources()
            }

            //bp.openInv()...

            return;
        }

        send(battlePlayer, option.getMessage());

        ////
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
