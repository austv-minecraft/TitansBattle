package me.roinujnosde.titansbattle.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.roinujnosde.titansbattle.BaseGame;
import me.roinujnosde.titansbattle.TitansBattle;

/**
 * Manages spectator mode for players watching games.
 * Controls spectator gamemode and movement range restrictions.
 */
public class SpectatorManager {
    
    private final TitansBattle plugin;
    private final Map<UUID, SpectatorData> spectators = new HashMap<>();
    private static final double DEFAULT_SPECTATOR_RANGE = 50.0;
    
    public SpectatorManager(@NotNull TitansBattle plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Adds a player as spectator and sets them to spectator mode
     * 
     * @param player The player to add as spectator
     * @param game The game the player is spectating
     * @param centerLocation The center location for the spectator range
     * @param range The maximum range in blocks for the spectator
     */
    public void addSpectator(@NotNull Player player, @NotNull BaseGame game, @NotNull Location centerLocation, double range) {
        // Preserve the original gamemode if the player is already a spectator
        // to prevent losing the real previous gamemode on repeated /watch calls
        SpectatorData existing = spectators.get(player.getUniqueId());
        GameMode previousGameMode = (existing != null) ? existing.previousGameMode : player.getGameMode();
        
        SpectatorData data = new SpectatorData(
            previousGameMode, 
            game,
            centerLocation.clone(),
            range
        );
        spectators.put(player.getUniqueId(), data);
        player.setGameMode(GameMode.SPECTATOR);
        plugin.debug("Added spectator: " + player.getName() + " watching " + game.getConfig().getName() + " with range " + range);
    }
    
    /**
     * Removes a player from spectator mode and restores their original gamemode
     * 
     * @param player The player to remove from spectator mode
     */
    public void removeSpectator(@NotNull Player player) {
        SpectatorData data = spectators.remove(player.getUniqueId());
        if (data != null) {
            player.setGameMode(data.previousGameMode);
            plugin.debug("Removed spectator: " + player.getName());
        }
    }
    
    /**
     * Handles a spectator disconnecting. Removes them from the spectator map
     * and schedules them to be teleported to the exit on rejoin via the respawn list.
     * 
     * @param player The player who disconnected
     */
    public void handleDisconnect(@NotNull Player player) {
        SpectatorData data = spectators.remove(player.getUniqueId());
        if (data != null) {
            // Add to respawn list so they'll be teleported to exit on rejoin
            plugin.getConfigManager().getRespawn().add(player.getUniqueId());
            plugin.getConfigManager().save();
            plugin.debug("Spectator " + player.getName() + " disconnected, marked for teleport on rejoin");
        }
    }
    
    /**
     * Checks if a player is currently a spectator
     * 
     * @param player The player to check
     * @return true if the player is a spectator
     */
    public boolean isSpectator(@NotNull Player player) {
        return spectators.containsKey(player.getUniqueId());
    }
    
    /**
     * Checks if a location is within the allowed spectator range
     * 
     * @param player The spectator player
     * @param location The location to check
     * @return true if within range, false otherwise
     */
    public boolean isWithinRange(@NotNull Player player, @NotNull Location location) {
        SpectatorData data = spectators.get(player.getUniqueId());
        if (data == null) {
            return true;
        }
        
        // Only check horizontal distance (X and Z)
        double distance = Math.sqrt(
            Math.pow(location.getX() - data.centerLocation.getX(), 2) +
            Math.pow(location.getZ() - data.centerLocation.getZ(), 2)
        );
        
        return distance <= data.maxRange;
    }
    
    /**
     * Gets the center location for a spectator
     * 
     * @param player The spectator player
     * @return The center location or null if not a spectator
     */
    public Location getCenterLocation(@NotNull Player player) {
        SpectatorData data = spectators.get(player.getUniqueId());
        return data != null ? data.centerLocation.clone() : null;
    }
    
    /**
     * Gets the exit location for a spectator based on the game they are watching.
     * Falls back to the general exit if the game exit is not set.
     * 
     * @param player The spectator player
     * @return The exit location, or null if none is configured
     */
    public @Nullable Location getExitLocation(@NotNull Player player) {
        SpectatorData data = spectators.get(player.getUniqueId());
        if (data == null || data.game == null) {
            return plugin.getConfigManager().getGeneralExit();
        }
        Location exit = data.game.getConfig().getExit();
        if (exit == null) {
            exit = plugin.getConfigManager().getGeneralExit();
        }
        return exit;
    }
    
    /**
     * Clears all spectators watching a specific game and teleports them to the specified location
     * 
     * @param game The game whose spectators should be cleared
     * @param exitLocation The location to teleport spectators to, or null to not teleport
     */
    public void clearAllForGame(@NotNull BaseGame game, @Nullable Location exitLocation) {
        // Create a copy to avoid ConcurrentModificationException
        for (Map.Entry<UUID, SpectatorData> entry : new HashMap<>(spectators).entrySet()) {
            if (entry.getValue().game != game) {
                continue;
            }
            Player player = plugin.getServer().getPlayer(entry.getKey());
            if (player != null && player.isOnline()) {
                removeSpectator(player);
                if (exitLocation != null) {
                    player.teleport(exitLocation);
                    plugin.debug("Teleported spectator " + player.getName() + " to exit location");
                }
            } else {
                spectators.remove(entry.getKey());
            }
        }
    }
    
    /**
     * Clears all spectators (used on plugin disable)
     */
    public void clearAll() {
        clearAll(null);
    }
    
    /**
     * Clears all spectators and teleports them to the specified location
     * 
     * @param exitLocation The location to teleport spectators to, or null to not teleport
     */
    public void clearAll(@Nullable Location exitLocation) {
        // Create a copy to avoid ConcurrentModificationException
        for (UUID uuid : new HashMap<>(spectators).keySet()) {
            Player player = plugin.getServer().getPlayer(uuid);
            if (player != null && player.isOnline()) {
                removeSpectator(player);
                if (exitLocation != null) {
                    player.teleport(exitLocation);
                    plugin.debug("Teleported spectator " + player.getName() + " to exit location");
                }
            }
        }
        spectators.clear();
    }
    
    /**
     * Internal class to store spectator data
     */
    private static class SpectatorData {
        private final GameMode previousGameMode;
        private final BaseGame game;
        private final Location centerLocation;
        private final double maxRange;
        
        SpectatorData(GameMode previousGameMode, BaseGame game, Location centerLocation, double maxRange) {
            this.previousGameMode = previousGameMode;
            this.game = game;
            this.centerLocation = centerLocation;
            this.maxRange = maxRange;
        }
    }
}
