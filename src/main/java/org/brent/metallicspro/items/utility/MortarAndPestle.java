package org.brent.metallicspro.items.utility;

import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.items.Rarity;
import org.brent.metallicspro.recpies.Ingredient;
import org.brent.metallicspro.recpies.RecipeBuilder;
import org.brent.metallicspro.recpies.Result;
import org.bukkit.Material;

public class MortarAndPestle extends CustomItem {

    public MortarAndPestle() {
        super("Mortar and Pestle", Material.FLOWER_POT, Rarity.COMMON);

        addRecipeBuilder(
                new RecipeBuilder(RecipeBuilder.Type.SHAPELESS_CRAFTING, new Result(getItemStack(), 1, getRawName()))
                        .addIngredient(new Ingredient(Material.BRICK, 3))
        );
    }
}
