package com.dufji.stark.listeners;

import com.dufji.stark.Stark;
import com.dufji.stark.enums.Language;
import com.dufji.stark.models.StarkPlayer;
import com.dufji.stark.utils.CC;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

// Dufji Task #3
public class DeathListener implements Listener {

    private static Stark starkPlugin;

    public DeathListener(Stark plugin) {
        starkPlugin = plugin;
    }


    @EventHandler
    public static void onDeathEvent(PlayerDeathEvent event) {
        FileConfiguration config = starkPlugin.getConfig();

        if (config.getBoolean("death-event.giveMoney")) {

            // Creating our two players
            StarkPlayer killer = new StarkPlayer(event.getEntity().getKiller().getUniqueId());
            StarkPlayer deadGuy = new StarkPlayer(event.getEntity().getPlayer().getUniqueId());

            // Getting the values from the config
            int minKillMoney = config.getInt("death-event.minKillMoney");
            int maxKillMoney = config.getInt("death-event.maxKillMoney");

            // Generating a random amount of money
            int amount = (int) (Math.random() * (maxKillMoney - minKillMoney + 1) + minKillMoney);

            // Adding money to the killer
            killer.setBalance(killer.getBalance() + amount);
            killer.getPlayer().sendMessage(Language.NOTICE_BALANCE_ADD.toString().replace("%amount%", String.valueOf(amount)).replace("%player%",deadGuy.getPlayer().getName()));


            if(!(deadGuy.getBalance() <= 0)) {
                deadGuy.setBalance(deadGuy.getBalance() - amount);
                deadGuy.getPlayer().sendMessage(CC.translate(Language.NOTICE_BALANCE_DEATH.toString().replace("%amount%", String.valueOf(amount)).replace("%player%",killer.getPlayer().getName())));
            }


        }
    }

}
