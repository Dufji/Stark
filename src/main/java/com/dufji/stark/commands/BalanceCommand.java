package com.dufji.stark.commands;

import com.dufji.stark.Stark;
import com.dufji.stark.model.StarkPlayer;
import com.dufji.stark.utils.CC;
import dev.hyperskys.configurator.annotations.GetValue;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.bukkit.annotation.CommandPermission;

public class BalanceCommand {

    public static @GetValue(file = "config.yml", path = "Messages.view-balance-self") String viewBalanceSelf  = "&cFailed to load configuration message.";
    public static @GetValue(file = "config.yml", path = "Messages.view-balance-other") String viewBalanceOther  = "&cFailed to load configuration message.";

    @Command({"balance", "bal"})
    @CommandPermission("stark.command.balance")
    public void onBalanceCommand(Player player, @Optional String targetName) {
        if (targetName == null) {
            StarkPlayer starkPlayer = new StarkPlayer(player.getUniqueId());
            player.sendMessage(CC.translate(viewBalanceSelf.replaceAll("%balance%", Stark.formatCurrency(starkPlayer.getBalance()))));
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);
        StarkPlayer starkPlayer = new StarkPlayer(target.getUniqueId());
        player.sendMessage(CC.translate(viewBalanceOther.replaceAll("%player%", targetName).replace("%balance%", Stark.formatCurrency(starkPlayer.getBalance()))));
    }

}
