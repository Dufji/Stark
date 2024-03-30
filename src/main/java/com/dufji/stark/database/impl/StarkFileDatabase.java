package com.dufji.stark.database.impl;

import com.dufji.stark.Stark;
import com.dufji.stark.database.StarkDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.UUID;
import java.util.logging.Level;

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

}
