package com.dufji.stark;

import com.dufji.stark.database.MongoDBManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Stark extends JavaPlugin {

    private static @Getter Stark instance;
    private MongoDBManager mongoDBManager;

    @Override
    public void onEnable() {
        instance = this;
        mongoDBManager = new MongoDBManager("mongodb://localhost:27017");
        // FART
        instance = this;
    }

    @Override
    public void onDisable() {
        mongoDBManager.close();
    }

}
