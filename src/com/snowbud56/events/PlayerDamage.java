package com.snowbud56.events;

import com.snowbud56.SpleefMinigame;
import com.snowbud56.constructors.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamage implements Listener {
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Game game = SpleefMinigame.getGame((Player) e.getEntity());
            if (game == null) return;
            if (!game.getGameState().equals(Game.GameState.ACTIVE)) e.setCancelled(true);
        }
    }
}
