package com.github.meytiii.meypacks;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class DataManager {

    private final MeyPacks plugin;

    public DataManager(MeyPacks plugin) {
        this.plugin = plugin;
    }

    public void saveBackpack(Player player, Inventory backpack) {
        File playerFile = new File(plugin.getDataFolder(), player.getUniqueId().toString() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

        config.set("backpack.contents", backpack.getContents());

        try {
            config.save(playerFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save backpack for " + player.getName());
            e.printStackTrace();
        }
    }

    public Inventory loadBackpack(Player player) {
        File playerFile = new File(plugin.getDataFolder(), player.getUniqueId().toString() + ".yml");

        if (!playerFile.exists()) {
            return null;
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

        List<?> itemList = config.getList("backpack.contents");
        if (itemList == null) {
            return null;
        }

        ItemStack[] contents = itemList.toArray(new ItemStack[0]);

        String title = player.getName() + "'s MeyPack";
        Inventory backpack = Bukkit.createInventory(player, 54, title);
        backpack.setContents(contents);

        return backpack;
    }
}
