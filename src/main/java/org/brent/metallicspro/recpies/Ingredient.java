package org.brent.metallicspro.recpies;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class Ingredient {

    private final Material material;
    private final ItemStack itemStack;
    private int amount = 1;
    private boolean consume = true;

    public Ingredient(Material material) {
        this.material = material;
        this.itemStack = null;
    }

    public Ingredient(ItemStack itemStack) {
        this.material = null;
        this.itemStack = itemStack;
    }

    public Ingredient(Material material, int amount) {
        this.material = material;
        this.itemStack = null;
        this.amount = amount;
    }

    public Ingredient(ItemStack itemStack, int amount) {
        this.material = null;
        this.itemStack = itemStack;
        this.amount = amount;
    }

    public Ingredient(Material material, int amount, boolean consume) {
        this.material = material;
        this.itemStack = null;
        this.amount = amount;
        this.consume = consume;
    }

    public Ingredient(ItemStack itemStack, int amount, boolean consume) {
        this.material = null;
        this.itemStack = itemStack;
        this.amount = amount;
        this.consume = consume;
    }

    public @Nullable Material getMaterial() {
        return material;
    }

    public @Nullable ItemStack getItemStack() {
        return itemStack;
    }

    public int getAmount() {
        return amount;
    }

    public boolean willConsume() {
        return consume;
    }
}
