package org.brent.metallicspro;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.items.ItemRegistry;
import org.brent.metallicspro.items.metal.MetalRegistry;
import org.brent.metallicspro.listeners.recipe.CraftListener;
import org.brent.metallicspro.listeners.ore.OreBreakListener;
import org.brent.metallicspro.listeners.ore.OreBreakRegister;
import org.brent.metallicspro.blocks.ore.OreBlockRegistry;

import org.brent.metallicspro.recpies.RecipeRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

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

        Bukkit.getScheduler().runTaskLater(this, () -> {
            Bukkit.getLogger().info("====File paths for items registered====");
            for (NamespacedKey key : CustomItem.keys) {
                Bukkit.getLogger().info(key.asString());
            }
        }, 40);
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