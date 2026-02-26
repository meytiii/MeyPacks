package com.github.meytiii.meypacks;

import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class MeyPacks extends JavaPlugin {

    private final Map<UUID, Inventory> backpacks = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("MeyPacks has been enabled!");

        this.getCommand("meypack").setExecutor(new BackpackCommand(this.backpacks));
    }

    @Override
    public void onDisable() {
        getLogger().info("MeyPacks has been disabled!");
    }
}