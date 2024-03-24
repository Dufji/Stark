package com.dufji.stark.database;

import com.dufji.stark.Stark;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

@Getter
public class MongoDBManager {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> dataCollection;

    public MongoDBManager(String connectionURL) {
        try {
            mongoClient = MongoClients.create(connectionURL);
            database = mongoClient.getDatabase("Stark");
            dataCollection = database.getCollection("storage_data");
        } catch (Exception exception) {
            Stark.getInstance().getLogger().log(Level.SEVERE, "Failed to connect to the MongoDB database.");
        }
    }

    public double getUserBalance(UUID uuid) {
        try {
            if (dataCollection.find(new Document("uuid", uuid.toString())).first() == null) {
                Document playerDocument = new Document("uuid", uuid.toString());
                playerDocument.put("balance", 0.00);
                dataCollection.insertOne(playerDocument);
                return 0.00;
            }

            return Objects.requireNonNull(dataCollection.find(new Document("uuid", uuid.toString())).first()).getDouble("balance");
        } catch (NullPointerException exception) {
            Document playerDocument = new Document("uuid", uuid.toString());
            playerDocument.put("balance", 0.00);
            dataCollection.replaceOne(new Document("uuid", uuid.toString()), playerDocument);
            return 0.00;
        }
    }

    public void setUserBalance(UUID uuid, double balance) {

        if (dataCollection.find(new Document("uuid", uuid.toString())).first() == null) {
            Document playerDocument = new Document("uuid", uuid.toString());
            playerDocument.put("balance", balance);
            dataCollection.insertOne(playerDocument);
            return;
        }

        Document playerDocument = new Document("uuid", uuid.toString());
        playerDocument.append("balance", balance);
        dataCollection.updateOne(new Document("uuid", uuid.toString()), playerDocument);
    }

    public void close() {
        mongoClient.close();
    }

}
