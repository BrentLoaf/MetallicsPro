package org.brent.metallicspro.materials.types;

import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.materials.*;
import org.brent.metallicspro.recpies.Ingredient;
import org.brent.metallicspro.recpies.types.CraftBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.RecipeChoice;

public class PowderableMaterial extends CustomMaterial {

    public PowderableMaterial(String name, MaterialProperties properties, Material... solids) {
        super(name, properties);

        setFormTypes(
                FormType.POWDER,
                FormType.SMALL_POWDER
        );

        CustomItem powder = getItem(FormType.POWDER);
        CustomItem smallPowder = getItem(FormType.SMALL_POWDER);

        setRecipesPerForm(FormType.SMALL_POWDER,
                new CraftBuilder(CraftBuilder.Type.SHAPELESS)
                        .addIngredient(Ingredient.of(powder.getItemStack()))
                        .setAmount(9)
        );

        setRecipesPerForm(FormType.POWDER,
                new CraftBuilder(CraftBuilder.Type.SHAPED)
                        .setKeyAdd("_from_small")
                        .setShape(
                                "SSS",
                                "SSS",
                                "SSS"
                        )
                        .addIngredient('S', Ingredient.of(smallPowder.getItemStack())),
                new CraftBuilder(CraftBuilder.Type.SHAPELESS)
                        .setKeyAdd("_from_original")
                        .addIngredient(Ingredient.of(new MaterialReference("stone", UtilityType.MORTAR_AND_PESTLE)).setWillKeep(true))
                        .addIngredient(Ingredient.of(new RecipeChoice.MaterialChoice(solids)))
        );
    }
}
