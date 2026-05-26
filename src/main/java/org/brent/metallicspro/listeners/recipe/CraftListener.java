package org.brent.metallicspro.listeners.recipe;

import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.recpies.Ingredient;
import org.brent.metallicspro.recpies.types.CraftBuilder;
import org.brent.metallicspro.recpies.types.RecipeBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public class CraftListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onItemCraft(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack cursor = event.getCursor();
        ItemStack result = event.getRecipe().getResult();

        boolean isShiftClick = event.getClick().isShiftClick();

        if (!isShiftClick &&
                        cursor.getType() != Material.AIR &&
                        !cursor.isSimilar(result)) return;

        if (!(event.getRecipe() instanceof Keyed key)) return;

        if (!key.getKey().getNamespace().equalsIgnoreCase(MetallicsPro.getPlugin().getName())) return;

        NamespacedKey recipeKey = key.getKey();
        RecipeBuilder<?, ?> recipe = MetallicsPro.getRecipeRegistry().getFromKey(recipeKey);
        if (!(recipe instanceof CraftBuilder craft)) return;

        if (isShiftClick
                && itemsThatCanFit(player.getInventory(), craft.getAllPossibleResults()) == 0
                && !craft.getAllPossibleResults().isEmpty()) return;

        int amount = toCraft(event, craft);

        // Return items if it should
        ItemStack[] items = event.getInventory().getMatrix();
        for (int slot = 0; slot < items.length; slot++) {

            ItemStack itemStack = items[slot];
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;

            if (craft.shouldReturn(itemStack)) returnItem(itemStack.clone(), slot, event, craft, amount);
        }

        // Give a random result if should
        if (amount == 1) {
            ItemStack finalResult = recipe.getRandomResult() == null ?
                    recipe.useResultEditor(result, event.getInventory().getMatrix()) :
                    recipe.getRandomResult();
            event.getInventory().setResult(finalResult);
        } else if (!craft.getAllPossibleResults().isEmpty()) {
            Bukkit.getScheduler().runTaskLater(MetallicsPro.getPlugin(), () -> {
                removeAllSimilarItems(player.getInventory(), craft.getResult());

                for (int i = amount; i > 0; i--) {
                    ItemStack thisResult = recipe.getRandomResult() == null ?
                            recipe.useResultEditor(result, event.getInventory().getMatrix()) :
                            recipe.getRandomResult();

                    HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(thisResult);
                    leftover.values().forEach(j ->
                            player.getWorld().dropItemNaturally(player.getLocation(), j)
                    );
                }
            }, 1L);
        }
    }

    public void removeAllSimilarItems(Inventory inventory, ItemStack itemToRemove) {
        ItemStack[] storage = inventory.getStorageContents();

        for (int i = 0; i < storage.length; i++) {
            ItemStack current = storage[i];

            if (current != null && current.isSimilar(itemToRemove)) inventory.setItem(i, null);
        }
    }

    private int itemsThatCanFit(Inventory inventory, List<ItemStack> toDoubleCheck) {
        int amount = 0;

        for (ItemStack slot : inventory.getStorageContents()) {
            for (ItemStack item : toDoubleCheck) {
                if (slot == null || slot.getType() == Material.AIR) {
                    amount += item.getMaxStackSize();
                    continue;
                }

                if (slot.isSimilar(item)) {
                    amount += item.getMaxStackSize() - slot.getAmount();
                }
            }
        }

        return amount;
    }

    private void returnItem(ItemStack toReturn, int slot, CraftItemEvent event, CraftBuilder recipe, int amount) {
        ItemStack returnItem = toReturn.clone();

        Ingredient ingredient = recipe.getIngredient(toReturn);
        if (ingredient == null) return;

        ItemStack returns = ingredient.useEditor(returnItem, event.getInventory().getMatrix());
        int slotIndex = slot + 1;

        if (returns.isSimilar(returnItem)) {
            returnItem.setAmount(amount + returnItem.getAmount());
            event.getInventory().setItem(slotIndex, returnItem);
        } else {
            Bukkit.getScheduler().runTaskLater(MetallicsPro.getPlugin(), () -> {
                returns.setAmount(returnItem.getAmount());
                event.getInventory().setItem(slotIndex, returns);
            }, 1L);
        }
    }

    private int toCraft(CraftItemEvent event, CraftBuilder recipe) {
        return event.getClick().isShiftClick() ? maxCraft(event.getInventory().getMatrix(), recipe) : 1;
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

    public static void init() {
        JavaPlugin plugin = MetallicsPro.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(new CraftListener(), plugin);
    }
}
