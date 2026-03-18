package me.roinujnosde.titansbattle.listeners;

import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.jetbrains.annotations.NotNull;

import me.roinujnosde.titansbattle.BaseGame;
import me.roinujnosde.titansbattle.TitansBattle;

public class PlayerTeleportListener extends TBListener {

    public PlayerTeleportListener(@NotNull TitansBattle plugin) {
        super(plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onCommandTeleport(PlayerTeleportEvent event) {
        if (event.getCause() != TeleportCause.COMMAND) {
            return;
        }
        final Player player = event.getPlayer();

        BaseGame game = plugin.getBaseGameFrom(player);
        if (game != null && !Boolean.TRUE.equals(game.getConfig().isAllowCommandTeleport())) {
            plugin.getLogger().log(Level.INFO, "Teletransporte iniciado via comando foi cancelado por %s", player.getName());
            event.setCancelled(true);
        }
    }

}
