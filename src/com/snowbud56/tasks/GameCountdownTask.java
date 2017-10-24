package com.snowbud56.tasks;

import com.snowbud56.SpleefMinigame;
import com.snowbud56.constructors.Game;
import com.snowbud56.utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class GameCountdownTask extends BukkitRunnable {
    @Override
    public void run() {
        if (game.isState(Game.GameState.STARTING)) {
            time--;
            if (time == 0) {
                new GameRunTask(game).runTaskTimer(SpleefMinigame.getInstance(), 0, 20);
                this.game.setMovementFrozen(true);
                ItemStack item = new ItemStack(Material.DIAMOND_SPADE, 1);
                ItemMeta im = item.getItemMeta();
                im.spigot().setUnbreakable(true);
                im.setDisplayName(ChatUtils.format("&9&lThe Ice Breaker"));
                item.setItemMeta(im);
                item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 100);
                int id = 0;
                for (Player player : game.getPlayers()) {
                    player.getInventory().addItem(item);
                    try {
                        player.teleport(game.getSpawnPoints().get(id));
                        id += 1;
                    } catch (IndexOutOfBoundsException ex) {
                        SpleefMinigame.getInstance().getLogger().severe("Not enough spawn points to satisfy game needs (Game is " + game.getDisplayName() + ")");
                    }
                }
                cancel();
            } else if (time == 15 || time == 10 || time <= 5) game.sendMessage("&a[!] You will be teleported in " + time + " seconds!");
        } else cancel();

    }

    private int time = 20;
    private Game game;

    public GameCountdownTask(Game game) {
        this.game = game;
    }
}
