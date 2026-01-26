package me.roinujnosde.titansbattle.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

import me.roinujnosde.titansbattle.TitansBattle;

/**
 * Listener to manage spectator range restrictions
 */
public class SpectatorListener extends TBListener {

    public SpectatorListener(@NotNull TitansBattle plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        
        if (!plugin.getSpectatorManager().isSpectator(player)) {
            return;
        }
        
        Location to = event.getTo();
        if (to == null) {
            return;
        }
        
        // Check if the player is trying to move outside the allowed range
        if (!plugin.getSpectatorManager().isWithinRange(player, to)) {
            // Cancel the movement and keep player at current location
            event.setCancelled(true);
            
            // Send a message to the player
            player.sendMessage(plugin.getLang("spectator-range-limit"));
            
            plugin.debug("Spectator " + player.getName() + " tried to move outside allowed range");
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        
        if (!plugin.getSpectatorManager().isSpectator(player)) {
            return;
        }
        
        Location to = event.getTo();
        if (to == null) {
            return;
        }
        
        // Check if teleport is within range for spectators
        // Allow spectator teleport if it's within the allowed range
        if (!plugin.getSpectatorManager().isWithinRange(player, to)) {
            event.setCancelled(true);
            player.sendMessage(plugin.getLang("spectator-range-limit"));
            plugin.debug("Cancelled spectator teleport for " + player.getName() + " - outside range");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        
        // Remove player from spectator mode when they quit
        if (plugin.getSpectatorManager().isSpectator(player)) {
            plugin.getSpectatorManager().removeSpectator(player);
        }
    }
}
