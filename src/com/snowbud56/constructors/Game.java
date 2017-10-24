package com.snowbud56.constructors;

import com.snowbud56.RollbackHandler;
import com.snowbud56.SpleefMinigame;
import com.snowbud56.data.DataHandler;
import com.snowbud56.tasks.GameCountdownTask;
import com.snowbud56.utils.ChatUtils;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class Game {

    private String displayName;
    private int maxPlayers;
    private int minPlayers;
    private World world;
    private List<Location> spawnPoints;
    private GameState gameState = GameState.LOBBY;
    private Location lobbyPoint;

    private Set<Player> players;
    private Set<Player> spectators;
    private boolean isMovementFrozen = false;

    public Game(String gameName) {
        FileConfiguration fileConfiguration = DataHandler.getInstance().getGameInfo();
        this.spectators = new HashSet<>();
        this.players = new HashSet<>();
        this.spawnPoints = new ArrayList<>();
        this.displayName = fileConfiguration.getString("games." + gameName + ".displayName");
        this.maxPlayers = fileConfiguration.getInt("games." + gameName + ".maxPlayers");
        this.minPlayers = fileConfiguration.getInt("games." + gameName + ".minPlayers");
        Bukkit.createWorld(new WorldCreator(fileConfiguration.getString("games." + gameName + ".world") + "_active"));
        this.world = Bukkit.getWorld(fileConfiguration.getString("games." + gameName + ".world") + "_active");
        try {
            String[] values = fileConfiguration.getString("games." + gameName + ".lobbyPoint").split(",");
            double x = Double.parseDouble(values[0].split(":")[1]);
            double y = Double.parseDouble(values[1].split(":")[1]);
            double z = Double.parseDouble(values[2].split(":")[1]);
            lobbyPoint = new Location(world, x, y, z);
        } catch (Exception e) {
            SpleefMinigame.getInstance().getLogger().severe("Failed to load lobbyPoint for game + '" + gameName + "'. ExceptionType: " + e);
        }
        for (String location : DataHandler.getInstance().getGameInfo().getStringList("games." + gameName + ".spawnPoints")) {
            // X:0,Y:0,Z:0
            try {
                String[] values = location.split(",");
                double x = Double.parseDouble(values[0].split(":")[1]);
                double y = Double.parseDouble(values[1].split(":")[1]);
                double z = Double.parseDouble(values[2].split(":")[1]);
                Location loc = new Location(world, x, y, z);
                spawnPoints.add(loc);
            } catch (Exception e) {
                SpleefMinigame.getInstance().getLogger().severe("Failed to load spawnPoint with metadata " + location + " for game + '" + gameName + "'. ExceptionType: " + e);
            }
        }
    }

    public void joinGame(Player player) {
        if (players.contains(player)) {
            player.sendMessage(ChatUtils.format("&c[!] You're already in the game!"));
            return;
        }
        if (isState(GameState.LOBBY) || isState(GameState.STARTING)) {
            if (getPlayers().size() == getMaxPlayers()) player.sendMessage(ChatUtils.format("&c[!] That game is full!"));
            else {
                getPlayers().add(player);
                player.teleport((isState(GameState.LOBBY) || isState(GameState.STARTING)) ? lobbyPoint : spawnPoints.get(0));
                player.setMaxHealth(20);
                player.setHealth(player.getMaxHealth());
                player.setFoodLevel(25);
                sendMessage("&a[+] &7" + player.getName() + " &7(&a" + getPlayers().size() + "&7/&a" + getMaxPlayers() + "&7).");
            }
            if (getPlayers().size() == getMinPlayers() && !isState(GameState.STARTING)) {
                startGame();
            }
        } else {
            getSpectators().add(player);
        }
    }
    public void startGame() {
        setState(GameState.STARTING);
        sendMessage("&a[!] You will be teleported in 20 seconds!");
        /*int id = 0;
        for (Player player : getPlayers()) {
            try {
                playerSpawnPoints.put(player, spawnPoints.get(id));
                player.teleport(spawnPoints.get(id));
                id += 1;
            } catch (IndexOutOfBoundsException ex) {
                SpleefMinigame.getInstance().getLogger().severe("Not enough spawn points to satisfy game needs (Game is " + getDisplayName() + ")");
            }
        }*/
        new GameCountdownTask(this).runTaskTimer(SpleefMinigame.getInstance(), 0, 20);
    }

    public void endGame() {
        setState(GameState.ENDING);
        if (players.size() == 1) {
            for (Player player : players) Bukkit.broadcastMessage(ChatUtils.format("&a[!] " + player.getName() + " won the game in " + getDisplayName() + "!"));
        }
        else Bukkit.broadcastMessage(ChatUtils.format("&a[!] No one won the game in " + getDisplayName() + "!"));
        for (Player player : getSpectators()) {
            player.teleport(Bukkit.getWorld("world").getSpawnLocation());
            player.setGameMode(GameMode.SURVIVAL);
            player.setMaxHealth(20);
            player.setHealth(player.getMaxHealth());
            player.setFoodLevel(25);
            player.getInventory().clear();
        }
        for (Player player : getPlayers()) {
            player.teleport(Bukkit.getWorld("world").getSpawnLocation());
            player.setGameMode(GameMode.SURVIVAL);
            player.setMaxHealth(20);
            player.setHealth(player.getMaxHealth());
            player.setFoodLevel(25);
            player.getInventory().clear();
        }
        RollbackHandler.getRollbackHandler().rollback(getWorld());
        spectators.clear();
        players.clear();
        setState(GameState.LOBBY);
    }

    public void switchToSpectator(Player p) {
        players.remove(p);
        spectators.add(p);
    }

    public boolean isMovementFrozen() {
        return isMovementFrozen;
    }

    public void setMovementFrozen(boolean movementFrozen) {
        isMovementFrozen = movementFrozen;
    }

    public boolean isState(GameState state) {
        return gameState.equals(state);
    }

    public void setState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public String getDisplayName() {
        return displayName;
    }

    public World getWorld() {
        return world;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public List<Location> getSpawnPoints() {
        return spawnPoints;
    }

    public void sendMessage(String message) {
        for (Player player : getPlayers()) {
            player.sendMessage(ChatUtils.format(message));
        }
    }

    public Set<Player> getSpectators() {
        return spectators;
    }

    public enum GameState {
        LOBBY, STARTING, ACTIVE, ENDING
    }
}
