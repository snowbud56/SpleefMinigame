package com.snowbud56.tasks;

import com.snowbud56.constructors.Game;
import org.bukkit.scheduler.BukkitRunnable;

public class GameRunTask extends BukkitRunnable {

    private Game game;
    private int startIn = 11;

    public GameRunTask(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        if (startIn <= 1) {
            this.cancel();
            this.game.setState(Game.GameState.ACTIVE);
            this.game.sendMessage("&a[!] The game has started.");
            this.game.setMovementFrozen(false);
        } else {
            startIn -= 1;
            this.game.sendMessage("&c[*] The game will begin in " + startIn + " second" + (startIn == 1 ? "" : "s") + ".");
        }
    }
}
