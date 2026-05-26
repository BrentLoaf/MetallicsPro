package org.brent.metallicspro.items.ore;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.items.metal.Metal;
import org.brent.metallicspro.items.utility.MortarAndPestle;
import org.brent.metallicspro.items.utility.Sieve;
import org.brent.metallicspro.recpies.Ingredient;
import org.brent.metallicspro.recpies.types.CraftBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;

public abstract class OreItem {

    protected final RawOre rawOre;
    protected final CrushedOre crushedOre;
    protected final BlockOfOre blockOfOre;

    protected CraftBuilder usageRecipe;

    public OreItem(String name, Material baseMaterial, Material blockOre, ItemRarity rarity, String key) {
        this.rawOre = new RawOre(name, baseMaterial, rarity);
        this.crushedOre = new CrushedOre(name, rarity);
        this.blockOfOre = new BlockOfOre(name, blockOre, rarity);

        rawOre.addRecipeBuilder(
                new CraftBuilder(CraftBuilder.Type.SHAPELESS, rawOre.getItemStack(), rawOre.getRawName())
                        .setAmount(9)
                        .addIngredient(Ingredient.of(blockOfOre.getItemStack())));

        crushedOre.addRecipeBuilder(
                new CraftBuilder(CraftBuilder.Type.SHAPELESS, crushedOre.getItemStack(), crushedOre.getRawName())
                        .addIngredient(Ingredient.of(rawOre.getItemStack()))
                        .addIngredient(Ingredient.of(new MortarAndPestle().getItemStack()).setWillKeep(true))
        );

        usageRecipe = new CraftBuilder(CraftBuilder.Type.SHAPELESS, getDefaultItem(), key + "_process")
                .addIngredient(Ingredient.of(new Sieve().getItemStack()).setWillKeep(true))
                .addIngredient(Ingredient.of(crushedOre.getItemStack()));

        blockOfOre.addRecipeBuilder(
                new CraftBuilder(CraftBuilder.Type.SHAPED, blockOfOre.getItemStack(), blockOfOre.getRawName())
                        .setShape(
                                "ooo",
                                "ooo",
                                "ooo"
                        )
                        .addIngredient('o', Ingredient.of(rawOre.getItemStack())));
    }

    public void addMetal(Metal metal, int weight) {
        usageRecipe.addRandomResult(metal.getSmallPowder().getItemStack(), weight);
    }

    public void init() {
        Bukkit.getLogger().info("[MetalicsPro] Init started for " + usageRecipe.getKey().toString());
        MetallicsPro.getRecipeRegistry().add(usageRecipe);
    }

    public RawOre getRawOre() {
        return rawOre;
    }

    public CrushedOre getCrushedOre() {
        return crushedOre;
    }

    public BlockOfOre getRawOreBlock() {
        return blockOfOre;
    }

    public CraftBuilder getUsageRecipe() {
        return usageRecipe;
    }

    public ItemStack getDefaultItem() {
        ItemStack itemStack = new ItemStack(Material.SUGAR);

        itemStack.editMeta(meta -> {
            meta.displayName(Component.text("Small Metal Powder Pile")
                    .decoration(TextDecoration.ITALIC, false));
            meta.setRarity(ItemRarity.COMMON);

            NamespacedKey key = new NamespacedKey("metallicspro", "metal/small_iron_powder_pile");
            meta.setItemModel(key);
        });

        return itemStack;
    }

    public class RawOre extends CustomItem {

        public RawOre(String name, Material baseMaterial, ItemRarity rarity) {
            super(name + " Ore", baseMaterial, rarity);
        }
    }

    public class CrushedOre extends CustomItem {

        public CrushedOre(String name, ItemRarity rarity) {
            super(name + " Crushed Ore", Material.SUGAR, rarity);
        }
    }

    public class BlockOfOre extends CustomItem {

        public BlockOfOre(String name, Material material, ItemRarity rarity) {
            super(name + " Ore Block", material, rarity);

            modelPath = "";
        }
    }
}
