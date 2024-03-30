package com.dufji.stark;

import com.dufji.stark.commands.BalanceCommand;
import com.dufji.stark.database.StarkDatabase;
import dev.hyperskys.configurator.Configurator;
import dev.hyperskys.configurator.annotations.GetValue;
import dev.hyperskys.configurator.api.Configuration;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;

@Getter
public final class Stark extends JavaPlugin {

    private static @Getter Stark instance;
    private final Configuration configuration = new Configuration("config.yml");
    public static @GetValue(file = "config.yml", path = "Settings.database-type") String databaseKey  = "file";
    private StarkDatabase starkDatabase;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        Configurator.setupConfigurator(this);
        BukkitCommandHandler handler = BukkitCommandHandler.create(this);
        handler.register(new BalanceCommand());
        configuration.init();
        starkDatabase = StarkDatabase.getFromKey(databaseKey);
    }

    @Override
    public void onDisable() {

    }

}
