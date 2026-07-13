package org.brent.metallicspro.blocks.types;

import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.blocks.CustomBlock;
import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.items.ItemRegistry;
import org.brent.metallicspro.materials.FormType;
import org.brent.metallicspro.materials.MaterialReference;
import org.brent.metallicspro.materials.UtilityType;
import org.brent.metallicspro.recpies.Ingredient;
import org.brent.metallicspro.recpies.types.CraftBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class OreBlock extends CustomBlock {

    private static final CustomItem DEFAULT_POWDER = new CustomItem("Small Metal Powder Pile", Material.SUGAR).setModelPath("iron/small_iron_powder_pile");
    private static boolean ADDED_DEFAULT_METAL = false;

    private final CustomItem crushed;
    private final HashMap<String, Integer> possibleMetals = new HashMap<>();

    public OreBlock(String name, Material toReplace, Material dropMaterial) {
        super(name, toReplace);

        considerSilkTouch(false);
        considerFortune(true);

        ItemRegistry itemRegistry = MetallicsPro.getItemRegistry();

        CustomItem drop = new CustomItem(name + " Chunk", dropMaterial).setModelPath("");

        if (!ADDED_DEFAULT_METAL) {
            itemRegistry.add(DEFAULT_POWDER);
            ADDED_DEFAULT_METAL = true;
        }

        this.crushed = new CustomItem("Crushed " + name, dropMaterial)
                .addRecipeBuilder(new CraftBuilder(CraftBuilder.Type.SHAPELESS)
                        .addIngredient(Ingredient.of(new MaterialReference("stone", UtilityType.MORTAR_AND_PESTLE)).setWillKeep(true))
                        .addIngredient(Ingredient.of(drop.getItemStack()))
                );

        setDrop(new CustomBlock.Drop(drop).setMinDropAmount(1).setMaxDropAmount(2));
    }

    public OreBlock addMetal(String id, int weight) {
        this.possibleMetals.put(id, weight);
        return this;
    }

    @Override
    public void resolve() {
        super.resolve();

        ItemRegistry itemRegistry = MetallicsPro.getItemRegistry();

        CraftBuilder craft = new CraftBuilder(CraftBuilder.Type.SHAPELESS, DEFAULT_POWDER.getItemStack(), getRawName())
                .addIngredient(Ingredient.of(itemRegistry.getCustomItem().get("sieve").getItemStack()).setWillKeep(true))
                .addIngredient(Ingredient.of(this.crushed.getItemStack()));

        for (String string : possibleMetals.keySet()) {
            ItemStack metal = new MaterialReference(string, FormType.SMALL_POWDER).resolve().getItemStack();
            craft.addRandomResult(metal, possibleMetals.get(string));
        }

        this.crushed.addRecipeBuilder(craft);

        crushed.resolve();
        MetallicsPro.getItemRegistry().add(crushed);
    }
}
