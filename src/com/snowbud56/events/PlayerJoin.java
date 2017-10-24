package com.snowbud56.events;

import com.snowbud56.SpleefMinigame;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    /*@EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (SpleefMinigame.getInstance().getConfig().getBoolean("single-server-mode")) {
            Player p = e.getPlayer();
            p.setGameMode(GameMode.ADVENTURE);
            p.setMaxHealth(20);
            p.setHealth(p.getMaxHealth());
            p.setFoodLevel(25);
            Double x, y, z;
            try {
                String[] values = SpleefMinigame.getInstance().getConfig().getString("lobby-point.location").split(",");
                x = Double.parseDouble(values[0].split(":")[1]);
                y = Double.parseDouble(values[1].split(":")[1]);
                z = Double.parseDouble(values[2].split(":")[1]);
                p.teleport(new Location(Bukkit.getWorld(SpleefMinigame.getInstance().getConfig().getString("lobby-point.world")), x, y, z));
            } catch (Exception ex) {
                SpleefMinigame.getInstance().getLogger().severe("Main lobby point failed with exception: " + ex);
                ex.printStackTrace();
            }
        }
    }*/
}
