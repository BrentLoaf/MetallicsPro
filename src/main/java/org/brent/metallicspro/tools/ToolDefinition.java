package org.brent.metallicspro.tools;

import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.materials.CustomMaterial;
import org.brent.metallicspro.materials.MaterialRegistry;
import org.brent.metallicspro.recpies.types.RecipeBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;

public class ToolDefinition {

    protected final Function<String, String> nameFunction;
    protected final Material baseMaterial;
    protected final ToolProperties properties;

    protected final HashMap<String, ItemStack> materials = new HashMap<>();
    protected Function<ItemStack, RecipeBuilder> recipeBuilderFunction;

    public ToolDefinition(Function<String, String> nameFunction, Material baseMaterial, ToolProperties properties) {
        this.nameFunction = nameFunction;
        this.baseMaterial = baseMaterial;
        this.properties = properties;
    }

    public Function<String, String> getNameFunction() {
        return nameFunction;
    }

    public Material getBaseMaterial() {
        return baseMaterial;
    }

    public HashMap<String, ItemStack> getMaterials() {
        return materials;
    }

    public ToolDefinition setMaterials(String... materials) {
        Arrays.stream(materials).forEach(material -> this.materials.put(material, null));
        return this;
    }

    public ToolDefinition addMaterial(String material, ItemStack itemStack) {
        this.materials.put(material, itemStack);
        return this;
    }

    @Nullable
    public Function<ItemStack, RecipeBuilder> getNonForgableFallback() {
        return recipeBuilderFunction;
    }

    public ToolDefinition setNonForgableFallback(Function<ItemStack, RecipeBuilder> recipeBuilderFunction) {
        this.recipeBuilderFunction = recipeBuilderFunction;
        return this;
    }

    public String getRawName() {
        return nameFunction.apply("material").toLowerCase().replace(' ', '_');
    }

    public void resolve() {
        MaterialRegistry materialRegistry = MetallicsPro.getMaterialRegistry();

        for (String materialId : materials.keySet()) {
            CustomMaterial material = materialRegistry.getFromName(materialId);

            CustomItem customItem = new CustomItem(nameFunction.apply(material.getName()), baseMaterial)
                    .setSectionAdd(getRawName())
                    .setItemRarity(material.getRarity())
                    .addMetaModifier(material.getApplier())
                    .addMetaModifier(properties.getMetaModifier());

            if (!material.getProperties().isForageable()) customItem.addRecipeBuilder(recipeBuilderFunction.apply(materials.get(materialId)));

            MetallicsPro.getItemRegistry().add(customItem);
        }
    }
}
