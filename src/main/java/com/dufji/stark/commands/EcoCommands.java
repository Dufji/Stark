package com.dufji.stark.commands;

import com.dufji.stark.models.StarkPlayer;
import com.dufji.stark.utils.CC;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.util.List;

@SuppressWarnings("unused")
public class EcoCommands {

    @Command({"balance", "stark balance"})
    @CommandPermission("stark.balance")
    public void onBalanceCommand(Player player, @Optional Player target) {
        if(target == null) {
            StarkPlayer starkPlayer = new StarkPlayer(player);
            player.sendMessage(CC.translate("&eYour balance is: &6" + starkPlayer.getBalance()));
            return;
        }

        StarkPlayer starkPlayer = new StarkPlayer(target);
        player.sendMessage(CC.translate("&6" + starkPlayer.getName() + "'s &ebalance is: &6" + starkPlayer.getBalance()));
    }


    @Command({"pay", "stark pay"})
    @CommandPermission("stark.pay")
    public void onPayCommand(Player sender, Player target, double amount) {
        StarkPlayer starkSender = new StarkPlayer(sender);
        StarkPlayer starkTarget = new StarkPlayer(target);

        if(starkSender.getBalance() < amount) {
            sender.sendMessage(CC.translate("&cYou do not have enough money to pay that amount."));
            return;
        }

        starkSender.setBalance(starkSender.getBalance() - amount);
        starkTarget.setBalance(starkTarget.getBalance() + amount);
        sender.sendMessage(CC.translate("&eYou have paid &6" + amount + " &eto &6" + starkTarget.getName()));
        target.sendMessage(CC.translate("&6" + starkSender.getName() + " &ehas paid you &6" + amount));
    }


    @Command({"balance set", "stark balance set"})
    @CommandPermission("stark.balance.set")
    public void onSetBalanceCommand(Player sender, Player target, double amount) {

        if (amount < 0) {
            sender.sendMessage(CC.translate("&cYou cannot set a negative balance."));
            return;
        }

        StarkPlayer starkTarget = new StarkPlayer(target);
        starkTarget.setBalance(amount);
        sender.sendMessage(CC.translate("&eYou have set &6" + starkTarget.getName() + "'s &ebalance to &6" + amount));
    }

    @Command({"balance top", "baltop", "balancetop"})
    @CommandPermission("stark.balance.top")
    public void onBalanceTopCommand(Player sender) {
        sender.sendMessage(CC.translate("&eTop 10 balances:"));
        List<StarkPlayer> topBalances = new StarkPlayer(sender).getTopBalances();
        for (int i = 1; i <= topBalances.size(); i++) {
            sender.sendMessage(CC.translate("&7[" + i + "&7]" + " &6" + topBalances.get(i).getName() + " &e- &6" + topBalances.get(i).getBalance()));
        }
    }

}
