package com.dufji.stark.database.impl;

import com.dufji.stark.database.StarkDatabase;

import java.util.UUID;

public class StarkMySQLDatabase extends StarkDatabase {

    @Override
    public float getBalance(UUID uuid) {
        return 0;
    }

    @Override
    public void setBalance(UUID uuid, float balance) {

    }

    @Override
    public Integer getBalTopPosition(UUID uuid) {
        return 0;
    }


}
