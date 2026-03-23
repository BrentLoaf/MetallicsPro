package org.brent.metallicspro.items.metal;

import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.items.Rarity;
import org.brent.metallicspro.items.utility.MortarAndPestle;
import org.brent.metallicspro.recpies.Ingredient;
import org.brent.metallicspro.recpies.RecipeBuilder;
import org.brent.metallicspro.recpies.Result;
import org.bukkit.Material;

public abstract class Metal {

    protected final Nugget nugget;
    protected final Ingot ingot;
    protected final Powder powder;
    protected final SmallPowder smallPowder;

    public Metal(String name, Rarity rarity) {
        this.nugget = new Nugget(name, rarity);
        this.ingot = new Ingot(name, rarity);
        this.powder = new Powder(name, rarity);
        this.smallPowder = new SmallPowder(name, rarity);

        ingot.addRecipeBuilder(
                new RecipeBuilder(RecipeBuilder.Type.SHAPED_CRAFTING, new Result(ingot.getItemStack(), 1, ingot.getRawName()))
                        .setShape(
                                "NNN",
                                "NNN",
                                "NNN"
                        )
                        .addIngredient('N', new Ingredient(nugget.getItemStack()))
        );

        nugget.addRecipeBuilder(
                new RecipeBuilder(RecipeBuilder.Type.SHAPELESS_CRAFTING, new Result(nugget.getItemStack(), 9, nugget.getRawName()))
                        .addIngredient(new Ingredient(ingot.getItemStack()))
        );

        powder.addRecipeBuilder(
                new RecipeBuilder(RecipeBuilder.Type.SHAPELESS_CRAFTING, new Result(powder.getItemStack(), 1, powder.getRawName()))
                        .addIngredient(new Ingredient(ingot.getItemStack()))
                        .addIngredient(new Ingredient(new MortarAndPestle().getItemStack(), 1, false))
        );
        powder.addRecipeBuilder(
                new RecipeBuilder(RecipeBuilder.Type.SHAPED_CRAFTING, new Result(powder.getItemStack(), 1, powder.getRawName()))
                        .setShape(
                                "SSS",
                                "SSS",
                                "SSS"
                        )
                        .addIngredient('S', new Ingredient(smallPowder.getItemStack()))
        );

        smallPowder.addRecipeBuilder(
                new RecipeBuilder(RecipeBuilder.Type.SHAPELESS_CRAFTING, new Result(smallPowder.getItemStack(), 9, smallPowder.getRawName()))
                        .addIngredient(new Ingredient(powder.getItemStack()))
        );
        smallPowder.addRecipeBuilder(
                new RecipeBuilder(RecipeBuilder.Type.SHAPELESS_CRAFTING, new Result(smallPowder.getItemStack(), 1, smallPowder.getRawName()))
                        .addIngredient(new Ingredient(nugget.getItemStack()))
                        .addIngredient(new Ingredient(new MortarAndPestle().getItemStack(), 1, false))
        );
    }

    public Nugget getNugget() {
        return nugget;
    }

    public Ingot getIngot() {
        return ingot;
    }

    public Powder getPowder() {
        return powder;
    }

    public SmallPowder getSmallPowder() {
        return smallPowder;
    }

    public class Nugget extends CustomItem {

        public Nugget(String name, Rarity rarity) {
            super(name + " Nugget", Material.IRON_NUGGET, rarity);
        }
    }

    public class Ingot extends CustomItem {

        public Ingot(String name, Rarity rarity) {
            super(name + " Ingot", Material.IRON_INGOT, rarity);
        }
    }

    public class Powder extends CustomItem {

        public Powder(String name, Rarity rarity) {
            super(name + " Powder Pile", Material.SUGAR, rarity);
        }
    }

    public class SmallPowder extends CustomItem {

        public SmallPowder(String name, Rarity rarity) {
            super("Small " + name + " Powder Pile", Material.SUGAR, rarity);
        }
    }
}
