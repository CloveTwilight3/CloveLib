package org.com.clovelib;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CloveLib extends JavaPlugin {
    private static CloveLib instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("CloveLib has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("CloveLib has been disabled!");
    }

    public static CloveLib getInstance() {
        return instance;
    }

    public boolean canUseCommand(String command, org.bukkit.entity.Player player) {
        if (isJailPluginEnabled() && isPlayerJailed(player)) {
            return false;
        }
        return true;
    }

    public boolean isJailPluginEnabled() {
        return Bukkit.getPluginManager().isPluginEnabled("JailPlugin");
    }

    public boolean isPlayerJailed(org.bukkit.entity.Player player) {
        JavaPlugin jailPlugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin("JailPlugin");
        if (jailPlugin == null) {
            return false;
        }
        try {
            return (boolean) jailPlugin.getClass().getMethod("isPlayerJailed", org.bukkit.entity.Player.class).invoke(jailPlugin, player);
        } catch (Exception e) {
            getLogger().severe("Failed to check jailed status: " + e.getMessage());
            return false;
        }
    }
}
