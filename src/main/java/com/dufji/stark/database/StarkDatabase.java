package com.dufji.stark.database;

import com.dufji.stark.database.impl.StarkFileDatabase;
import com.dufji.stark.database.impl.StarkMongoDatabase;
import com.dufji.stark.database.impl.StarkMySQLDatabase;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.UUID;

public abstract class StarkDatabase {

    public abstract float getBalance(UUID uuid);
    public abstract void setBalance(UUID uuid, float balance);

    public abstract Integer getBalTopPosition(UUID uuid);

    public static StarkDatabase getFromKey(String key) {
        switch (key.toLowerCase()) {
            case "mysql":
                return new StarkMySQLDatabase();
            case "mongodb":
                return new StarkMongoDatabase();
            case "file":
                return new StarkFileDatabase();
            default:
                return null;
        }
    }

}
