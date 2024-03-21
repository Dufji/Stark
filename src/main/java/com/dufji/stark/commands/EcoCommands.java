package com.dufji.stark.commands;

import com.dufji.stark.models.StarkPlayer;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.bukkit.annotation.CommandPermission;

public class EcoCommands {

    @Command("balance")
    @CommandPermission("stark.balance")
    public void onBalanceCommand(StarkPlayer sender, @Optional StarkPlayer target) {
        // TODO: Implement balance command
        if(target == null) {
            // If the target is null, then we want to send the sender's balance
        } else {
            // If the target is not null, then we want to send the target's balance
        }
    }


    @Command("pay")
    @CommandPermission("stark.pay")
    public void onPayCommand(Player sender, Player target, double amount) {
        // TODO: Implement pay command
    }


    @Command("balance set")
    @CommandPermission("stark.balance.set")
    public void onSetBalanceCommand(StarkPlayer sender, StarkPlayer target, double amount) {
        // TODO: Implement set balance command
    }

    @Command({"balance top", "baltop", "balancetop"})
    @CommandPermission("stark.balance.top")
    public void onBalanceTopCommand(StarkPlayer sender) {
        // TODO: Implement balance top command
    }







}
