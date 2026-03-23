package org.brent.metallicspro;

import org.brent.metallicspro.items.ItemRegistry;
import org.brent.metallicspro.items.metal.MetalRegistry;
import org.brent.metallicspro.listeners.items.CraftListener;
import org.brent.metallicspro.listeners.ore.OreBreakListener;
import org.brent.metallicspro.listeners.ore.OreBreakRegister;
import org.brent.metallicspro.blocks.ore.OreBlockRegistry;

import org.brent.metallicspro.recpies.RecipeRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public final class MetallicsPro extends JavaPlugin {

    private static MetallicsPro plugin;
    private static OreBlockRegistry oreBlockRegistry;
    private static ItemRegistry itemRegistry;
    private static MetalRegistry metalRegistry;
    private static RecipeRegistry recipeRegistry;

    @Override
    public void onEnable() {
        plugin = this;

        recipeRegistry = new RecipeRegistry();
        oreBlockRegistry = new OreBlockRegistry();
        itemRegistry = new ItemRegistry();
        metalRegistry = new MetalRegistry(itemRegistry);

        OreBreakRegister.init();
        OreBreakListener.init();
        CraftListener.init();
    }

    @Override
    public void onDisable() { }

    public static MetallicsPro getPlugin() {
        return plugin;
    }

    public static OreBlockRegistry getOreBlockRegistry() {
        return oreBlockRegistry;
    }

    public static ItemRegistry getItemRegistry() {
        return itemRegistry;
    }

    public static MetalRegistry getMetalRegistry() {
        return metalRegistry;
    }

    public static RecipeRegistry getRecipeRegistry() {
        return recipeRegistry;
    }

}
