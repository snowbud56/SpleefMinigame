package com.snowbud56.data;

import com.snowbud56.SpleefMinigame;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class DataHandler {
    private File gameInfoFile;
    private FileConfiguration gameInfo;

    private static DataHandler instance = new DataHandler();

    public static DataHandler getInstance() {
        return instance;
    }

    private DataHandler() {
        this.gameInfoFile = new File("plugins/Spleef/gameInfo.yml");
        if (!this.gameInfoFile.exists()) {
            try {
                this.gameInfoFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.gameInfo = YamlConfiguration.loadConfiguration(this.gameInfoFile);
    }

    public FileConfiguration getGameInfo() {
        return gameInfo;
    }
    public void saveGameInfo() {
        try {
            this.gameInfo.save(this.gameInfoFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
