package com.github.meytiii.meypacks;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.UUID;

public class BackpackCommand implements CommandExecutor {

    // A field to hold the map that we get from the main class
    private final Map<UUID, Inventory> backpacks;

    // The Constructor: This is called from the main class.
    // It receives the backpacks map and stores it in our field.
    public BackpackCommand(Map<UUID, Inventory> backpacks) {
        this.backpacks = backpacks;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command!");
            return true;
        }

        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();

        Inventory backpack = backpacks.computeIfAbsent(playerUUID, id -> {
            return Bukkit.createInventory(player, 27, "My Portal Backpack");
        });

        player.openInventory(backpack);

        return true;
    }
}