package org.brent.metallicspro.items.utility;

import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.recpies.Ingredient;
import org.brent.metallicspro.recpies.types.CraftBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemRarity;

public class Sieve extends CustomItem {

    public Sieve() {
        super("Sieve", Material.PAPER);

        addRecipeBuilder(
                new CraftBuilder(CraftBuilder.Type.SHAPELESS, getItemStack(), getRawName())
                        .addIngredient(Ingredient.of(Material.STRING).setCount(2))
                        .addIngredient(Ingredient.of(Material.STICK).setCount(2))
        );
    }
}
