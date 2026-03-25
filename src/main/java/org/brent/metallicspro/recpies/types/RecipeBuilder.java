package org.brent.metallicspro.recpies.types;

import org.brent.metallicspro.MetallicsPro;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.BiFunction;

public abstract class RecipeBuilder<T extends RecipeBuilder, R> {

    protected final String key;
    protected String keyAdd;

    protected final ItemStack result;
    protected int amount = 1;

    protected BiFunction<ItemStack, ItemStack[], ItemStack> resultEditor = (r, i) -> r;

    protected Map<ItemStack, Integer> randomResult = new HashMap<>();

    public RecipeBuilder(ItemStack result, String key) {
        this.result = result;
        this.key = key;
    }

    public NamespacedKey getKey() {
        return new NamespacedKey(MetallicsPro.getPlugin(), key + keyAdd);
    }

    public NamespacedKey getKey(String append) {
        return new NamespacedKey(MetallicsPro.getPlugin(), key + keyAdd + append);
    }

    public ItemStack getResult() {
        result.setAmount(amount);
        return result;
    }

    public ItemStack useResultEditor(ItemStack result, ItemStack[] items) {
        return resultEditor.apply(result, items);
    }

    public T setResultEditor(BiFunction<ItemStack, ItemStack[], ItemStack> resultEditor) {
        this.resultEditor = resultEditor;
        return (T) this;
    }

    public T setAmount(int amount) {
        this.amount = amount;
        return (T) this;
    }

    public abstract @Nullable R getRecipe();

    public void registerRecipe() {
        R r = getRecipe();

        if (!(r instanceof Recipe)) {
            Bukkit.getLogger().warning("[MetallicsPro] Invalid recipe type in class: " + this.getClass().getName());
            return;
        }

        Recipe recipe = (Recipe) r;
        if (recipe == null) return;
        Bukkit.addRecipe(recipe);
    }

    public abstract boolean shouldReturn(ItemStack itemStack);

    public T setRandomResults(Map<ItemStack, Integer> randomResult) {
        this.randomResult = randomResult;
        return (T) this;
    }

    public T addRandomResult(ItemStack itemStack, int weight) {
        this.randomResult.put(itemStack, weight);
        return (T) this;
    }

    public @Nullable ItemStack getRandomResult() {
        if (randomResult.isEmpty()) return null;

        int totalWeight = 0;

        for (int weight : randomResult.values()) {
            totalWeight += weight;
        }

        int roll = new Random().nextInt(totalWeight);

        int current = 0;
        for (Map.Entry<ItemStack, Integer> entry : randomResult.entrySet()) {
            current += entry.getValue();

            if (roll < current) {
                return entry.getKey();
            }
        }

        return null;
    }
}