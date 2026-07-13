package org.brent.metallicspro.recpies;

import org.brent.metallicspro.materials.MaterialReference;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public class Ingredient {

    private RecipeChoice choice = null;

    private ItemStack ingredientItem = null;
    private Material ingredientMaterial = null;
    private MaterialReference ingredientReference = null;

    private int count = 1;
    private boolean keep = false;

    private BiFunction<ItemStack, ItemStack[], ItemStack> ingredientEdit = (i, m) -> i;

    private Ingredient(ItemStack ingredientItem) {
        this.ingredientItem = ingredientItem;
    }

    private Ingredient(Material ingredientMaterial) {
        this.ingredientMaterial = ingredientMaterial;
    }

    private Ingredient(MaterialReference ingredientReference) {
        this.ingredientReference = ingredientReference;
    }

    private Ingredient(RecipeChoice choice) {
        this.choice = choice;
    }

    public static Ingredient of(ItemStack ingredientItem) {
        return new Ingredient(ingredientItem);
    }

    public static Ingredient of(Material ingredientMaterial) {
        return new Ingredient(ingredientMaterial);
    }

    public static Ingredient of(MaterialReference reference) {
        return new Ingredient(reference);
    }

    public static Ingredient of(RecipeChoice choice) {
        return new Ingredient(choice);
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

    @Nullable
    public RecipeChoice getChoice() {
        if (choice != null) {
            return choice;
        } else if (ingredientItem != null) {
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

    public void resolve() {
        if (ingredientReference == null) return;
        this.ingredientItem = ingredientReference.resolve().getItemStack();
    }
}
