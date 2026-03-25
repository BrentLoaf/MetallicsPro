package org.brent.metallicspro.recpies;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public class Ingredient {

    private ItemStack ingredientItem = null;
    private Material ingredientMaterial = null;

    private int count = 1;
    private boolean keep = false;

    private BiFunction<ItemStack, ItemStack[], ItemStack> ingredientEdit = (i, m) -> i;

    private Ingredient(ItemStack ingredientItem) {
        this.ingredientItem = ingredientItem;
    }

    private Ingredient(Material ingredientMaterial) {
        this.ingredientMaterial = ingredientMaterial;
    }

    public static Ingredient of(ItemStack ingredientItem) {
        return new Ingredient(ingredientItem);
    }

    public static Ingredient of(Material ingredientMaterial) {
        return new Ingredient(ingredientMaterial);
    }

    public @Nullable ItemStack getItem() {
        return ingredientItem;
    }

    public @Nullable Material getMaterial() {
        return ingredientMaterial;
    }

    public Ingredient setCount(int count) {
        this.count = count;
        return this;
    }

    public int getCount() {
        return count;
    }

    public Ingredient setWillKeep(boolean keep) {
        this.keep = keep;
        return this;
    }

    public boolean willKeep() {
        return keep;
    }

    public @Nullable RecipeChoice getChoice() {
        if (ingredientItem != null) {
            return new RecipeChoice.ExactChoice(ingredientItem);
        } else if (ingredientMaterial != null) {
            return new RecipeChoice.MaterialChoice(ingredientMaterial);
        }
        return null;
    }

    public Ingredient setEditor(BiFunction<ItemStack, ItemStack[], ItemStack> ingredientEdit) {
        this.ingredientEdit = ingredientEdit;
        return this;
    }

    public ItemStack useEditor(ItemStack item, ItemStack[] matrix) {
        return ingredientEdit.apply(item, matrix);
    }
}
