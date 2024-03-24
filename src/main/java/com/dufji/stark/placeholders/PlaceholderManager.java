package com.dufji.stark.placeholders;

import com.dufji.stark.models.StarkPlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class PlaceholderManager extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "Stark";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Dufji, HyperSkys";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }


    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        StarkPlayer sPlayer = new StarkPlayer(player.getUniqueId());
        if(sPlayer.getPlayer().isOnline()) {
            switch (identifier) {
                case "balance":
                    return String.valueOf(sPlayer.getBalance());
                case "baltop_position":
                    return String.valueOf(sPlayer.getBaltopPosition());
                default:
                    return "Unknown placeholder";
            }
        }

        return null;
    }

}
