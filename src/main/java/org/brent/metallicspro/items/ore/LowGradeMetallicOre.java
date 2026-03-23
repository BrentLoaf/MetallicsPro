package org.brent.metallicspro.items.ore;

import org.brent.metallicspro.items.Rarity;
import org.brent.metallicspro.items.metal.Copper;
import org.brent.metallicspro.items.metal.Iron;
import org.brent.metallicspro.items.metal.Zinc;
import org.brent.metallicspro.items.utility.Sieve;
import org.brent.metallicspro.recpies.Ingredient;
import org.brent.metallicspro.recpies.RecipeBuilder;
import org.brent.metallicspro.recpies.Result;
import org.bukkit.Material;

public class LowGradeMetallicOre extends OreItem {

    public LowGradeMetallicOre() {
        super("Low-Grade Metallic", Material.RAW_COPPER, Rarity.COMMON);

        usageRecipe = new RecipeBuilder(RecipeBuilder.Type.SHAPELESS_CRAFTING, new Result(getDefaultItem(), 1, "default_low-grade_powder"))
                .addIngredient(new Ingredient(new Sieve().getItemStack(), 1, false))
                .addIngredient(new Ingredient(getCrushedOre().getItemStack()))
                .addPossibleResult(new Copper().getSmallPowder(), 13)
                .addPossibleResult(new Zinc().getSmallPowder(), 6)
                .addPossibleResult(new Iron().getSmallPowder(), 1);
    }
}
