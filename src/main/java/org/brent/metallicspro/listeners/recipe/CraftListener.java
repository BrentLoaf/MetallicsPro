package org.brent.metallicspro.listeners.recipe;

import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.recpies.types.CraftBuilder;
import org.brent.metallicspro.recpies.types.RecipeBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onItemCraft(CraftItemEvent event) {
        ItemStack cursor = event.getCursor();
        ItemStack result = event.getRecipe().getResult();

        if (!event.getClick().isShiftClick() &&
                        cursor.getType() != Material.AIR &&
                        !cursor.isSimilar(result)) return;

        if (!(event.getRecipe() instanceof Keyed key)) return;

        if (!key.getKey().getNamespace().equalsIgnoreCase(MetallicsPro.getPlugin().getName())) return;

        NamespacedKey recipeKey = key.getKey();
        RecipeBuilder<?, ?> recipe = MetallicsPro.getRecipeRegistry().getFromKey(recipeKey);
        if (!(recipe instanceof CraftBuilder craft)) return;

        // Return items if it should
        ItemStack[] items = event.getInventory().getMatrix();
        for (int slot = 0; slot < items.length; slot++) {

            ItemStack itemStack = items[slot];
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;

            if (craft.shouldReturn(itemStack)) returnItem(itemStack.clone(), slot, event, craft);
        }

        // Give a random result if should
        ItemStack randomResult = recipe.getRandomResult();
        if (randomResult != null) event.getInventory().setResult(randomResult);
    }

    private int maxCraft(ItemStack[] items, CraftBuilder recipe) {
        int lowest = Integer.MAX_VALUE;

        for (ItemStack itemStack : items) {
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;
            if (recipe.shouldReturn(itemStack)) continue;

            lowest = Math.min(lowest, itemStack.getAmount());
        }

        return lowest;
    }

    private void returnItem(ItemStack toReturn, int slot, CraftItemEvent event, CraftBuilder recipe) {
        ItemStack returnItem = toReturn.clone();

        int amount = event.getClick().isShiftClick() ?
                maxCraft(event.getInventory().getMatrix(), recipe) + returnItem.getAmount() :
                1 + returnItem.getAmount();

        returnItem.setAmount(amount);

        int slotIndex = slot + 1;

        event.getInventory().setItem(slotIndex, returnItem);
    }

    public static void init() {
        JavaPlugin plugin = MetallicsPro.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(new CraftListener(), plugin);
    }
}
