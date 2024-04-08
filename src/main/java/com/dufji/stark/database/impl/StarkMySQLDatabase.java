package com.dufji.stark.database.impl;

import com.dufji.stark.Stark;
import com.dufji.stark.database.StarkDatabase;
import dev.hyperskys.configurator.annotations.GetValue;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.UUID;

public class StarkMySQLDatabase extends StarkDatabase {

    public static @GetValue(file = "config.yml", path = "Databases.MySQL.username") String username = "root";
    public static @GetValue(file = "config.yml", path = "Databases.MySQL.password") String password = "password";
    public static @GetValue(file = "config.yml", path = "Databases.MySQL.connection-uri") String connectionURI = "mongodb://localhost:27017";
    private Connection connection;

    public StarkMySQLDatabase() {
        try {
            connection = DriverManager.getConnection(connectionURI, username, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS stark");
            statement.executeUpdate("USE stark");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS balances (uuid VARCHAR(36), balance FLOAT, PRIMARY KEY (uuid))");
        } catch (Exception exception) {
            Stark.getInstance().getLogger().severe("An error occurred while connecting to the MongoDB database.");
            Bukkit.getPluginManager().disablePlugin(Stark.getInstance());
        }
    }

    @Override
    public float getBalance(UUID uuid) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT balance FROM balances WHERE uuid = ?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getFloat("balance");
            }
        } catch (Exception exception) {
            Stark.getInstance().getLogger().severe("An error occurred while getting the balance of a player.");
        }
        return -1;
    }

    @Override
    public void setBalance(UUID uuid, float balance) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO balances (uuid, balance) VALUES (?, ?) ON DUPLICATE KEY UPDATE balance = ?");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setFloat(2, balance);
            preparedStatement.setFloat(3, balance);
            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            Stark.getInstance().getLogger().severe("An error occurred while setting the balance of a player.");
        }
    }

    @Override
    public Integer getBalTopPosition(UUID uuid) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM balances WHERE balance > (SELECT balance FROM balances WHERE uuid = ?)");
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) + 1;
            }
        } catch (Exception exception) {
            Stark.getInstance().getLogger().severe("An error occurred while getting the balance top position of a player.");
        }
        return -1;
    }

}
