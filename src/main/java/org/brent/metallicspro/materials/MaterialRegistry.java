package org.brent.metallicspro.materials;

import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.items.ItemRegistry;
import org.brent.metallicspro.materials.types.AlloyMaterial;
import org.brent.metallicspro.materials.types.MetalMaterial;
import org.brent.metallicspro.materials.types.PowderableMaterial;
import org.brent.metallicspro.recpies.Ingredient;
import org.brent.metallicspro.recpies.types.CraftBuilder;
import org.brent.metallicspro.recpies.types.FurnaceBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MaterialRegistry {

    private final HashMap<String, CustomMaterial> materials = new HashMap<>();

    private final ItemRegistry itemRegistry;

    public MaterialRegistry(ItemRegistry itemRegistry) {
        this.itemRegistry = itemRegistry;
        CustomMaterial.REGISTRY = this;
        MaterialReference.REGISTRY = this;

        // Misc
        add(new CustomMaterial("Stone", new MaterialProperties())
                .setUtilityTypes(UtilityType.MORTAR_AND_PESTLE)
                .setRecipesPerForm(UtilityType.MORTAR_AND_PESTLE, new CraftBuilder(CraftBuilder.Type.SHAPED)
                        .setShape(
                                "   ",
                                "S S",
                                " S "
                        )
                        .addIngredient('S', Ingredient.of(new RecipeChoice.MaterialChoice(
                                Material.STONE,
                                Material.DIORITE,
                                Material.ANDESITE,
                                Material.GRANITE,
                                Material.COBBLESTONE,
                                Material.COBBLED_DEEPSLATE,
                                Material.DEEPSLATE
                        )))
                )
        );

        add(new CustomMaterial("Clay", new MaterialProperties())
                .setUtilityTypes(UtilityType.UNFIRED_CRUCIBLE)
                .setRecipesPerForm(UtilityType.UNFIRED_CRUCIBLE, new CraftBuilder(CraftBuilder.Type.SHAPED)
                        .setShape(
                                "   ",
                                "C C",
                                " C "
                        )
                        .addIngredient('C', Ingredient.of(Material.CLAY_BALL))
                )
        );

        add(new CustomMaterial("Ceramic", new MaterialProperties()
                .setHeatConduction(MaterialProperties.HeatConduction.LOW))
                .setUtilityTypes(UtilityType.FIRED_CRUCIBLE)
                .setRecipesPerForm(UtilityType.FIRED_CRUCIBLE,
                        new FurnaceBuilder(Ingredient.of(new MaterialReference("clay", UtilityType.UNFIRED_CRUCIBLE)))
                                .setTypes(FurnaceBuilder.Type.NORMAL)
                )
        );

        add(new PowderableMaterial("Carbon", new MaterialProperties(), Material.COAL, Material.CHARCOAL)
                .setUtilityTypes(UtilityType.UNFIRED_CRUCIBLE)
                .setRecipesPerForm(UtilityType.UNFIRED_CRUCIBLE, new CraftBuilder(CraftBuilder.Type.SHAPED)
                        .setShape(
                                "   ",
                                "C C",
                                " C "
                        )
                        .addIngredient('C', Ingredient.of(new RecipeChoice.MaterialChoice(Material.COAL, Material.CHARCOAL)))
                )
        );

        add(new CustomMaterial("Graphite", new MaterialProperties()
                        .setHeatConduction(MaterialProperties.HeatConduction.VERY_HIGH))
                .setUtilityTypes(UtilityType.FIRED_CRUCIBLE)
                        .setRecipesPerForm(UtilityType.FIRED_CRUCIBLE,
                                new FurnaceBuilder(Ingredient.of(new MaterialReference("carbon", UtilityType.UNFIRED_CRUCIBLE)))
                                        .setTypes(FurnaceBuilder.Type.NORMAL)
                        )
                );

        // Metals
        add(new MetalMaterial(
                "Aluminum",
                new MaterialProperties()
                        .setHeatResistance(MaterialProperties.HeatResistance.LOW)
                        .setHeatConduction(MaterialProperties.HeatConduction.HIGH))
                .setRarity(ItemRarity.UNCOMMON)
        );

        add(new MetalMaterial(
                "Cobalt",
                new MaterialProperties()
                        .setHeatResistance(MaterialProperties.HeatResistance.VERY_HIGH)
                        .setHeatConduction(MaterialProperties.HeatConduction.MODERATE))
                .setRarity(ItemRarity.RARE)
        );

        add(new MetalMaterial(
                "Copper",
                new MaterialProperties()
                        .setHeatResistance(MaterialProperties.HeatResistance.LOW)
                        .setHeatConduction(MaterialProperties.HeatConduction.VERY_HIGH))
                .setRarity(ItemRarity.COMMON)
        );

        add(new MetalMaterial(
                "Gold",
                new MaterialProperties()
                        .setHeatResistance(MaterialProperties.HeatResistance.MODERATE)
                        .setHeatConduction(MaterialProperties.HeatConduction.EXTREME))
                .setRarity(ItemRarity.RARE)
        );

        add(new MetalMaterial(
                "Iron",
                new MaterialProperties()
                        .setHeatResistance(MaterialProperties.HeatResistance.HIGH)
                        .setHeatConduction(MaterialProperties.HeatConduction.MODERATE))
                .setRarity(ItemRarity.UNCOMMON)
        );

        add(new MetalMaterial(
                "Lead",
                new MaterialProperties()
                        .setHeatResistance(MaterialProperties.HeatResistance.VERY_LOW)
                        .setHeatConduction(MaterialProperties.HeatConduction.LOW))
                .setRarity(ItemRarity.UNCOMMON)
        );

        add(new MetalMaterial(
                "Nickel",
                new MaterialProperties()
                        .setHeatResistance(MaterialProperties.HeatResistance.HIGH)
                        .setHeatConduction(MaterialProperties.HeatConduction.HIGH))
                .setRarity(ItemRarity.UNCOMMON)
        );

        add(new MetalMaterial(
                "Sodium",
                new MaterialProperties()
                        .setHeatResistance(MaterialProperties.HeatResistance.VERY_LOW)
                        .setHeatConduction(MaterialProperties.HeatConduction.HIGH)
                        .setHydroExplosive(true))
                .setRarity(ItemRarity.RARE)
        );

        add(new MetalMaterial(
                "Tin",
                new MaterialProperties()
                        .setHeatResistance(MaterialProperties.HeatResistance.MINIMAL)
                        .setHeatConduction(MaterialProperties.HeatConduction.LOW))
                .setRarity(ItemRarity.COMMON)
        );

        add(new MetalMaterial(
                "Tungsten",
                new MaterialProperties()
                        .setHeatResistance(MaterialProperties.HeatResistance.EXTREME)
                        .setHeatConduction(MaterialProperties.HeatConduction.VERY_LOW))
                .setRarity(ItemRarity.EPIC)
        );

        add(new MetalMaterial(
                "Zinc",
                new MaterialProperties()
                        .setHeatResistance(MaterialProperties.HeatResistance.VERY_LOW)
                        .setHeatConduction(MaterialProperties.HeatConduction.MODERATE))
                .setRarity(ItemRarity.COMMON)
        );

        // Alloys
        add(new AlloyMaterial(
                "Brass",
                new MaterialProperties()
                        .setHeatResistance(MaterialProperties.HeatResistance.MODERATE)
                        .setHeatConduction(MaterialProperties.HeatConduction.HIGH),
                new MaterialReference("copper", FormType.POWDER),
                new MaterialReference("aluminum", FormType.POWDER))
                .setAmount(2)
                .setRarity(ItemRarity.COMMON)
        );


        add(new AlloyMaterial(
                "Nitinol",
                new MaterialProperties()
                        .setHeatResistance(MaterialProperties.HeatResistance.VERY_HIGH)
                        .setHeatConduction(MaterialProperties.HeatConduction.MODERATE),
                new MaterialReference("tungsten", FormType.POWDER),
                new MaterialReference("nickel", FormType.POWDER))
                .setAmount(2)
                .setRarity(ItemRarity.RARE)
        );

        add(new AlloyMaterial(
                "Steel",
                new MaterialProperties()
                        .setHeatResistance(MaterialProperties.HeatResistance.VERY_HIGH)
                        .setHeatConduction(MaterialProperties.HeatConduction.MODERATE),
                new MaterialReference("carbon", FormType.SMALL_POWDER),
                new MaterialReference("iron", FormType.POWDER))
                .setAmount(1)
                .setRarity(ItemRarity.EPIC)
        );

        resolveMaterials();
    }

    private void add(CustomMaterial material) {
        materials.put(material.getRawName(), material);
    }

    public void resolveMaterials() {
        for (CustomMaterial material : materials.values()) {
            material.resolve();

            for (MaterialType form : material.getTypes().keySet()) {
                CustomItem item = material.getItem(form);
                itemRegistry.add(item);
            }
        }
    }

    public @Nullable CustomMaterial getFromName(String name) {
        return materials.get(name);
    }

    public HashMap<String, MetalMaterial> getMetals() {
        return materials.entrySet().stream()
                .filter(entry -> entry.getValue() instanceof MetalMaterial)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (MetalMaterial) entry.getValue(),
                        (a, b) -> a,
                        HashMap::new
                ));
    }

    public HashMap<String, CustomMaterial> getMaterials() {
        return materials;
    }

    @Nullable
    public CustomMaterial getMaterial(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return null;

        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey key = CustomMaterial.getMaterialKey();

        if (!data.has(key)) return null;

        return getFromName(data.get(key, PersistentDataType.STRING));
    }
}
