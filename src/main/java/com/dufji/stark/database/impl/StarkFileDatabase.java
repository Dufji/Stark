package com.dufji.stark.database.impl;

import com.dufji.stark.Stark;
import com.dufji.stark.database.StarkDatabase;
import com.dufji.stark.model.StarkPlayer;
import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class StarkFileDatabase extends StarkDatabase {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private JsonObject jsonObject;

    public StarkFileDatabase() {
        try {
            File dataFolder = new File(Stark.getInstance().getDataFolder().getPath(), "data");
            if (!dataFolder.exists()) dataFolder.mkdirs();
            File dataFile = new File(dataFolder, "data.json");
            dataFile.createNewFile();
            JsonObject jsonObject = new JsonObject();
            FileWriter fileWriter = new FileWriter(dataFile);
            fileWriter.write(gson.toJson(jsonObject));
            fileWriter.flush();
            fileWriter.close();
            this.jsonObject = jsonObject;
        } catch (Exception exception) {
            Stark.getInstance().getLogger().log(Level.SEVERE, "An error occurred while initializing the file database.");
        }
    }

    private void saveJsonObject() {
        try {
            File dataFolder = new File(Stark.getInstance().getDataFolder().getPath(), "data");
            File dataFile = new File(dataFolder, "data.json");
            dataFile.delete();
            dataFile.createNewFile();
            FileWriter fileWriter = new FileWriter(dataFile);
            fileWriter.write(gson.toJson(this.jsonObject));
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception exception) {
            Stark.getInstance().getLogger().log(Level.SEVERE, "An error occurred while saving the file database.");
        }
    }

    private void refreshJsonObject() {
        try {
            File dataFolder = new File(Stark.getInstance().getDataFolder().getPath(), "data");
            File dataFile = new File(dataFolder, "data.json");
            this.jsonObject = new JsonParser().parse(new FileReader(dataFile)).getAsJsonObject();
        } catch (Exception exception) {
            Stark.getInstance().getLogger().log(Level.SEVERE, "An error occurred while refreshing the file database.");
        }
    }

    @Override
    public float getBalance(UUID uuid) {
        refreshJsonObject();
        if (!jsonObject.has(uuid.toString())) {
            jsonObject.addProperty(uuid.toString(), 0);
            saveJsonObject();
            return 0;
        }

        return jsonObject.get(uuid.toString()).getAsFloat();
    }

    //TODO: Fix the issue of the balance resetting to 0 after a server restart
    @Override
    public void setBalance(UUID uuid, float balance) {
        refreshJsonObject();
        if (!jsonObject.has(uuid.toString())) {
            jsonObject.addProperty(uuid.toString(), balance);
            saveJsonObject();
        }

        jsonObject.remove(uuid.toString());
        jsonObject.addProperty(uuid.toString(), balance);
        saveJsonObject();
    }




    @Override
    public Integer getBalTopPosition(UUID uuid) {
        refreshJsonObject();

        if (!jsonObject.has(uuid.toString())) {
            jsonObject.addProperty(uuid.toString(), 0);
            saveJsonObject();
            return 0;
        }


        OfflinePlayer[] allPlayers = Bukkit.getOfflinePlayers();

        List<StarkPlayer> sortedPlayers = Arrays.stream(allPlayers)
                .map(player -> new StarkPlayer(player.getUniqueId()))
                .sorted(Comparator.comparing(StarkPlayer::getBalance).reversed())
                .collect(Collectors.toList());



        List<StarkPlayer> topPlayers = sortedPlayers.stream()
                .limit(jsonObject.entrySet().size())
                .collect(Collectors.toList());


        return topPlayers.indexOf(new StarkPlayer(uuid));
    }

}
