package com.github.meytiii.meypacks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class MeyPacks extends JavaPlugin implements Listener {

    private final Map<UUID, Inventory> backpacks = new HashMap<>();
    private DataManager dataManager;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        
        this.dataManager = new DataManager(this);
        
        this.getServer().getPluginManager().registerEvents(this, this);

        getLogger().info("MeyPacks has been enabled!");
        this.getCommand("meypack").setExecutor(new BackpackCommand(this.backpacks));

        for (Player player : getServer().getOnlinePlayers()) {
            loadPlayerBackpack(player);
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


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        loadPlayerBackpack(event.getPlayer());
    }
    
    private void loadPlayerBackpack(Player player) {
        Inventory loadedBackpack = dataManager.loadBackpack(player);
        if (loadedBackpack != null) {
            this.backpacks.put(player.getUniqueId(), loadedBackpack);
            getLogger().info("Loaded backpack for " + player.getName());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (backpacks.containsKey(playerUUID)) {
            dataManager.saveBackpack(player, backpacks.get(playerUUID));
            
            backpacks.remove(playerUUID);
            getLogger().info("Saved and removed backpack for " + player.getName());
        }
    }
}
