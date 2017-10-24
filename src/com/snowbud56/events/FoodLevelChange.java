package com.snowbud56.events;

import com.snowbud56.SpleefMinigame;
import com.snowbud56.constructors.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChange implements Listener {
    @EventHandler
    public void FoodLevelChange(FoodLevelChangeEvent e) {
        Player p = (Player) e.getEntity();
        for (Game game : SpleefMinigame.getGames()) {
            if (game.getPlayers().contains(p) && !game.getGameState().equals(Game.GameState.ACTIVE)) {
                e.setCancelled(true);
            }
        }
    }
}
