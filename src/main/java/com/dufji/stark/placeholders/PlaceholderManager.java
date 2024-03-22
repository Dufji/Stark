package com.dufji.stark.placeholders;

import com.dufji.stark.models.StarkPlayer;
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
        return "dufji";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }


    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        StarkPlayer sPlayer = new StarkPlayer(player);

        if(sPlayer != null && sPlayer.isOnline()) {
            switch(identifier) {
                case "balance":
                    return String.valueOf(sPlayer.getBalance());
                case "player_name":
                    return sPlayer.getName();
            }
        }
        return null;
    }
}
