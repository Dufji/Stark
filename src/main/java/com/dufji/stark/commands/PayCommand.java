package com.dufji.stark.commands;

import com.dufji.stark.Stark;
import com.dufji.stark.model.StarkPlayer;
import com.dufji.stark.utils.CC;
import dev.hyperskys.configurator.annotations.GetValue;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.annotation.CommandPermission;

public class PayCommand {

    public static @GetValue(file = "config.yml", path = "Messages.insufficient_balance") String notEnoughMoney = "&cFailed to load configuration message.";
    public static @GetValue(file = "config.yml", path = "Messages.notice_player_paid_other") String paidPlayer = "&cFailed to load configuration message.";
    public static @GetValue(file = "config.yml", path = "Messages.notice_player_received_other") String receivedMoney = "&cFailed to load configuration message.";

    public static @GetValue(file = "config.yml", path = "Messages.invalid_amount") String invalidAmount = "&cFailed to load configuration message.";


    private final Stark plugin;

    public PayCommand(Stark plugin) {
        this.plugin = plugin;
    }

    @Command({"pay", "sendmoney", "send"})
    @CommandPermission("stark.command.pay")
    public void onPayCommand(Player player, Player target, float amount) {
        StarkPlayer starkPlayer = new StarkPlayer(player.getUniqueId());
        StarkPlayer starkTarget = new StarkPlayer(target.getUniqueId());

        if (starkPlayer.getBalance() < amount) {
            player.sendMessage(CC.translate(notEnoughMoney));
            return;
        }

        if(amount <= 0) {
            player.sendMessage(CC.translate(invalidAmount));
            return;
        }

        starkPlayer.setBalance(starkPlayer.getBalance() - amount);
        starkTarget.setBalance(starkTarget.getBalance() + amount);

        player.sendMessage(CC.translate(paidPlayer.replaceAll("%player%", target.getName()).replaceAll("%amount%", Stark.formatCurrency(amount))));
        target.sendMessage(CC.translate(receivedMoney.replaceAll("%player%", player.getName()).replaceAll("%amount%", Stark.formatCurrency(amount))));
    }
}
