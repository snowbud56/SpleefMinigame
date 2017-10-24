package com.snowbud56.events;

import com.snowbud56.SpleefMinigame;
import com.snowbud56.constructors.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        for (Game game : SpleefMinigame.getGames()) if (game.getPlayers().contains(p)) {
            game.sendMessage("&c[-] &7" + p.getName() + " left!");
            p.damage(p.getMaxHealth());
        }
    }
}
