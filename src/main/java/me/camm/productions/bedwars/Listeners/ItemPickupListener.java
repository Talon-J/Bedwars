package me.camm.productions.bedwars.Listeners;

import me.camm.productions.bedwars.Arena.GameRunning.Arena;
import me.camm.productions.bedwars.Arena.Players.BattlePlayer;
import me.camm.productions.bedwars.Files.FileKeywords.TeamFileKeywords;
import me.camm.productions.bedwars.Items.ItemDatabases.ShopItem;
import me.camm.productions.bedwars.Items.ItemDatabases.TieredItem;

import me.camm.productions.bedwars.Util.Helpers.ItemHelper;
import org.bukkit.Location;

import org.bukkit.entity.Entity;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import org.bukkit.inventory.ItemStack;

import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static me.camm.productions.bedwars.Files.FileKeywords.TeamFileKeywords.FORGE_SPAWN;
import static me.camm.productions.bedwars.Util.Locations.BlockRegisterType.ARENA;

public class ItemPickupListener implements Listener
{
    private final Plugin plugin;
    private final Arena arena;

    public ItemPickupListener(Plugin plugin, Arena arena)
    {
        this.plugin = plugin;
        this.arena = arena;
    }

    @EventHandler
    public void onItemMerge(ItemMergeEvent event)
    {
        if (event.getEntity().hasMetadata(FORGE_SPAWN.getKey())||event.getTarget().hasMetadata(FORGE_SPAWN.getKey()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onItemDespawn(ItemDespawnEvent event)
    {
        if (event.getLocation().getBlock().hasMetadata(ARENA.getData())) {
            event.setCancelled(true);
            Item item = event.getEntity();
            item.setTicksLived(1);
        }
    }

    @EventHandler
    public void onItemPickUp(PlayerPickupItemEvent event)
    {
        ConcurrentHashMap<UUID, BattlePlayer> players = arena.getPlayers();
        UUID id = event.getPlayer().getUniqueId();


        if (!players.containsKey(id))
            return;

            BattlePlayer player = players.get(id);

            if (!player.getIsAlive() || player.getIsEliminated())
            {
                event.setCancelled(true);
                return;
            }

            ItemStack picked = event.getItem().getItemStack().clone();


            if (ItemHelper.isItemInvalid(picked))
                return;

            TieredItem tiered = ItemHelper.isTieredItem(ItemHelper.getAssociate(picked));


            if (tiered != null)
            {
                if (ItemHelper.isSword(tiered.getItem())) {
                    ItemHelper.clearAll(ShopItem.WOODEN_SWORD.sellMaterial,player.getRawPlayer().getInventory());
                }

            }
            else if (ItemHelper.isCurrencyItem(picked)) {

                Location loc = player.getTeam().getForge().getForgeLocation();
                double distance = player.getTeam().getForge().getDistance();

                Item pickup = event.getItem();
                if (!pickup.hasMetadata(FORGE_SPAWN.getKey()))
                    return;

                Collection<Entity> nearby = loc.getWorld().getNearbyEntities(loc, distance, distance, distance);
                for (Entity entity : nearby) {
                    if (!(entity instanceof Player))
                        continue;

                    Player current = (Player) entity;
                    if (!players.containsKey(current.getUniqueId()))
                        continue;

                    BattlePlayer currentReceiver = players.get(current.getUniqueId());
                    if (!player.getTeam().equals(currentReceiver.getTeam()))  //if on the same team, then share
                        continue;

                    if (player.getUUID().equals(currentReceiver.getUUID()))  //if they are not the same player
                        continue;

                    if (currentReceiver.getRawPlayer().getInventory().firstEmpty() != -1 &&
                            (currentReceiver.getIsAlive() && !currentReceiver.getIsEliminated()))
                        currentReceiver.getRawPlayer().getInventory().addItem(picked);
                }
            }

    }
}
