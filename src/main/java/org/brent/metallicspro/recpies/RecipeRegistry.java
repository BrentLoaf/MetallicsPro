package org.brent.metallicspro.recpies;

import org.brent.metallicspro.recpies.types.RecipeBuilder;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class RecipeRegistry {

    private final HashMap<NamespacedKey, RecipeBuilder<?, ?>> recipes = new HashMap<>();

    public void add(@Nullable RecipeBuilder<?, ?> builder) {
        if (builder == null) return;
        builder.registerRecipe();
        recipes.put(builder.getKey(), builder);
    }

    public @Nullable RecipeBuilder<?, ?> getFromKey(NamespacedKey key) {
        return recipes.get(key);
    }
}
