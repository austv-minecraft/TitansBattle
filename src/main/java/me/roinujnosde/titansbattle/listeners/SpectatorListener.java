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

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        
        if (!plugin.getSpectatorManager().isSpectator(player)) {
            return;
        }
        
        Location to = event.getTo();
        if (to == null) {
            return;
        }
        
        // Allow teleports within the spectator range
        if (plugin.getSpectatorManager().isWithinRange(player, to)) {
            return;
        }
        
        // Allow teleports initiated by the plugin itself (e.g., unwatch command, game ending)
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN) {
            return;
        }
        
        // Block all other out-of-range teleports (commands like /home, /spawn, spectator clicking, etc.)
        event.setCancelled(true);
        player.sendMessage(plugin.getLang("spectator-use-unwatch"));
        plugin.debug("Blocked spectator teleport for " + player.getName() + " - use /tb unwatch to leave");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        
        // Remove player from spectator mode and schedule teleport to exit on rejoin
        if (plugin.getSpectatorManager().isSpectator(player)) {
            plugin.getSpectatorManager().handleDisconnect(player);
        }
    }
}
