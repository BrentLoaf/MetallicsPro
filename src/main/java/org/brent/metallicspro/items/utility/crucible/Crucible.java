package org.brent.metallicspro.items.utility.crucible;

import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.items.CustomItemBuilder;
import org.brent.metallicspro.items.ItemRegistry;
import org.brent.metallicspro.materials.FormType;
import org.brent.metallicspro.materials.types.MetalMaterial;
import org.brent.metallicspro.recpies.Ingredient;
import org.brent.metallicspro.recpies.types.CraftBuilder;
import org.brent.metallicspro.recpies.types.FurnaceBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Crucible implements CustomItemBuilder {

    protected final UnfiredCrucible unfired;
    protected final FiredCrucible fired;

    protected int crucibleMultiplier = 1;

    public Crucible(String unfiredName, String firedName, ItemRarity rarity, Material... craftings) {
        unfired = new UnfiredCrucible(unfiredName, rarity);
        fired = new FiredCrucible(firedName, rarity);

        for (Material material : craftings) {
            unfired.addRecipeBuilder(
                    new CraftBuilder(CraftBuilder.Type.SHAPED, unfired.getItemStack(), unfired.getRawName() + "_" + material.name())
                            .setShape(
                                    "xxx",
                                    "cxc",
                                    "xcx"
                            )
                            .addIngredient('c', Ingredient.of(material))
            );
        }

        fired.addRecipeBuilder(
                new FurnaceBuilder(fired.getItemStack(), Ingredient.of(unfired.getItemStack()), fired.getRawName())
        );
    }

    @Override
    public void init(ItemRegistry registry) {
        registry.add(unfired);
        registry.add(fired);

        for (CustomItem type : fired.getCrucibleTypes()) registry.add(type);
    }

    public class UnfiredCrucible extends CustomItem {

        public UnfiredCrucible(String name, ItemRarity rarity) {
            super("Unfired " + name + " Crucible", Material.BOWL);
        }
    }

    public class FiredCrucible extends CustomItem {

        public FiredCrucible(String name, ItemRarity rarity) {
            super(name + " Crucible", Material.BOWL);

            rawAppend = "empty";
            modelPath = this.name.toLowerCase().replace(' ', '_');

            addLore(ChatColor.RESET + "" + ChatColor.GRAY + "Contents: Empty");
        }

        public List<CustomItem> getCrucibleTypes() {
            List<CustomItem> types = new ArrayList<>();

            for (MetalMaterial metalMaterial : MetallicsPro.getMaterialRegistry().getMetals().values()) {
                CrucibleType type = new CrucibleType(name, metalMaterial, getItemStack());

                types.add(type.getMolten());
                types.add(type.getSolid());
            }

            return types;
        }

        public class CrucibleType {

            private final Molten molten;
            private final Solid solid;

            public CrucibleType(String name, MetalMaterial metalMaterial, ItemStack base) {
                molten = new Molten(name, metalMaterial);
                solid = new Solid(name, metalMaterial);

                double meltingSpeed = metalMaterial.getProperties().getHeatResistance().getMultiplier();

                solid.addRecipeBuilder(
                        new CraftBuilder(CraftBuilder.Type.SHAPELESS, solid.getRawName() + "_ingot")
                                .addIngredient(Ingredient.of(base))
                                .addIngredient(Ingredient.of(metalMaterial.getItem(FormType.INGOT).getItemStack()))
                );
                solid.addRecipeBuilder(
                        new CraftBuilder(CraftBuilder.Type.SHAPELESS, solid.getRawName() + "_powder")
                                .addIngredient(Ingredient.of(base))
                                .addIngredient(Ingredient.of(metalMaterial.getItem(FormType.POWDER).getItemStack()))
                );

                molten.addRecipeBuilder(
                        new FurnaceBuilder(molten.getItemStack(), Ingredient.of(solid.getItemStack()), molten.getRawName() + "_melt")
                                .setTypes(FurnaceBuilder.Type.NORMAL, FurnaceBuilder.Type.BLAST)
                                .setTimeTicks((int) Math.round((200 / crucibleMultiplier) * meltingSpeed))
                );
                molten.addRecipeBuilder(
                        new CraftBuilder(CraftBuilder.Type.SHAPELESS, metalMaterial.getItem(FormType.INGOT).getItemStack(), molten.getRawName() + "_solidify")
                                .addIngredient(Ingredient.of(molten.getItemStack())
                                        .setWillKeep(true)
                                        .setEditor((i, m) -> base))
                );
            }

            public Molten getMolten() {
                return molten;
            }

            public Solid getSolid() {
                return solid;
            }

            public class Molten extends CustomItem {

                public Molten(String name, MetalMaterial metalMaterial) {
                    super(name, Material.BOWL);

                    rawAppend = metalMaterial.getRawName() + "_molten";
                    modelPath = name.toLowerCase().replace(' ', '_') + "_hot";

                    addLore(ChatColor.RESET + "" + ChatColor.GRAY + "Contents: Molten " + metalMaterial.getName());

                    addMetaModifier(meta -> {
                        meta.setMaxStackSize(1);
                    });
                }
            }

            public class Solid extends CustomItem {

                public Solid(String name, MetalMaterial metalMaterial) {
                    super(name, Material.BOWL);

                    rawAppend = metalMaterial.getRawName() + "_solid";
                    modelPath = name.toLowerCase().replace(' ', '_') + "_metal";

                    addLore(ChatColor.RESET + "" + ChatColor.GRAY + "Contents: Solid " + metalMaterial.getName());

                    addMetaModifier(meta -> {
                        meta.setMaxStackSize(1);
                    });
                }
            }
        }
    }
}
