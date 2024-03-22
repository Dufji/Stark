package com.dufji.stark.models;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

@Getter
public class StarkPlayer {

    private final UUID uuid;
    private final String name;
    private final Player player;

    public StarkPlayer(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.name = player.getName();
    }

    public double getBalance() {
        return 0;
    }

    public void setBalance(double balance) {
        // TODO: implement logic for the setBalance method
    }

    public List<StarkPlayer> getTopBalances() {
        return null;
    }

}
