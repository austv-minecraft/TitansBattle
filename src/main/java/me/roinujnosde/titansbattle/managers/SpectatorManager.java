package me.roinujnosde.titansbattle.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
     * @param centerLocation The center location for the spectator range
     * @param range The maximum range in blocks for the spectator
     */
    public void addSpectator(@NotNull Player player, @NotNull Location centerLocation, double range) {
        SpectatorData data = new SpectatorData(
            player.getGameMode(), 
            centerLocation.clone(),
            range
        );
        spectators.put(player.getUniqueId(), data);
        player.setGameMode(GameMode.SPECTATOR);
        plugin.debug("Added spectator: " + player.getName() + " with range " + range);
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
     * Clears all spectators (used when game ends)
     */
    public void clearAll() {
        // Create a copy to avoid ConcurrentModificationException
        for (UUID uuid : new HashMap<>(spectators).keySet()) {
            Player player = plugin.getServer().getPlayer(uuid);
            if (player != null && player.isOnline()) {
                removeSpectator(player);
            }
        }
        spectators.clear();
    }
    
    /**
     * Internal class to store spectator data
     */
    private static class SpectatorData {
        private final GameMode previousGameMode;
        private final Location centerLocation;
        private final double maxRange;
        
        SpectatorData(GameMode previousGameMode, Location centerLocation, double maxRange) {
            this.previousGameMode = previousGameMode;
            this.centerLocation = centerLocation;
            this.maxRange = maxRange;
        }
    }
}
