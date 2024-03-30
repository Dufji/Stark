package com.dufji.stark.commands;

import com.dufji.stark.Stark;
import com.dufji.stark.enums.Language;
import com.dufji.stark.models.StarkPlayer;
import com.dufji.stark.utils.CC;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.util.List;
import java.util.logging.Level;

@SuppressWarnings("unused")
public class EcoCommands {

    @Command({"balance", "stark balance"})
    @CommandPermission("stark.balance")
    public void onBalanceCommand(Player player, @Optional Player target) {
        if(target == null) {
            StarkPlayer starkPlayer = new StarkPlayer(player.getUniqueId());
            player.sendMessage(CC.translate("&eYour balance is: &6" + starkPlayer.getBalance()));
            return;
        }

        StarkPlayer starkPlayer = new StarkPlayer(target.getUniqueId());
        player.sendMessage(CC.translate("&6" + player.getDisplayName() + "'s &ebalance is: &6" + starkPlayer.getBalance()));
    }


    @Command({"pay", "stark pay"})
    @CommandPermission("stark.balance.pay")
    public void onPayCommand(Player sender, Player target, double amount) {
        try {
            StarkPlayer starkSender = new StarkPlayer(sender.getUniqueId());
            StarkPlayer starkTarget = new StarkPlayer(target.getUniqueId());

            if(starkSender.getBalance() < amount) {
                sender.sendMessage(CC.translate(Language.ERROR_NOT_ENOUGH_MONEY.toString()));
                return;
            }

            starkSender.setBalance(starkSender.getBalance() - amount);
            starkTarget.setBalance(starkTarget.getBalance() + amount);
            sender.sendMessage(CC.translate(Language.SUCCESS_PAY.toString().replace("%player%", target.getDisplayName()).replace("%amount%", String.valueOf(amount))));
            target.sendMessage(CC.translate(Language.NOTICE_PAY.toString().replace("%player%", sender.getDisplayName()).replace("%amount%", String.valueOf(amount))));
        } catch (Exception e) {
            sender.sendMessage(CC.translate(Language.ERROR_SOMETHING_WENT_WRONG.toString()));
        }
    }


    @Command({"balance set", "stark balance set", "eco set"})
    @CommandPermission("stark.balance.set")
    public void onSetBalanceCommand(Player sender, Player target, double amount) {


        if (amount < 0) {
            sender.sendMessage(CC.translate(Language.ERROR_NEGATIVE_BALANCE.toString()));
            return;
        }
        try {
            StarkPlayer starkTarget = new StarkPlayer(target.getUniqueId());
            starkTarget.setBalance(amount);
            sender.sendMessage(CC.translate(Language.SUCCESS_SET_BALANCE.toString().replace("%player%", target.getDisplayName()).replace("%amount%", String.valueOf(amount))));
            target.sendMessage(CC.translate(Language.NOTICE_BALANCE_SET.toString().replace("%amount%",String.valueOf(amount))));
        } catch (Exception e) {
            sender.sendMessage(CC.translate(Language.ERROR_SOMETHING_WENT_WRONG.toString()));

        }
    }

    @Command({"balance top", "baltop", "balancetop"})
    @CommandPermission("stark.balance.top")
    public void onBalanceTopCommand(Player sender) {
        sender.sendMessage(CC.translate("&eTop 10 balances:"));
        List<StarkPlayer> topBalances = new StarkPlayer(sender.getUniqueId()).getTopBalances();
        // topBalances.forEach(starkPlayer -> sender.sendMessage(CC.translate("&7[" + starkPlayer.getBaltopPosition() + "&7]" +  "&6" + starkPlayer.getPlayer().getDisplayName() + " &e- &6" + starkPlayer.getBalance())));
        for (int i = 1; i <= topBalances.size(); i++) {
            sender.sendMessage(CC.translate("&7[" + i + "&7]" + " &6" + topBalances.get(i).getPlayer().getDisplayName() + " &e- &6" + topBalances.get(i).getBalance()));
        }
    }

}
