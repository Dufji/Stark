package com.dufji.stark.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public abstract class GUIHolder implements InventoryHolder {


    public abstract void handleClick(InventoryClickEvent event);

    public abstract void handleClose(InventoryCloseEvent event);

}
