package org.brent.metallicspro;

import org.brent.metallicspro.blocks.BlockRegistry;
import org.brent.metallicspro.items.ItemRegistry;
import org.brent.metallicspro.listeners.blocks.AnvilDamageListener;
import org.brent.metallicspro.listeners.blocks.BlockOverrideListener;
import org.brent.metallicspro.materials.MaterialRegistry;
import org.brent.metallicspro.listeners.item.ItemEnterWaterListener;
import org.brent.metallicspro.listeners.item.ItemEnterWaterRegister;
import org.brent.metallicspro.listeners.recipe.CraftListener;
import org.brent.metallicspro.listeners.blocks.BlockBreakListener;

import org.brent.metallicspro.recpies.RecipeRegistry;
import org.brent.metallicspro.textures.TexturepackGenerate;
import org.brent.metallicspro.tools.ToolRegistry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MetallicsPro extends JavaPlugin {

    private static MetallicsPro plugin;
    private static BlockRegistry blockRegistry;
    private static ItemRegistry itemRegistry;
    private static MaterialRegistry materialRegistry;
    private static ToolRegistry toolRegistry;
    private static RecipeRegistry recipeRegistry;

    @Override
    public void onEnable() {
        plugin = this;

        recipeRegistry = new RecipeRegistry();
        itemRegistry = new ItemRegistry();

        materialRegistry = new MaterialRegistry(itemRegistry);
        toolRegistry = new ToolRegistry();
        blockRegistry = new BlockRegistry();

        // Block listeners
        BlockBreakListener.init();
        AnvilDamageListener.init();
        BlockOverrideListener.init();

        // Item listeners
        ItemEnterWaterRegister.init();
        ItemEnterWaterListener.init();

        // Recipe listeners
        CraftListener.init();

        Bukkit.getScheduler().runTaskLater(this, TexturepackGenerate::start, 5L);
    }

    @Override
    public void onDisable() { }

    public static MetallicsPro getPlugin() {
        return plugin;
    }

    public static BlockRegistry getBlockRegistry() {
        return blockRegistry;
    }

    public static ItemRegistry getItemRegistry() {
        return itemRegistry;
    }

    public static MaterialRegistry getMaterialRegistry() {
        return materialRegistry;
    }

    public static ToolRegistry getToolRegistry() {
        return toolRegistry;
    }

    public static RecipeRegistry getRecipeRegistry() {
        return recipeRegistry;
    }
}