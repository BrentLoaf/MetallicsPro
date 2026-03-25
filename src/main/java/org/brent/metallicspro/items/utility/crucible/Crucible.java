package org.brent.metallicspro.items.utility.crucible;

import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.items.CustomItemBuilder;
import org.brent.metallicspro.items.ItemRegistry;
import org.brent.metallicspro.items.metal.Metal;
import org.brent.metallicspro.recpies.Ingredient;
import org.brent.metallicspro.recpies.types.CraftBuilder;
import org.brent.metallicspro.recpies.types.FurnaceBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class Crucible implements CustomItemBuilder {

    protected final UnfiredCrucible unfired;
    protected final FiredCrucible fired;

    protected final int firedDurability;

    public Crucible(String unfiredName, String firedName, ItemRarity rarity, Material... craftings) {
        unfired = new UnfiredCrucible(unfiredName, rarity);
        fired = new FiredCrucible(firedName, rarity, firedDurability);

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
                        .addTypes(FurnaceBuilder.Type.NORMAL)
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
            super("Unfired " + name + " Crucible", Material.BOWL, rarity);
        }
    }

    public class FiredCrucible extends CustomItem {

        private static @Nullable NamespacedKey typeKey = null;
        private static @Nullable NamespacedKey isMolten = null;

        public FiredCrucible(String name, ItemRarity rarity, int firedDurability) {
            super(name + " Crucible", Material.BOWL, rarity);

            rawAppend = "empty";
            modelPath = this.name.toLowerCase().replace(' ', '_');

            if (typeKey == null) typeKey =  new NamespacedKey(MetallicsPro.getPlugin(), "contents");
            if (isMolten == null) typeKey =  new NamespacedKey(MetallicsPro.getPlugin(), "is_molten");

            addLore(ChatColor.RESET + "" + ChatColor.GRAY + "Contents: Empty");

            addMetaModifier(meta -> {
                meta.getPersistentDataContainer().set(typeKey, PersistentDataType.STRING, "none");
                meta.getPersistentDataContainer().set(typeKey, PersistentDataType.BOOLEAN, false);
            });
        }

        public List<CustomItem> getCrucibleTypes() {
            List<CustomItem> types = new ArrayList<>();

            for (Metal metal : MetallicsPro.getMetalRegistry().getMetals()) {
                CrucibleType type = new CrucibleType(name, metal, getItemStack());

                types.add(type.getMolten());
                types.add(type.getSolid());
            }

            return types;
        }

        public class CrucibleType {

            private final Molten molten;
            private final Solid solid;

            public CrucibleType(String name, Metal metal, ItemStack base) {
                molten = new Molten(name, metal);
                solid = new Solid(name, metal);

                solid.addRecipeBuilder(
                        new CraftBuilder(CraftBuilder.Type.SHAPELESS, solid.getItemStack(), solid.getRawName() + "_ingot")
                                .addIngredient(Ingredient.of(base))
                                .addIngredient(Ingredient.of(metal.getIngot().getItemStack()))
                );
                solid.addRecipeBuilder(
                        new CraftBuilder(CraftBuilder.Type.SHAPELESS, solid.getItemStack(), solid.getRawName() + "_powder")
                                .addIngredient(Ingredient.of(base))
                                .addIngredient(Ingredient.of(metal.getPowder().getItemStack()))
                );

                molten.addRecipeBuilder(
                        new FurnaceBuilder(molten.getItemStack(), Ingredient.of(solid.getItemStack()), molten.getRawName() + "_melt")
                                .addTypes(FurnaceBuilder.Type.NORMAL, FurnaceBuilder.Type.BLAST)
                );
                molten.addRecipeBuilder(
                        new CraftBuilder(CraftBuilder.Type.SHAPELESS, metal.getIngot().getItemStack(), molten.getRawName() + "_solidify")
                                .addIngredient(Ingredient.of(molten.getItemStack()).setWillKeep(true)
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

                public Molten(String name, Metal metal) {
                    super(name, Material.BOWL, ItemRarity.COMMON);

                    rawAppend = metal.getRawName() + "_molten";
                    modelPath = name.toLowerCase().replace(' ', '_');

                    addLore(ChatColor.RESET + "" + ChatColor.GRAY + "Contents: Molten " + metal.getName());

                    addMetaModifier(meta -> {
                        meta.getPersistentDataContainer().set(typeKey, PersistentDataType.STRING, metal.getRawName());
                        meta.getPersistentDataContainer().set(typeKey, PersistentDataType.BOOLEAN, true);
                    });
                }
            }

            public class Solid extends CustomItem {

                public Solid(String name, Metal metal) {
                    super(name, Material.BOWL, ItemRarity.COMMON);

                    rawAppend = metal.getRawName() + "_solid";
                    modelPath = name.toLowerCase().replace(' ', '_');

                    addLore(ChatColor.RESET + "" + ChatColor.GRAY + "Contents: Solid " + metal.getName());

                    addMetaModifier(meta -> {
                        meta.getPersistentDataContainer().set(typeKey, PersistentDataType.STRING, metal.getRawName());
                        meta.getPersistentDataContainer().set(typeKey, PersistentDataType.BOOLEAN, false);
                    });
                }
            }
        }
    }
}
