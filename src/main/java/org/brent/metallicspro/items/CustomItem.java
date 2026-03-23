package org.brent.metallicspro.items;

import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.recpies.RecipeBuilder;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class CustomItem {

    protected final String name;
    protected final Material baseMaterial;
    protected final Rarity rarity;
    protected final List<RecipeBuilder> recipeBuilders = new ArrayList<>();

    public CustomItem(String name, Material baseMaterial, Rarity rarity) {
        this.name = name;
        this.baseMaterial = baseMaterial;
        this.rarity = rarity;
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(baseMaterial);

        itemStack.editMeta(meta -> {
            meta.setDisplayName(ChatColor.RESET + "" + rarity.getColor() + "" + name);
        });

        return itemStack;
    }

    public void init(ItemRegistry registry) {
        for (RecipeBuilder recipe : recipeBuilders) MetallicsPro.getRecipeRegistry().add(recipe);
    }

    public String getName() {
        return name;
    }

    public String getRawName() {
        return name.toLowerCase().replace(' ', '_');
    }

    public Material getBaseMaterial() {
        return baseMaterial;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public List<RecipeBuilder> getRecipeBuilders() {
        return recipeBuilders;
    }

    public void addRecipeBuilder(RecipeBuilder recipeBuilder) {
        this.recipeBuilders.add(recipeBuilder);
    }
}
