package org.brent.metallicspro.items.utility;

import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.recpies.Ingredient;
import org.brent.metallicspro.recpies.types.CraftBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemRarity;

public class MortarAndPestle extends CustomItem {

    public MortarAndPestle() {
        super("Mortar and Pestle", Material.FLOWER_POT);

        addRecipeBuilder(
                new CraftBuilder(CraftBuilder.Type.SHAPELESS, getItemStack(), getRawName())
                        .addIngredient(Ingredient.of(Material.BRICK).setCount(3))
        );
    }
}
