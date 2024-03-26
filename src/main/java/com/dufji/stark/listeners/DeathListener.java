package com.dufji.stark.listeners;

import com.dufji.stark.Stark;
import com.dufji.stark.models.StarkPlayer;
import com.dufji.stark.utils.CC;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

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
            killer.getPlayer().sendMessage(CC.translate("&eYou have received &6" + amount + " &efrom killing &6" + deadGuy.getPlayer().getName()));


            // Im thinking about adding that the dead guy loses money that will go to the killer
            // Let me know if you want me to add that
            // no dumb idea (what if they have no money!?!)


        }
    }

}
