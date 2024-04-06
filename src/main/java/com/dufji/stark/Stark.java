package com.dufji.stark;

import com.dufji.stark.commands.BalanceCommand;
import com.dufji.stark.commands.BalanceSetCommand;
import com.dufji.stark.commands.PayCommand;
import com.dufji.stark.commands.TopBalanceCommand;
import com.dufji.stark.database.StarkDatabase;
import com.dufji.stark.managers.PlaceholderManager;
import com.dufji.stark.utils.CC;
import com.dufji.stark.vault.VaultHook;
import dev.hyperskys.configurator.Configurator;
import dev.hyperskys.configurator.annotations.GetValue;
import dev.hyperskys.configurator.api.Configuration;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;



@Getter
public final class Stark extends JavaPlugin {

    private static @Getter Stark instance;
    private final Configuration configuration = new Configuration("config.yml");
    private StarkDatabase starkDatabase;
    private VaultHook vault_hook;
    private Economy economy = null;

    public static @GetValue(file = "config.yml", path = "Messages.CurrencySingular") String currencySingular = "Dollar";
    public static @GetValue(file = "config.yml", path = "Messages.CurrencyPlural") String currencyPlural = "Dollars";
    public static @GetValue(file = "config.yml", path = "Settings.database-type") String databaseKey  = "file";


    @Override
    public void onLoad() {
        instance = this;

        // Vault Setup
        if(getServer().getPluginManager().getPlugin("Vault") != null) {
            getServer().getConsoleSender().sendMessage(CC.translate("&aStark - Hooking into vault..."));
            getServer().getServicesManager().register(net.milkbowl.vault.economy.Economy.class, this.vault_hook = new VaultHook(this), (Plugin)this, ServicePriority.High);
        } else {
            vaultError("Vault not found");
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
                new PayCommand(this),
                new TopBalanceCommand()
        );



        configuration.init();




        // Registering Placeholders for PlaceholderAPI
        registerPlaceholders();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().log(java.util.logging.Level.INFO, "Disabling Stark plugin...");
    }



    public void registerPlaceholders() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            if(!new PlaceholderManager().register()) {
                getLogger().severe("Could not register placeholders!");
            }
        }
    }




    private void vaultError(String specific) {
        Bukkit.getConsoleSender().sendMessage("§8+------------------------------------+");
        Bukkit.getConsoleSender().sendMessage("             §cStark §6Plugin");
        Bukkit.getConsoleSender().sendMessage("              §4Disabling");
        Bukkit.getConsoleSender().sendMessage("§8");
        Bukkit.getConsoleSender().sendMessage("§f-> §c" + specific);
        Bukkit.getConsoleSender().sendMessage("§8+------------------------------------+");
    }

    public static String formatCurrency(double amount) {
        return String.format("%,.2f", amount);
    }

    public String getCurrency(double amount) {
        return (amount == 1.0D) ? currencySingular : currencyPlural;
    }

    private boolean setupEconomy() {
        if(getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

}
