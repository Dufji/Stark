package com.dufji.stark;

import com.dufji.stark.user.StarkPlayer;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;


import java.util.ArrayList;
import java.util.List;

public class StarkVaultHook extends AbstractEconomy {

    private final Stark main;

    public StarkVaultHook(Stark main) {
        this.main = main;
    }

    public String getName() {
        return this.main.getDescription().getName();
    }

    public boolean isEnabled() {
        return this.main.isEnabled();
    }

    public int fractionalDigits() {
        return -1;
    }

    public String format(double balance) {
        return Stark.formatCurrency(balance);
    }

    @Override
    public String currencyNamePlural() {
        return this.main.getCurrency(2.0D);
    }

    @Override
    public String currencyNameSingular() {
        return this.main.getCurrency(1.0D);
    }

    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {

        if (amount < 0.0D)
            return new EconomyResponse(
                    0.0D,
                    0.0D,
                    EconomyResponse.ResponseType.FAILURE,
                    "Cannot withdraw negative funds"
            );

        StarkPlayer starkPlayer = new StarkPlayer(player.getUniqueId());
        if (starkPlayer.getBalance() >= amount) {
            starkPlayer.setBalance(starkPlayer.getBalance() - (float) amount);
            return new EconomyResponse(
                    amount,
                    starkPlayer.getBalance(),
                    EconomyResponse.ResponseType.SUCCESS,
                    null);
        }


        return new EconomyResponse(0.0D,
                starkPlayer.getBalance(),
                EconomyResponse.ResponseType.FAILURE,
                "Insufficient funds");

    }

    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return withdrawPlayer(player, amount);
    }

    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        if (amount < 0.0D)
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Cannot desposit negative funds");
        StarkPlayer starkPlayer = new StarkPlayer(player.getUniqueId());
        starkPlayer.setBalance(starkPlayer.getBalance() + (float) amount);


        return new EconomyResponse(amount, starkPlayer.getBalance(), EconomyResponse.ResponseType.SUCCESS, null);
    }

    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return depositPlayer(player, amount);
    }

    public boolean createPlayerAccount(OfflinePlayer player) {
        return new StarkPlayer(player.getUniqueId()).getBalance() == 0.0F;
    }

    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return createPlayerAccount(player);
    }

    public double getBalance(OfflinePlayer player) {
        return new StarkPlayer(player.getUniqueId()).getBalance();
    }

    public double getBalance(OfflinePlayer player, String worldName) {
        return getBalance(player);
    }

    public boolean has(OfflinePlayer player, double amount) {
        return new StarkPlayer(player.getUniqueId()).getBalance() >= (float) amount;
    }

    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return has(player, amount);
    }

    public boolean hasAccount(OfflinePlayer player) {
        return new StarkPlayer(player.getUniqueId()).getBalance() >= 0.0F;

    }

    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return hasAccount(player);
    }

    public boolean createPlayerAccount(String name) {
        return createPlayerAccount(Bukkit.getOfflinePlayer(name));
    }

    public boolean createPlayerAccount(String name, String worldName) {
        return createPlayerAccount(name);
    }

    public EconomyResponse depositPlayer(String name, double amount) {
        return depositPlayer(Bukkit.getOfflinePlayer(name), amount);
    }

    public EconomyResponse depositPlayer(String name, String worldName, double amount) {
        return depositPlayer(name, amount);
    }

    public double getBalance(String name) {
        return getBalance(Bukkit.getOfflinePlayer(name));
    }

    public double getBalance(String name, String worldName) {
        return getBalance(name);
    }

    public boolean has(String name, double amount) {
        return has(Bukkit.getOfflinePlayer(name), amount);
    }

    public boolean has(String name, String worldName, double amount) {
        return has(name, amount);
    }

    public boolean hasAccount(String name) {
        return hasAccount(Bukkit.getOfflinePlayer(name));
    }

    public boolean hasAccount(String name, String worldName) {
        return hasAccount(name);
    }

    public EconomyResponse withdrawPlayer(String name, double amount) {
        return withdrawPlayer(Bukkit.getOfflinePlayer(name), amount);
    }

    public EconomyResponse withdrawPlayer(String name, String worldName, double amount) {
        return withdrawPlayer(name, amount);
    }

    public boolean hasBankSupport() {
        return false;
    }

    public List<String> getBanks() {
        return new ArrayList<>();
    }

    public EconomyResponse isBankMember(String name, String worldName) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "The bank is not supported!");
    }

    public EconomyResponse isBankOwner(String name, String worldName) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "The bank is not supported!");
    }

    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "The bank is not supported!");
    }

    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "The bank is not supported!");
    }

    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "The bank is not supported!");
    }

    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "The bank is not supported!");
    }

    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "The bank is not supported!");
    }

    public EconomyResponse createBank(String name, String worldName) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "The bank is not supported!");
    }
}
