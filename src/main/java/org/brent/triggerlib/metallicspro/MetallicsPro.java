package org.brent.triggerlib.metallicspro;

import org.brent.triggerlib.metallicspro.listeners.ore.OreBreakListener;
import org.brent.triggerlib.metallicspro.listeners.ore.OreBreakRegister;
import org.brent.triggerlib.metallicspro.blocks.ore.OreBlockRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public final class MetallicsPro extends JavaPlugin {

    private static MetallicsPro plugin;
    private static OreBlockRegistry oreBlockRegistry;

    @Override
    public void onEnable() {
        plugin = this;
        oreBlockRegistry = new OreBlockRegistry();

        OreBreakRegister.init();
        OreBreakListener.init();
    }

    @Override
    public void onDisable() { }

    public static MetallicsPro getPlugin() {
        return plugin;
    }

    public static OreBlockRegistry getOreBlockRegistry() {
        return oreBlockRegistry;
    }
}
