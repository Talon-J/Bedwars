package me.camm.productions.bedwars.Items.SectionInventories.Inventories;

import me.camm.productions.bedwars.Arena.GameRunning.Arena;
import me.camm.productions.bedwars.Arena.Players.BattlePlayer;
import me.camm.productions.bedwars.Arena.Teams.BattleTeam;

public class SelectionInventory extends TeamOptionInventory{
    private final String message;
    public SelectionInventory(Arena arena, String message) throws IllegalStateException {
        super(arena);
        this.message = message;
    }

    @Override
    protected void handleResult(BattlePlayer initiation, BattleTeam picked) {
        BattleTeam friendly = initiation.getTeam();

        String result = initiation.getRawPlayer().getName() + " Says " + message + " "+picked.getTeamColor().getName();
        friendly.sendTeamMessage(result);
    }
}
