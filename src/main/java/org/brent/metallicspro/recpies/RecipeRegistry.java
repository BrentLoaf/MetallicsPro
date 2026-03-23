package org.brent.metallicspro.recpies;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class RecipeRegistry {

    private final HashMap<NamespacedKey, RecipeBuilder> recipes = new HashMap<>();

    public void add(RecipeBuilder recipeBuilder) {
        Recipe recipe = recipeBuilder.build();
        Bukkit.addRecipe(recipe);
        recipes.put(recipeBuilder.getKey(), recipeBuilder);
    }

    public @Nullable RecipeBuilder getFromKey(NamespacedKey key) {
        return recipes.get(key);
    }
}
