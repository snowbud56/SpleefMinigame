package com.snowbud56;

import com.snowbud56.constructors.Game;
import com.snowbud56.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpleefCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        if (args.length == 0) {
            sender.sendMessage(ChatUtils.format("&c[!] Invalid usage! Please provide a valid subcommand."));
            sender.sendMessage(ChatUtils.format("&c[!] Subcommands: join"));
            return false;
        }
        Player p = (Player) sender;
        if (args[0].equalsIgnoreCase("join")) {
            Game game = SpleefMinigame.getGame("example");
            if (game == null) p.sendMessage(ChatUtils.format("&c[!] Something went wrong! (That game doesn't exist!)"));
            else game.joinGame(p);
        } else if (args[0].equalsIgnoreCase("start")) {
            if (args.length >= 2) {
                Game game = SpleefMinigame.getGame(args[1]);
                game.sendMessage("&a[!] " + p.getName() + " has started the game.");
                game.startGame();
            } else {
                sender.sendMessage(ChatUtils.format("&c[!] Invalid usage! Please supply a game to start."));
            }
        } else if (args[0].equalsIgnoreCase("stop")) {
            if (args.length >= 2) {
                Game game = SpleefMinigame.getGame(args[1]);
                game.sendMessage("&c[!] " + p.getName() + " has stopped the game.");
                game.endGame();
            } else {
                sender.sendMessage(ChatUtils.format("&c[!] Invalid usage! Please supply a game to stop."));
            }
        }
        return false;
    }
}
