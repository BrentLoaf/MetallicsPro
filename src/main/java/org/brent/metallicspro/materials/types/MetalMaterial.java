package org.brent.metallicspro.materials.types;

import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.items.ItemRegistry;
import org.brent.metallicspro.materials.*;
import org.brent.metallicspro.recpies.Ingredient;
import org.brent.metallicspro.recpies.types.CraftBuilder;
import org.brent.metallicspro.recpies.types.FurnaceBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class MetalMaterial extends CustomMaterial {

    public MetalMaterial(String name, MaterialProperties properties) {
        super(name, properties.setForageable(true));

        setFormTypes(
                FormType.INGOT,
                FormType.FIRED_INGOT,
                FormType.NUGGET,
                FormType.POWDER,
                FormType.SMALL_POWDER
        );

        CustomItem ingot = getItem(FormType.INGOT);
        CustomItem nugget = getItem(FormType.NUGGET);
        CustomItem powder = getItem(FormType.POWDER);
        CustomItem smallPowder = getItem(FormType.SMALL_POWDER);

        setRecipesPerForm(
                FormType.INGOT,
                new CraftBuilder(CraftBuilder.Type.SHAPED)
                        .setShape(
                                "NNN",
                                "NNN",
                                "NNN"
                        )
                        .addIngredient('N', Ingredient.of(nugget.getItemStack()))
        );

        setRecipesPerForm(
                FormType.NUGGET,
                new CraftBuilder(CraftBuilder.Type.SHAPELESS)
                        .addIngredient(Ingredient.of(ingot.getItemStack()))
                        .setAmount(9)
        );

        setRecipesPerForm(
                FormType.POWDER,
                new CraftBuilder(CraftBuilder.Type.SHAPELESS)
                        .setKeyAdd("_from_original")
                        .addIngredient(Ingredient.of(ingot.getItemStack()))
                        .addIngredient(Ingredient.of(new MaterialReference("stone",UtilityType.MORTAR_AND_PESTLE))
                                        .setWillKeep(true)),
                new CraftBuilder(CraftBuilder.Type.SHAPED)
                        .setKeyAdd("_from_small")
                        .setShape(
                                "SSS",
                                "SSS",
                                "SSS"
                        )
                        .addIngredient('S', Ingredient.of(smallPowder.getItemStack()))
        );

        setRecipesPerForm(
                FormType.SMALL_POWDER,
                new CraftBuilder(CraftBuilder.Type.SHAPELESS)
                        .setKeyAdd("_from_large")
                        .addIngredient(Ingredient.of(powder.getItemStack()))
                        .setAmount(9),
                new CraftBuilder(CraftBuilder.Type.SHAPELESS)
                        .setKeyAdd("_from_original")
                        .addIngredient(Ingredient.of(nugget.getItemStack()))
                        .addIngredient(Ingredient.of(new MaterialReference("stone",UtilityType.MORTAR_AND_PESTLE))
                                        .setWillKeep(true))
        );

        setRecipesPerForm(
                FormType.FIRED_INGOT,
                new FurnaceBuilder(Ingredient.of(ingot.getItemStack()))
                        .setTypes(FurnaceBuilder.Type.NORMAL, FurnaceBuilder.Type.BLAST)
                        .setTimeTicks((int) Math.round(200 * properties.getHeatResistance().getMultiplier()))
        );
    }

    @Override
    public void resolve() {
        for (CustomMaterial material : REGISTRY.getMaterials().values()) {
            if (!material.getTypes().containsKey(UtilityType.FIRED_CRUCIBLE)) continue;

            registerCrucibles(material);
        }

        super.resolve();
    }

    private void registerCrucibles(CustomMaterial material) {
        CustomItem emptyCrucible = material.getItem(UtilityType.FIRED_CRUCIBLE);

        CustomItem solid = new CustomItem(emptyCrucible.getName(), Material.BOWL)
                .addMetaModifier(meta -> meta.setMaxStackSize(16))
                .setModelPath(emptyCrucible.getRawName() + "_metal")
                .setRawAppend("_solid_" + getRawName())
                .addLore(ChatColor.RESET + "" + ChatColor.GRAY + "Contents: Solid " + getName())
                .addRecipeBuilder(
                        new CraftBuilder(CraftBuilder.Type.SHAPELESS, emptyCrucible.getRawName() + "_" + getRawName() + "_ingot")
                                .addIngredient(Ingredient.of(getItem(FormType.INGOT).getItemStack()))
                                .addIngredient(Ingredient.of(emptyCrucible.getItemStack()))
                )
                .addRecipeBuilder(
                        new CraftBuilder(CraftBuilder.Type.SHAPELESS, emptyCrucible.getRawName() + "_" + getRawName() + "_powder")
                                .addIngredient(Ingredient.of(getItem(FormType.POWDER).getItemStack()))
                                .addIngredient(Ingredient.of(emptyCrucible.getItemStack()))
                );

        CustomItem molten = new CustomItem(emptyCrucible.getName(), Material.BOWL)
                .addMetaModifier(meta -> meta.setMaxStackSize(16))
                .setModelPath(emptyCrucible.getRawName() + "_hot")
                .setRawAppend("_molten_" + getRawName())
                .addLore(ChatColor.RESET + "" + ChatColor.GRAY + "Contents: Molten " + getName())
                .addRecipeBuilder(
                        new FurnaceBuilder(Ingredient.of(solid.getItemStack()), emptyCrucible.getRawName() + "_melt_" + getRawName())
                                .setXp(0f)
                                .setTypes(FurnaceBuilder.Type.NORMAL, FurnaceBuilder.Type.BLAST)
                                .setTimeTicks(
                                        (int) Math.round((200 / material.getProperties().getHeatConduction().getMultiplier())
                                                * getProperties().getHeatResistance().getMultiplier())
                                )
                );



        getItem(FormType.INGOT)
                .addRecipeBuilder(
                        new CraftBuilder(CraftBuilder.Type.SHAPELESS, getRawName() + "_ingot_solidify_from_" + molten.getRawName())
                                .addIngredient(Ingredient.of(molten.getItemStack()).setWillKeep(true)
                                        .setEditor((i, ia) -> emptyCrucible.getItemStack())
                                )
                );

        ItemRegistry itemRegistry = MetallicsPro.getItemRegistry();

        itemRegistry.add(solid);
        itemRegistry.add(molten);
    }
}
