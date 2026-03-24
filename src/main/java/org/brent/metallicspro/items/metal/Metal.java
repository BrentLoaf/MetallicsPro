package org.brent.metallicspro.items.metal;

import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.items.utility.MortarAndPestle;
import org.brent.metallicspro.recpies.Ingredient;
import org.brent.metallicspro.recpies.types.CraftBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemRarity;

public abstract class Metal {

    protected final Nugget nugget;
    protected final Ingot ingot;
    protected final Powder powder;
    protected final SmallPowder smallPowder;

    public Metal(String name, ItemRarity rarity) {
        this.nugget = new Nugget(name, rarity);
        this.ingot = new Ingot(name, rarity);
        this.powder = new Powder(name, rarity);
        this.smallPowder = new SmallPowder(name, rarity);

        ingot.addRecipeBuilder(
                new CraftBuilder(CraftBuilder.Type.SHAPED, ingot.getItemStack(), ingot.getRawName())
                        .setShape(
                                "NNN",
                                "NNN",
                                "NNN"
                        )
                        .addIngredient('N', Ingredient.of(nugget.getItemStack()))
        );

        nugget.addRecipeBuilder(
                new CraftBuilder(CraftBuilder.Type.SHAPELESS, nugget.getItemStack(), nugget.getRawName())
                        .addIngredient(Ingredient.of(ingot.getItemStack()))
        );

        powder.addRecipeBuilder(
                new CraftBuilder(CraftBuilder.Type.SHAPELESS, powder.getItemStack(), powder.getRawName() + "_via_ingot_mortar")
                        .addIngredient(Ingredient.of(ingot.getItemStack()))
                        .addIngredient(Ingredient.of(new MortarAndPestle().getItemStack()).setWillKeep(true))
        );

        powder.addRecipeBuilder(
                new CraftBuilder(CraftBuilder.Type.SHAPED, powder.getItemStack(), powder.getRawName() + "_via_small_pile")
                        .setShape(
                                "SSS",
                                "SSS",
                                "SSS"
                        )
                        .addIngredient('S', Ingredient.of(smallPowder.getItemStack()))
        );

        smallPowder.addRecipeBuilder(
                new CraftBuilder(CraftBuilder.Type.SHAPELESS, smallPowder.getItemStack(), smallPowder.getRawName() + "_via_powder")
                        .addIngredient(Ingredient.of(powder.getItemStack()))
        );

        smallPowder.addRecipeBuilder(
                new CraftBuilder(CraftBuilder.Type.SHAPELESS, smallPowder.getItemStack(), smallPowder.getRawName() + "_via_nugget_mortar")
                        .addIngredient(Ingredient.of(nugget.getItemStack()))
                        .addIngredient(Ingredient.of(new MortarAndPestle().getItemStack()).setWillKeep(true))
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

        public Nugget(String name, ItemRarity rarity) {
            super(name + " Nugget", Material.IRON_NUGGET, rarity);
        }
    }

    public class Ingot extends CustomItem {

        public Ingot(String name, ItemRarity rarity) {
            super(name + " Ingot", Material.IRON_INGOT, rarity);
        }
    }

    public class Powder extends CustomItem {

        public Powder(String name, ItemRarity rarity) {
            super(name + " Powder Pile", Material.SUGAR, rarity);
        }
    }

    public class SmallPowder extends CustomItem {

        public SmallPowder(String name, ItemRarity rarity) {
            super("Small " + name + " Powder Pile", Material.SUGAR, rarity);
        }
    }
}
