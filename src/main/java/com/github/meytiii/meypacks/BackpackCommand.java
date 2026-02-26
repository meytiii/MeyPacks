package com.github.meytiii.meypacks;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.ChatColor;

import java.util.Map;
import java.util.UUID;

public class BackpackCommand implements CommandExecutor {

    private final Map<UUID, Inventory> backpacks;

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
            String title = player.getName() + "'s MeyPack";

            return Bukkit.createInventory(player, 54, title);
        });

        player.openInventory(backpack);

        return true;
    }
}