package com.github.meytiii.meypacks;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class MeyPacks extends JavaPlugin {

    private final Map<UUID, Inventory> backpacks = new HashMap<>();
    private DataManager dataManager;
    private BackpackManager backpackManager;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        this.dataManager = new DataManager(this);
        this.backpackManager = new BackpackManager(this);
        
        this.getServer().getPluginManager().registerEvents(new BackpackListener(this, this.backpacks, this.dataManager, this.backpackManager), this);

        getLogger().info("MeyPacks has been enabled!");
        this.getCommand("meypack").setExecutor(new BackpackCommand(this.backpacks));

        for (Player player : getServer().getOnlinePlayers()) {
            player.getInventory().setItem(8, backpackManager.createBackpackIcon());
            Inventory loadedBackpack = dataManager.loadBackpack(player);
            if (loadedBackpack != null) {
                backpacks.put(player.getUniqueId(), loadedBackpack);
            }
        }
    }

    @Override
    public void onDisable() {
        for (Map.Entry<UUID, Inventory> entry : backpacks.entrySet()) {
            Player player = getServer().getPlayer(entry.getKey());
            if (player != null) {
                dataManager.saveBackpack(player, entry.getValue());
            }
        }
        getLogger().info("MeyPacks has been disabled and data saved!");
    }
}
