package org.brent.metallicspro.materials.types;

import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.materials.FormType;
import org.brent.metallicspro.materials.MaterialProperties;
import org.brent.metallicspro.materials.MaterialReference;
import org.brent.metallicspro.recpies.Ingredient;
import org.brent.metallicspro.recpies.types.CraftBuilder;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;

public class AlloyMaterial extends MetalMaterial {

    protected final MaterialReference[] items;

    protected int amount;

    public AlloyMaterial(String name, MaterialProperties properties, MaterialReference... items) {
        super(name, properties);
        this.items = items;
    }

    public AlloyMaterial setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public void resolve() {
        super.resolve();

        CustomItem powder = getItem(FormType.POWDER);

        CraftBuilder craft = new CraftBuilder(CraftBuilder.Type.SHAPELESS, powder.getRawName() + "_via_combine")
                .setAmount(amount);

        for (MaterialReference item : items) craft.addIngredient(Ingredient.of(item.resolve().getItemStack()));

        powder.addRecipeBuilder(craft);
    }
}
