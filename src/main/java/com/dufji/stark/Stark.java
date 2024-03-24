package com.dufji.stark;

import com.dufji.stark.database.MongoDBManager;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Stark extends JavaPlugin {

    private static @Getter Stark instance;
    private MongoDBManager mongoDBManager;

    private FileConfiguration config;

    @Override
    public void onEnable() {

        try {
            instance = this;
            config = Stark.getInstance().getConfig();
            mongoDBManager = new MongoDBManager(config.getString("mongodb.connectionURL"));
        } catch (Exception exception) {
            getLogger().severe("Failed to connect to the MongoDB database.");
            getServer().getPluginManager().disablePlugin(this);
        }

    }


    @Override
    public void onDisable() {
        mongoDBManager.close();
    }

}
