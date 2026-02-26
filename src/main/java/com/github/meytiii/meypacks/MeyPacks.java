package com.meytiii.meypacks;

import org.bukkit.plugin.java.JavaPlugin;

public final class MeyPacks extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("MeyPacks has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("MeyPacks has been disabled!");
    }
}