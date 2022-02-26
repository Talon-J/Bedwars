package me.camm.productions.bedwars.Arena.Players;

import me.camm.productions.bedwars.Arena.Teams.BattleTeam;
import me.camm.productions.bedwars.Arena.Teams.TeamTitle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static me.camm.productions.bedwars.Arena.Players.Scoreboards.ScoreBoardHeader.TEAM_ALIVE;
import static me.camm.productions.bedwars.Arena.Players.Scoreboards.ScoreBoardHeader.TEAM_DEAD;

public interface IPlayerUtil
{
    default String getTeamStatus(BattleTeam team)
    {
        try {
            String previousName;
            if (team.isEliminated())
                previousName = team.getDisplayScoreboardEntry() + " " + TEAM_DEAD.getPhrase();
            else if (!team.doesBedExist())
                previousName = team.getDisplayScoreboardEntry() + " " + team.getRemainingPlayers();
            else
                previousName = team.getDisplayScoreboardEntry() + " " + TEAM_ALIVE.getPhrase();

            return previousName;
        }
        catch (NullPointerException e)
        {
            return "Team DNE";
        }
    }

    default String addRespawnNumber(TeamTitle title, int seconds)
    {
        if (title == TeamTitle.RESPAWN_AFTER)
        {
            return title.getMessage() + seconds +"!\"}";
        }
        return title.getMessage();
    }

    default void clearInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[]{null,null,null,null});
    }



}
