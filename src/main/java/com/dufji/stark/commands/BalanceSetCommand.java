package com.dufji.stark.commands;

import com.dufji.stark.Stark;
import com.dufji.stark.user.StarkPlayer;
import com.dufji.stark.utils.color.CC;
import dev.hyperskys.configurator.annotations.GetValue;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.annotation.CommandPermission;

public class BalanceSetCommand {

    public static @GetValue(file = "config.yml", path = "Messages.admin_balance_set") String adminBalanceSet = "&cFailed to load configuration message.";
    public static @GetValue(file = "config.yml", path = "Messages.player_balance_set") String playerBalanceSet = "&cFailed to load configuration message.";

    @Command({"balance set", "eco set"})
    @CommandPermission("stark.admin.balance.set")
    public void onBalanceSetCommand(Player player, OfflinePlayer target, float amount) {
        StarkPlayer starkTarget = new StarkPlayer(target.getUniqueId());
        starkTarget.setBalance(amount);
        player.sendMessage(CC.translate(adminBalanceSet.replaceAll("%player%", target.getName()).replaceAll("%amount%", String.valueOf(Stark.formatCurrency(amount)))));

        if(target.isOnline()) {
            target.getPlayer().sendMessage(CC.translate(playerBalanceSet.replaceAll("%amount%", String.valueOf(Stark.formatCurrency(amount)))));
        }
    }

}
