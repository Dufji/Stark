package com.dufji.stark.model;

import com.dufji.stark.Stark;
import lombok.Getter;

import java.util.UUID;

@Getter
public class StarkPlayer {

    private final UUID uuid;

    public StarkPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public float getBalance() {
        return Stark.getInstance().getStarkDatabase().getBalance(uuid);
    }

    public void setBalance(float balance) {
        Stark.getInstance().getStarkDatabase().setBalance(uuid, balance);
    }

    public Integer getBalTopPosition() {
        return Stark.getInstance().getStarkDatabase().getBalTopPosition(uuid);
    }

}
