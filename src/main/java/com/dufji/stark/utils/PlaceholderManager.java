package com.dufji.stark.utils;

import com.dufji.stark.Stark;
import com.dufji.stark.user.StarkPlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderManager extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "stark";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Dufji";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {

        if(player != null && player.isOnline()) {
            StarkPlayer starkPlayer = new StarkPlayer(player.getUniqueId());
            switch (identifier.toLowerCase()) {
                case "balance":
                    return Stark.formatCurrency(starkPlayer.getBalance());

                case "baltop_position":
                    return String.valueOf(starkPlayer.getBalTopPosition());

                default:
                    return null;
            }
        }

        return null;
    }


}
