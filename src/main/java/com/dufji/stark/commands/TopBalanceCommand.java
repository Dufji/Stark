package com.dufji.stark.commands;

import com.dufji.stark.Stark;
import com.dufji.stark.user.StarkPlayer;
import com.dufji.stark.utils.color.CC;
import dev.hyperskys.configurator.annotations.GetValue;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TopBalanceCommand {

    public static @GetValue(file = "config.yml", path = "Messages.top_playerbalances") String topBalances = "&cFailed to load configuration message.";

    @Command({"topbalance","topbals","baltop"})
    @CommandPermission("stark.command.topbalance")
    public void onTopBalanceCommand(Player sender) {
        OfflinePlayer[] allPlayers = Bukkit.getOfflinePlayers();

        List<StarkPlayer> sortedPlayers = Arrays.stream(allPlayers)
                .map(player -> new StarkPlayer(player.getUniqueId()))
                .sorted(Comparator.comparing(StarkPlayer::getBalance).reversed())
                .collect(Collectors.toList());

        List<StarkPlayer> topPlayers = sortedPlayers.stream()
                .limit(10)
                .collect(Collectors.toList());

        sender.sendMessage(CC.translate(topBalances));
        for (int i = 0; i < topPlayers.size(); i++) {
            StarkPlayer player = topPlayers.get(i);
            sender.sendMessage(CC.translate("&6") + (i + 1) + ". " + Bukkit.getOfflinePlayer(player.getUuid()).getName() + ": $" + Stark.formatCurrency(player.getBalance()));
        }

    }
}
