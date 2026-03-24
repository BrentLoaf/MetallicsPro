package org.brent.metallicspro.recpies.types;

import org.brent.metallicspro.recpies.Ingredient;
import org.bukkit.Material;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CraftBuilder extends RecipeBuilder<CraftBuilder, CraftingRecipe> {

    protected final Type type;

    protected String[] shape;

    protected List<Ingredient> shapelessIngredients = new ArrayList<>();
    protected Map<Character, Ingredient> shapedIngredients = new HashMap<>();

    public CraftBuilder(Type type, ItemStack result, String key) {
        super(result, key);
        this.type = type;
        keyAdd = "_crafting";
    }

    public Type getType() {
        return type;
    }

    public String[] getShape() {
        return shape;
    }

    public CraftBuilder setShape(String... shape) {
        this.shape = shape;
        return this;
    }

    public Map<Character, Ingredient> getShapedIngredients() {
        return this.shapedIngredients;
    }

    public CraftBuilder addIngredient(char slot, Ingredient ingredient) {
        this.shapedIngredients.put(slot, ingredient);
        return this;
    }

    public List<Ingredient> getShapelessIngredients() {
        return this.shapelessIngredients;
    }

    public CraftBuilder addIngredient(Ingredient ingredient) {
        this.shapelessIngredients.add(ingredient);
        return this;
    }

    @Override
    public boolean shouldReturn(ItemStack itemStack) {
        List<Ingredient> ingredients = type == Type.SHAPELESS ?
                shapelessIngredients :
                shapedIngredients.values().stream().toList();

        for (Ingredient ingredient : ingredients) {
            if (!ingredient.willKeep()) continue;

            ItemStack itemToCheck = ingredient.getMaterial() != null ?
                    new ItemStack(ingredient.getMaterial()) :
                    ingredient.getItem();

            if (itemStack.isSimilar(itemToCheck)) return true;
        }

        return false;
    }

    @Override
    public @Nullable CraftingRecipe getRecipe() {
        return this.type.getRecipe(this);
    }

    public enum Type {
        SHAPELESS {
            @Override
            public CraftingRecipe getRecipe(CraftBuilder builder) {
                ShapelessRecipe recipe = new ShapelessRecipe(builder.getKey(), builder.getResult());

                List<Ingredient> ingredients = builder.getShapelessIngredients();
                for (Ingredient ingredient : ingredients) {
                    int count = ingredient.getCount();

                    Material material = ingredient.getMaterial();
                    if (material != null) {
                        recipe.addIngredient(count, material);
                    } else {
                        recipe.addIngredient(count, ingredient.getItem());
                    }
                }

                return recipe;
            }
        },
        SHAPED {
            @Override
            public CraftingRecipe getRecipe(CraftBuilder builder) {
                ShapedRecipe recipe = new ShapedRecipe(builder.getKey(), builder.getResult());

                recipe.shape(builder.getShape());

                Map<Character, Ingredient> ingredients = builder.getShapedIngredients();
                for (Character character : ingredients.keySet()) {
                    Ingredient ingredient = ingredients.get(character);

                    Material material = ingredient.getMaterial();
                    if (material != null) {
                        recipe.setIngredient(character, material);
                    } else {
                        recipe.setIngredient(character, ingredient.getItem());
                    }
                }

                return recipe;
            }
        };

        public abstract CraftingRecipe getRecipe(CraftBuilder recipeBuilder);
    }
}
