package com.github.meytiii.meypacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;

public class BackpackManager {

    private final MeyPacks plugin;
    private final NamespacedKey backpackItemKey;

    public BackpackManager(MeyPacks plugin) {
        this.plugin = plugin;
        this.backpackItemKey = new NamespacedKey(plugin, "backpack_item");
    }

    public ItemStack createBackpackIcon() {
        ItemStack item = new ItemStack(Material.ENDER_CHEST);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "MeyPack");
        meta.setLore(Collections.singletonList(ChatColor.GRAY + "Click to open your backpack!"));

        meta.addEnchant(Enchantment.RIPTIDE, 1, true);

        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.getPersistentDataContainer().set(backpackItemKey, PersistentDataType.BYTE, (byte) 1);

        item.setItemMeta(meta);
        return item;
    }

    public boolean isBackpackIcon(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return false;
        }
        return item.getItemMeta().getPersistentDataContainer().has(backpackItemKey, PersistentDataType.BYTE);
    }
}
