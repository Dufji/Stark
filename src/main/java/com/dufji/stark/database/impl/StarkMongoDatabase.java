package com.dufji.stark.database.impl;

import com.dufji.stark.database.StarkDatabase;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.hyperskys.configurator.annotations.GetValue;
import org.bson.Document;

import java.util.*;
import java.util.stream.Collectors;

public class StarkMongoDatabase extends StarkDatabase {

    public static @GetValue(file = "config.yml", path = "Databases.MongoDB.connection-uri") String connectionURI = "mongodb://localhost:27017";
    private final MongoCollection<Document> dataCollection;

    public StarkMongoDatabase() {
        MongoClient mongoClient = MongoClients.create(connectionURI);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("stark");
        dataCollection = mongoDatabase.getCollection("data");
    }

    @Override
    public float getBalance(UUID uuid) {

        if (dataCollection.find(new Document("uuid", uuid.toString())).first() == null) {
            Document document = new Document("uuid", uuid.toString());
            document.put("balance", "0");
            dataCollection.insertOne(document);
        }

        return Float.parseFloat(dataCollection.find(new Document("uuid", uuid.toString())).first().getString("balance"));
    }

    @Override
    public void setBalance(UUID uuid, float balance) {

        if (dataCollection.find(new Document("uuid", uuid.toString())).first() == null) {
            Document document = new Document("uuid", uuid.toString());
            document.put("balance", Float.toString(balance));
            dataCollection.insertOne(document);
            return;
        }

        Document document = dataCollection.find(new Document("uuid", uuid.toString())).first();
        document.replace("balance", Float.toString(balance));
        dataCollection.replaceOne(new Document("uuid", uuid.toString()), document);
    }

    @Override
    public Integer getBalTopPosition(UUID uuid) {

        if (dataCollection.find(new Document("uuid", uuid.toString())).first() == null) {
            Document document = new Document("uuid", uuid.toString());
            document.put("balance", "0");
            dataCollection.insertOne(document);
            return 0;
        }

        // doubt this will work but it's a start
        // TODO: Make dufji fix this and test it
        return dataCollection.find().sort(new Document("balance", -1)).into(new ArrayList<>()).stream().map(Document::toString).collect(Collectors.toList()).indexOf(uuid.toString()) + 1;
    }

}
