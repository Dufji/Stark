package com.dufji.stark.database.impl;

import com.dufji.stark.Stark;
import com.dufji.stark.database.StarkDatabase;
import com.google.gson.*;

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
            if(!dataFile.exists()) {
                dataFile.createNewFile();
                FileWriter fileWriter = new FileWriter(dataFile);
                JsonObject jsonObject = new JsonObject();
                fileWriter.write(gson.toJson(jsonObject));
                fileWriter.flush();
                fileWriter.close();
                this.jsonObject = jsonObject;
            } else {
                this.jsonObject = new JsonParser().parse(new FileReader(dataFile)).getAsJsonObject();

            }
        } catch (Exception exception) {
            Stark.getInstance().getLogger().log(Level.SEVERE, "An error occurred while initializing the file database.");
        }
    }

    private void saveJsonObject() {
        try {
            File dataFolder = new File(Stark.getInstance().getDataFolder().getPath(), "data");
            if (!dataFolder.exists()) dataFolder.mkdirs();
            File dataFile = new File(dataFolder, "data.json");
            if (!dataFile.exists()) dataFile.createNewFile();
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
            return;
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

        Map<UUID, Float> map = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            map.put(UUID.fromString(entry.getKey()), entry.getValue().getAsFloat());
        }

        LinkedHashMap<UUID, Float> sortedMap = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        List<UUID> list = new ArrayList<>(sortedMap.keySet());
        return list.indexOf(uuid) + 1;
    }

}
