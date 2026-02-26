package com.github.meytiii.meypacks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent; // <-- Make sure this is imported
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

public class BackpackListener implements Listener {

    private final MeyPacks plugin;
    private final Map<UUID, Inventory> backpacks;
    private final DataManager dataManager;
    private final BackpackManager backpackManager;

    public BackpackListener(MeyPacks plugin, Map<UUID, Inventory> backpacks, DataManager dataManager, BackpackManager backpackManager) {
        this.plugin = plugin;
        this.backpacks = backpacks;
        this.dataManager = dataManager;
        this.backpackManager = backpackManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().setItem(8, backpackManager.createBackpackIcon());
        Inventory loadedBackpack = dataManager.loadBackpack(player);
        if (loadedBackpack != null) {
            backpacks.put(player.getUniqueId(), loadedBackpack);
            plugin.getLogger().info("Loaded backpack for " + player.getName());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        if (backpacks.containsKey(playerUUID)) {
            dataManager.saveBackpack(player, backpacks.get(playerUUID));
            backpacks.remove(playerUUID);
            plugin.getLogger().info("Saved and removed backpack for " + player.getName());
        }
    }
    
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            event.getPlayer().getInventory().setItem(8, backpackManager.createBackpackIcon());
        }, 1L);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (backpackManager.isBackpackIcon(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (event.getClickedInventory() == player.getInventory() && backpackManager.isBackpackIcon(clickedItem)) {
            event.setCancelled(true);
            UUID playerUUID = player.getUniqueId();
            Inventory backpack = backpacks.computeIfAbsent(playerUUID, id -> {
                String title = player.getName() + "'s MeyPack";
                return Bukkit.createInventory(player, 54, title);
            });
            player.openInventory(backpack);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (backpackManager.isBackpackIcon(itemInHand)) {
            if (event.getAction().name().contains("RIGHT_CLICK")) {
                event.setCancelled(true);
                UUID playerUUID = player.getUniqueId();
                Inventory backpack = backpacks.computeIfAbsent(playerUUID, id -> {
                    String title = player.getName() + "'s MeyPack";
                    return Bukkit.createInventory(player, 54, title);
                });
                player.openInventory(backpack);
            }
        }
    }
}