package com.dufji.stark.models;

import com.dufji.stark.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;


public class StarkPlayer {
    private UUID uuid;
    private String name;
    private double balance;
    private final Player player;

    public StarkPlayer(UUID uuid, String name, double balance) {
        this.uuid = uuid;
        this.name = name;
        this.balance = balance;
        this.player = Bukkit.getServer().getPlayer(uuid);
    }

    public StarkPlayer(Player player) {
        this.player = player;
    }

    public String getName() {
        return this.name;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        // TODO: implement logic for the setBalance method
    }

    public void addBalance(double amount) {
        // TODO: implement logic for the addBalance method
    }

    public void removeBalance(double amount) {
        // TODO: implement logic for the removeBalance method
    }

   public void sendMessage(String message) {
       Bukkit.getServer().getPlayer(this.uuid).sendMessage(CC.translate(message));
   }

    public Player getPlayer() {
         return this.player;
    }


    public boolean isOnline() {
        return this.player.isOnline();
    }








}
