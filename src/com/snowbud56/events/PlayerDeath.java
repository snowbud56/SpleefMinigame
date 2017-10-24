package com.snowbud56.events;

import com.snowbud56.SpleefMinigame;
import com.snowbud56.constructors.Game;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        for (Game game : SpleefMinigame.getGames()) {
            if (game.getPlayers().contains(p)) {
                handle(e, game);
            }
        }
    }

    private void handle(PlayerDeathEvent e, Game game) {
        Player player = e.getEntity();
        e.setDeathMessage(null);
        player.setMaxHealth(20);
        player.setHealth(player.getMaxHealth());
        player.setGameMode(GameMode.SPECTATOR);
        game.switchToSpectator(player);
        if (game.getPlayers().size() <= 1) {
            game.endGame();
        }
    }
}
