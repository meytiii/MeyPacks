package com.github.meytiii.meypacks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.UUID;

public class BackpackCommand implements CommandExecutor {

    private final Map<UUID, Inventory> backpacks;

    public BackpackCommand(Map<UUID, Inventory> backpacks) {
        this.backpacks = backpacks;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("meypack.use")) {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }

            openBackpack(player);
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("open")) {
            if (!sender.hasPermission("meypack.admin.open")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Player '" + args[1] + "' is not online.");
                return true;
            }

            if (sender instanceof Player) {
                Player admin = (Player) sender;
                openBackpack(target, admin);
            } else {
                sender.sendMessage(ChatColor.RED + "This command must be run by a player to open the GUI.");
            }
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Invalid usage. Use /meypack or /meypack open <player>");
        return true;
    }

    private void openBackpack(Player player) {
        openBackpack(player, player);
    }
    
    private void openBackpack(Player target, Player viewer) {
        UUID targetUUID = target.getUniqueId();
        Inventory backpack = backpacks.computeIfAbsent(targetUUID, id -> {
            int size = getBackpackSize(target);
            String title = target.getName() + "'s MeyPack";
            return Bukkit.createInventory(target, size, title);
        });
        viewer.openInventory(backpack);
    }

    private int getBackpackSize(Player player) {
        if (player.hasPermission("meypack.size.6")) return 54;
        if (player.hasPermission("meypack.size.5")) return 45;
        if (player.hasPermission("meypack.size.4")) return 36;
        if (player.hasPermission("meypack.size.3")) return 27;
        if (player.hasPermission("meypack.size.2")) return 18;
        if (player.hasPermission("meypack.size.1")) return 9;
        return 9; 
    }
}