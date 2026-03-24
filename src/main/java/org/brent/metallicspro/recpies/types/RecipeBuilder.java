package org.brent.metallicspro.recpies.types;

import org.brent.metallicspro.MetallicsPro;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public abstract class RecipeBuilder<T extends RecipeBuilder, R> {

    protected String keyAdd;

    protected final ItemStack result;
    protected final String key;

    protected Map<ItemStack, Integer> randomResult = new HashMap<>();

    public RecipeBuilder(ItemStack result, String key) {
        this.result = result;
        this.key = key;
    }

    public NamespacedKey getKey() {
        return new NamespacedKey(MetallicsPro.getPlugin(), key + keyAdd);
    }

    public ItemStack getResult() {
        return result;
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

    /*private final Type type;
    private final List<String> shape = new ArrayList<>();
    private final List<Ingredient> shapelessIngredients = new ArrayList<>();
    private final HashMap<Character, Ingredient> shapedIngredients = new HashMap<>();

    private final HashMap<CustomItem, Integer> possibleResults = new HashMap<>();

    public RecipeBuilder(Type type, Result result) {
        this.result = result;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public List<String> getShape() {
        return shape;
    }

    public List<Ingredient> getShapelessIngredients() {
        return shapelessIngredients;
    }

    public HashMap<Character, Ingredient> getShapedIngredients() {
        return shapedIngredients;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public HashMap<CustomItem, Integer> getPossibleResults() {
        return possibleResults;
    }

    public RecipeBuilder addPossibleResult(CustomItem item, int weight) {
        this.possibleResults.put(item, weight);
        return this;
    }

    public RecipeBuilder setShape(String s1, String s2, String s3) {
        this.shape.add(s1);
        this.shape.add(s2);
        this.shape.add(s3);
        return this;
    }

    public RecipeBuilder addIngredient(Ingredient ingredient) {
        this.shapelessIngredients.add(ingredient);
        return this;
    }

    public RecipeBuilder addIngredient(char character, Ingredient ingredient) {
        this.shapedIngredients.put(character, ingredient);
        return this;
    }

    public @Nullable CustomItem getRandomResult() {
        if (possibleResults.isEmpty()) return null;

        int totalWeight = 0;

        for (int weight : possibleResults.values()) {
            totalWeight += weight;
        }

        int roll = new Random().nextInt(totalWeight);

        int current = 0;
        for (Map.Entry<CustomItem, Integer> entry : possibleResults.entrySet()) {
            current += entry.getValue();

            if (roll < current) {
                return entry.getKey();
            }
        }

        return null;
    }

    public Recipe build() {
        key = new NamespacedKey(MetallicsPro.getPlugin(), result.key() + UUID.randomUUID());

        return type.getRecipe(this, result.getResult(), key);
    }

    public boolean shouldReturn(ItemStack itemStack) {
        List<Ingredient> ingredients = type == Type.SHAPELESS_CRAFTING ?
                shapelessIngredients :
                shapedIngredients.values().stream().toList();

        for (Ingredient ingredient : ingredients) {
            if (ingredient.willConsume()) continue;

            ItemStack itemToCheck = ingredient.getMaterial() != null ?
                    new ItemStack(ingredient.getMaterial()) :
                    ingredient.getItemStack();

            if (itemStack.isSimilar(itemToCheck)) return true;
        }

        return false;
    }

    public enum Type {
        SHAPELESS_CRAFTING {
            @Override
            public Recipe getRecipe(RecipeBuilder recipeBuilder, ItemStack item, NamespacedKey key) {
                ShapelessRecipe recipe = new ShapelessRecipe(key, item);

                for (Ingredient ingredient : recipeBuilder.shapelessIngredients) {
                    Material material = ingredient.getMaterial();
                    int amount = ingredient.getAmount();

                    if (material == null) {
                        ItemStack itemStack = ingredient.getItemStack();
                        recipe.addIngredient(amount, itemStack);
                    } else {
                        recipe.addIngredient(amount, material);
                    }
                }

                return recipe;
            }
        },
        SHAPED_CRAFTING {
            @Override
            public Recipe getRecipe(RecipeBuilder recipeBuilder, ItemStack item, NamespacedKey key) {
                ShapedRecipe recipe = new ShapedRecipe(key, item);

                recipe.shape(recipeBuilder.getShape().get(0), recipeBuilder.getShape().get(1), recipeBuilder.getShape().get(2));

                for (Character character : recipeBuilder.getShapedIngredients().keySet()) {
                    Ingredient ingredient = recipeBuilder.getShapedIngredients().get(character);

                    Material material = ingredient.getMaterial();
                    if (material == null) {
                        ItemStack itemStack = ingredient.getItemStack();
                        recipe.setIngredient(character, itemStack);
                    } else {
                        recipe.setIngredient(character, material);
                    }
                }

                return recipe;
            }
        };

        public abstract Recipe getRecipe(RecipeBuilder recipeBuilder, ItemStack item, NamespacedKey key);
    }*/
}