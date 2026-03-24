package org.brent.metallicspro.recpies;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class Ingredient {

    private ItemStack ingredientItem = null;
    private Material ingredientMaterial = null;

    private int count = 1;
    private boolean keep = false;

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
}
