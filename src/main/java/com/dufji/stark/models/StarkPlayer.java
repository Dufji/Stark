package com.dufji.stark.models;

import com.dufji.stark.Stark;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

@Getter
public class StarkPlayer {

    private final UUID uuid;
    private final Player player;

    public StarkPlayer(UUID uuid) {
        this.uuid = uuid;
        this.player = Bukkit.getPlayer(uuid);
    }

    public double getBalance() {
        return Stark.getInstance().getMongoDBManager().getUserBalance(uuid);
    }

    public void setBalance(double balance) {
        Stark.getInstance().getMongoDBManager().setUserBalance(uuid, balance);
    }

    public List<StarkPlayer> getTopBalances() {
        return null;
    }

    public int getBaltopPosition() {
        return 0;
    }

}
