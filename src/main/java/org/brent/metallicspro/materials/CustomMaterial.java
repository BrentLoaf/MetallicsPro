package org.brent.metallicspro.materials;

import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.recpies.types.RecipeBuilder;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.function.Consumer;

public class CustomMaterial {

    public static MaterialRegistry REGISTRY = null;

    protected final String name;
    protected final MaterialProperties properties;

    protected HashMap<MaterialType, CustomItem> materialTypes = new HashMap<>();
    protected ItemRarity rarity = ItemRarity.COMMON;

    public CustomMaterial(String name, MaterialProperties properties) {
        this.name = name;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public String getRawName() {
        return name.replace(' ', '_').toLowerCase();
    }

    public MaterialProperties getProperties() {
        return properties;
    }

    public HashMap<MaterialType, CustomItem> getTypes() {
        return materialTypes;
    }

    public ItemRarity getRarity() {
        return rarity;
    }

    @Nullable
    public CustomItem getItem(MaterialType type) {
        return materialTypes.get(type);
    }


    public CustomMaterial setRarity(ItemRarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public CustomMaterial setFormTypes(FormType... types) {
        for (FormType type : types) materialTypes.put(type, type.getItem(this)
                .addMetaModifier(type.getApplier())
                .addMetaModifier(getApplier())
                .addLore(properties.isHydroExplosive() ? ChatColor.RESET + "" + ChatColor.RED + "[Hydro Explosive]" : "")
        );
        return this;
    }

    public CustomMaterial setUtilityTypes(UtilityType... types) {
        for (UtilityType type : types) materialTypes.put(type, type.getItem(this)
                .addMetaModifier(type.getApplier())
                .addMetaModifier(getApplier())
                .addLore(properties.isHydroExplosive() ? ChatColor.RESET + "" + ChatColor.RED + "[Hydro Explosive]" : "")
        );
        return this;
    }

    public CustomMaterial setRecipesPerForm(MaterialType type, RecipeBuilder... builders) {
        CustomItem item = materialTypes.get(type);

        for (RecipeBuilder builder : builders) item.addRecipeBuilder(builder);

        materialTypes.put(type, item);
        return this;
    }

    public void resolve() {
        materialTypes.values().forEach(i -> i.resolve());
    }

    public static NamespacedKey getMaterialKey() {
        return new NamespacedKey(MetallicsPro.getPlugin(), "material");
    }

    public Consumer<ItemMeta> getApplier() {
        return meta -> meta.getPersistentDataContainer().set(getMaterialKey(), PersistentDataType.STRING, getRawName());
    }
}
