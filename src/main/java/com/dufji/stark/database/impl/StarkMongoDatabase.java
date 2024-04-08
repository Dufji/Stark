package com.dufji.stark.database.impl;

import com.dufji.stark.Stark;
import com.dufji.stark.database.StarkDatabase;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.hyperskys.configurator.annotations.GetValue;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.stream.Collectors;

public class StarkMongoDatabase extends StarkDatabase {

    public static @GetValue(file = "config.yml", path = "Databases.MongoDB.connection-uri") String connectionURI = "jdbc:mysql://localhost:3306/database";
    private MongoCollection<Document> dataCollection = null;

    public StarkMongoDatabase() {
        try {
            MongoClient mongoClient = MongoClients.create(connectionURI);
            MongoDatabase mongoDatabase = mongoClient.getDatabase("stark");
            dataCollection = mongoDatabase.getCollection("data");
        } catch (Exception exception) {
            Stark.getInstance().getLogger().severe("An error occurred while connecting to the MongoDB database.");
            Bukkit.getPluginManager().disablePlugin(Stark.getInstance());
        }
    }

    @Override
    public float getBalance(UUID uuid) {
        try {
            if (dataCollection.find(new Document("uuid", uuid.toString())).first() == null) {
                Document document = new Document("uuid", uuid.toString());
                document.put("balance", "0");
                dataCollection.insertOne(document);
                return 0;
            }

            return Float.parseFloat(dataCollection.find(new Document("uuid", uuid.toString())).first().getString("balance"));
        } catch (Exception exception) {
            Stark.getInstance().getLogger().severe("An error occurred while getting the balance of " + uuid.toString() + ".");
            return -1;
        }
    }

    @Override
    public void setBalance(UUID uuid, float balance) {
        try {
            if (dataCollection.find(new Document("uuid", uuid.toString())).first() == null) {
                Document document = new Document("uuid", uuid.toString());
                document.put("balance", Float.toString(balance));
                dataCollection.insertOne(document);
                return;
            }

            Document document = dataCollection.find(new Document("uuid", uuid.toString())).first();
            document.replace("balance", Float.toString(balance));
            dataCollection.replaceOne(new Document("uuid", uuid.toString()), document);
        } catch (Exception exception) {
            Stark.getInstance().getLogger().severe("An error occurred while setting the balance of " + uuid.toString() + ".");
        }
    }

    @Override
    public Integer getBalTopPosition(UUID uuid) {
        try {
            if (dataCollection.find(new Document("uuid", uuid.toString())).first() == null) {
                Document document = new Document("uuid", uuid.toString());
                document.put("balance", "0");
                dataCollection.insertOne(document);
                return 0;
            }

            List<Document> documents = dataCollection.find().into(new ArrayList<>());
            documents.sort(Comparator.comparingDouble(o -> -Float.parseFloat(o.getString("balance"))));
            List<String> uuids = documents.stream().map(document -> document.getString("uuid")).collect(Collectors.toList());
            return uuids.indexOf(uuid.toString()) + 1;
        } catch (Exception exception) {
            Stark.getInstance().getLogger().severe("An error occurred while getting the balance top position of " + uuid.toString() + ".");
            return -1;
        }
    }

}
