package com.dufji.stark.gui;

import com.dufji.stark.Stark;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class GUI implements Listener {
    private final Stark plugin;


    public GUI(Stark plugin) {
        this.plugin = plugin;
    }


    public void openGUI(Player player, InventoryHolder holder, String title, int size) {
        Inventory inventory = Bukkit.createInventory(holder, size, title);
        player.openInventory(inventory);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null || !(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        InventoryHolder holder = event.getInventory().getHolder();

        if(holder instanceof GUIHolder) {
            event.setCancelled(true);
            ((GUIHolder) holder).handleClick(event);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof GUIHolder) {
            ((GUIHolder) holder).handleClose(event);
        }
    }


    public void updateGUISlot(Inventory inventory, int slot, ItemStack item) {
        inventory.setItem(slot, item);
    }

    protected ItemStack createGuiItem(final Material material, final String name) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        return item;
    }

    public void fillGUI(Inventory inventory, ItemStack item) {
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, item);
        }
    }


    public abstract void handleClick(InventoryClickEvent event);

    public abstract void handleClose(InventoryCloseEvent event);
}
