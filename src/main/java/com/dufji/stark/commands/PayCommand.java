package com.dufji.stark.commands;

import com.dufji.stark.user.StarkPlayer;
import com.dufji.stark.utils.color.CC;
import dev.hyperskys.configurator.annotations.GetValue;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Cooldown;

public class PayCommand {

    public static @GetValue(file = "config.yml", path = "Messages.player-not-found") String playerNotFound  = "&cFailed to load configuration message.";
    public static @GetValue(file = "config.yml", path = "Messages.notice-player-paid-other") String noticePlayerPaidOther  = "&cFailed to load configuration message.";
    public static @GetValue(file = "config.yml", path = "Messages.notice-player-received-other") String noticePlayerReceiveOther  = "&cFailed to load configuration message.";
    public static @GetValue(file = "config.yml", path = "Messages.insufficient-balance") String insufficientBalance  = "&cFailed to load configuration message.";
    public static @GetValue(file = "config.yml", path = "Messages.self-pay-error") String selfPayError  = "&cFailed to load configuration message.";
    public static @GetValue(file = "config.yml", path = "Messages.minimum-amount") String minimumAmount  = "&cFailed to load configuration message.";


    @Command({"pay", "send"})
    @Cooldown(value = 5)
    public void onPayCommand(Player player, String targetName, float amount) {

        if (targetName.equals(player.getName())) {
            player.sendMessage(CC.translate(selfPayError));
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);
        if (!target.hasPlayedBefore()) {
            player.sendRawMessage(CC.translate(playerNotFound.replaceAll("%player%", targetName)));
            return;
        }

        StarkPlayer starkPlayer = new StarkPlayer(player.getUniqueId());
        StarkPlayer starkTarget = new StarkPlayer(target.getUniqueId());

        if (starkPlayer.getBalance() < amount) {
            player.sendMessage(CC.translate(insufficientBalance));
            return;
        }

        if (amount <= 5) {
            player.sendMessage(CC.translate(minimumAmount));
            return;
        }

        starkPlayer.setBalance(starkPlayer.getBalance() - amount);
        starkTarget.setBalance(starkTarget.getBalance() + amount);
        player.sendMessage(CC.translate(noticePlayerPaidOther.replaceAll("%player%", targetName).replace("%amount%", String.valueOf(amount))));

        if (target.isOnline()) {
            target.getPlayer().sendMessage(CC.translate(noticePlayerReceiveOther.replaceAll("%player%", player.getName()).replace("%amount%", String.valueOf(amount))));
        }

    }

}
