package com.dufji.stark;

import com.dufji.stark.commands.BalanceCommand;
import com.dufji.stark.commands.BalanceSetCommand;
import com.dufji.stark.commands.PayCommand;
import com.dufji.stark.commands.TopBalanceCommand;
import com.dufji.stark.database.StarkDatabase;
import com.dufji.stark.utils.PlaceholderManager;
import dev.hyperskys.configurator.Configurator;
import dev.hyperskys.configurator.annotations.GetValue;
import dev.hyperskys.configurator.api.Configuration;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;

@Getter
public final class Stark extends JavaPlugin {

    private static @Getter Stark instance;
    private final Configuration configuration = new Configuration("config.yml");
    private StarkDatabase starkDatabase;
    private Economy economy;
    private StarkVaultHook vault_hook;

    public static @GetValue(file = "config.yml", path = "Messages.CurrencySingular") String currencySingular = "Dollar";
    public static @GetValue(file = "config.yml", path = "Messages.CurrencyPlural") String currencyPlural = "Dollars";
    public static @GetValue(file = "config.yml", path = "Settings.database-type") String databaseKey = "file";


    @Override
    public void onLoad() {
        instance = this;

        // Vault Setup
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            StarkVaultHook vaultHook = new StarkVaultHook(this);
            getServer().getServicesManager().register(Economy.class, vaultHook, this, ServicePriority.Highest);
            economy = vaultHook;
        }

    }

    @Override
    public void onEnable() {

        starkDatabase = StarkDatabase.getFromKey(databaseKey);
        Configurator.setupConfigurator(this);
        BukkitCommandHandler handler = BukkitCommandHandler.create(this);
        handler.register(
                new BalanceCommand(),
                new BalanceSetCommand(),
                new PayCommand(),
                new TopBalanceCommand()
        );

        configuration.init();
        registerPlaceholders();

    }

    public void registerPlaceholders() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderManager().register();
        }
    }

    public static String formatCurrency(double amount) {
        return String.format("%,.2f", amount);
    }

    public String getCurrency(double amount) {
        return (amount == 1.0D) ? currencySingular : currencyPlural;
    }

    public static Stark getInstance(){
        return instance;
    }

    public StarkDatabase getStarkDatabase() {
        return starkDatabase;
    }
}
