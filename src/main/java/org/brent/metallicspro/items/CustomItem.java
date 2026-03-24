package org.brent.metallicspro.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.recpies.types.RecipeBuilder;
import org.bukkit.*;
import org.bukkit.inventory.*;

import java.util.*;

public abstract class CustomItem {

    protected final String name;
    protected final Material baseMaterial;
    protected final ItemRarity rarity;
    protected final List<RecipeBuilder<?, ?>> recipeBuilders = new ArrayList<>();

    public CustomItem(String name, Material baseMaterial, ItemRarity rarity) {
        this.name = name;
        this.baseMaterial = baseMaterial;
        this.rarity = rarity;
    }

    public static final HashSet<NamespacedKey> keys = new HashSet<>(); // JUST FOR DEBUGS

    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(baseMaterial);

        itemStack.editMeta(meta -> {
            meta.displayName(Component.text(name)
                    .decoration(TextDecoration.ITALIC, false));
            meta.setRarity(rarity);

            String pkg = this.getClass().getPackageName();
            String type = pkg.substring(pkg.indexOf("items") + "items".length() + 1)
                    .replace('.', '/');

            NamespacedKey key = new NamespacedKey("metallicspro", type + "/" + getRawName());
            meta.setItemModel(key);
            keys.add(key);
        });

        return itemStack;
    }

    public void init(ItemRegistry registry) {
        for (RecipeBuilder<?, ?> recipe : recipeBuilders) MetallicsPro.getRecipeRegistry().add(recipe);
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

    public ItemRarity getRarity() {
        return rarity;
    }

    public List<RecipeBuilder<?, ?>> getRecipeBuilders() {
        return recipeBuilders;
    }

    public void addRecipeBuilder(RecipeBuilder<?, ?> recipeBuilder) {
        this.recipeBuilders.add(recipeBuilder);
    }
}
