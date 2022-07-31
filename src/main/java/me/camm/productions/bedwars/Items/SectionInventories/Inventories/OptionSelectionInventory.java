package me.camm.productions.bedwars.Items.SectionInventories.Inventories;

import me.camm.productions.bedwars.Arena.GameRunning.Arena;
import me.camm.productions.bedwars.Arena.Players.BattlePlayer;
import me.camm.productions.bedwars.Arena.Teams.BattleTeam;
import me.camm.productions.bedwars.Items.SectionInventories.Inventories.InventoryOptionable;
import me.camm.productions.bedwars.Items.SectionInventories.Templates.InventoryProperty;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Material.DIAMOND;
import static org.bukkit.Material.EMERALD;

public class OptionSelectionInventory extends InventoryOptionable {


    private final BattlePlayer owner;
    private final static Material[] resources = new Material[]{DIAMOND, EMERALD};
    private static int END = InventoryProperty.LARGE_ROW_TWO_END.getValue();


    public OptionSelectionInventory(Arena arena, BattlePlayer owner) {
        super(arena);
        this.owner = owner;

    }


    public void loadTeams(){


        BattleTeam friendly = owner.getTeam();
        for (BattleTeam team: arena.getTeams().values()) {
            if (team.isEliminated())
                super.removeEntry(team);
            else if (!friendly.equals(team))
                super.addEntry(team);
        }

        super.updateInventory();
    }

    public void loadResources(){

        int resSlot = 0;
        for (int slot = ROW; slot <= END;slot++)
        {
            setItem(slot,null);

            if (resSlot < resources.length)
                setItem(slot, new ItemStack(resources[resSlot]));
        }
    }


    @Override
    public void operate(InventoryClickEvent event) {




    }

    @Override
    protected void handleResult(BattlePlayer initiation, BattleTeam picked) {

    }

    private void handleResult(BattlePlayer initiation, Material mat) {

    }
}
