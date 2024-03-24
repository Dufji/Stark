package com.dufji.stark.models;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

@Getter
public class StarkPlayer {

    private final UUID uuid;
    private final String name;
    private final Player player;
    private @Getter @Setter double balance;

    public StarkPlayer(Player player) {
        this.balance = 0.0;
        this.player = player;
        this.uuid = player.getUniqueId();
        this.name = player.getName();
    }

    public List<StarkPlayer> getTopBalances() {
        return null;
    }

}
