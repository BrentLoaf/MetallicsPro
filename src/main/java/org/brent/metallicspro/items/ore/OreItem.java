package org.brent.metallicspro.items.ore;

import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.items.Rarity;
import org.brent.metallicspro.items.utility.MortarAndPestle;
import org.brent.metallicspro.recpies.Ingredient;
import org.brent.metallicspro.recpies.RecipeBuilder;
import org.brent.metallicspro.recpies.Result;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class OreItem {

    protected final RawOre rawOre;
    protected final CrushedOre crushedOre;

    protected RecipeBuilder usageRecipe;

    public OreItem(String name, Material baseMaterial, Rarity rarity) {
        this.rawOre = new RawOre(name, baseMaterial, rarity);
        this.crushedOre = new CrushedOre(name, rarity);

        crushedOre.addRecipeBuilder(
                new RecipeBuilder(RecipeBuilder.Type.SHAPELESS_CRAFTING, new Result(crushedOre.getItemStack(), 1, crushedOre.getRawName()))
                        .addIngredient(new Ingredient(rawOre.getItemStack()))
                        .addIngredient(new Ingredient(new MortarAndPestle().getItemStack(), 1, false))
        );
    }

    public void init() {
        MetallicsPro.getRecipeRegistry().add(usageRecipe);
    }

    public RawOre getRawOre() {
        return rawOre;
    }

    public CrushedOre getCrushedOre() {
        return crushedOre;
    }

    public RecipeBuilder getUsageRecipe() {
        return usageRecipe;
    }

    public static ItemStack getDefaultItem() {
        ItemStack item = new ItemStack(Material.SUGAR);

        item.editMeta(meta -> {
            meta.setDisplayName(ChatColor.RESET + "" + ChatColor.WHITE + "Small Metal Powder Pile");
        });

        return item;
    }

    public class RawOre extends CustomItem {

        public RawOre(String name, Material baseMaterial, Rarity rarity) {
            super(name + " Ore", baseMaterial, rarity);
        }
    }

    public class CrushedOre extends CustomItem {

        public CrushedOre(String name, Rarity rarity) {
            super(name + " Crushed Ore", Material.SUGAR, rarity);
        }
    }
}
