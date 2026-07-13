package org.brent.metallicspro.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.recpies.types.RecipeBuilder;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;

public class CustomItem {

    protected final String name;
    protected final Material baseMaterial;

    protected ItemRarity rarity = ItemRarity.COMMON;
    protected List<RecipeBuilder<?, ?>> recipeBuilders = new ArrayList<>();
    protected List<Consumer<ItemMeta>> metaModifiers = new ArrayList<>();
    protected List<String> lore = new ArrayList<>();

    protected String rawAppend = "";
    protected String modelPath = null;
    protected String sectionAdd = "";
    protected boolean handleTextures = true;

    protected ItemStack itemStack;

    public CustomItem(String name, Material baseMaterial) {
        this.name = name;
        this.baseMaterial = baseMaterial;
    }

    private ItemStack buildItem() {
        ItemStack itemStack = new ItemStack(baseMaterial);

        itemStack.editMeta(meta -> {
            meta.displayName(Component.text(name)
                    .decoration(TextDecoration.ITALIC, false));
            meta.setRarity(rarity);

            NamespacedKey modelKey = getModelKey();
            if (modelKey != null) {
                meta.setItemModel(modelKey);
            }

            metaModifiers.forEach(m -> m.accept(meta));
            meta.setLore(lore);
        });

        return itemStack;
    }

    public ItemStack getItemStack() {
        if (itemStack == null) itemStack = buildItem();
        return itemStack;
    }

    @Nullable
    public NamespacedKey getModelKey() {
        String finalPath;

        if (modelPath == null) {
            finalPath = getRawName();
        } else if (!modelPath.isEmpty()) {
            finalPath = modelPath;
        } else {
            finalPath = null;
        }

        if (finalPath == null) return null;

        String section = sectionAdd == null || sectionAdd.isBlank()
                ? ""
                : sectionAdd + "/";

        return new NamespacedKey(
                "metallicspro",
                section + finalPath
        );
    }

    public void init(ItemRegistry registry) {
        for (RecipeBuilder<?, ?> recipe : recipeBuilders) {
            if (recipe.getResult() == null) recipe.setResult(getItemStack());
            if (recipe.getKeyString() == null) recipe.setKey(getRawName());

            MetallicsPro.getRecipeRegistry().add(recipe);
        }
    }

    public String getName() {
        return name;
    }

    public String getRawName() {
        String raw = name.toLowerCase().replace(' ', '_');

        if (rawAppend.isEmpty()) {
            return raw;
        } else {
            return raw + "_" + rawAppend;
        }
    }

    public Material getBaseMaterial() {
        return baseMaterial;
    }

    public ItemRarity getRarity() {
        return rarity;
    }

    public List<RecipeBuilder<?, ?>> getRecipeBuilders() {
        return recipeBuilders;
    }

    public boolean shouldHandleTextures() {
        return handleTextures;
    }

    public CustomItem addRecipeBuilder(RecipeBuilder<?, ?> recipeBuilder) {
        this.recipeBuilders.add(recipeBuilder);
        return this;
    }

    public CustomItem addMetaModifier(Consumer<ItemMeta> metaModifier) {
        this.metaModifiers.add(metaModifier);
        return this;
    }

    public CustomItem addLore(String... lines) {
        this.lore.addAll(List.of(lines));
        return this;
    }

    public CustomItem setRawAppend(String rawAppend) {
        this.rawAppend = rawAppend;
        return this;
    }

    @Nullable
    public CustomItem setModelPath(String modelPath) {
        this.modelPath = modelPath;
        return this;
    }

    public CustomItem setSectionAdd(String sectionAdd) {
        this.sectionAdd = sectionAdd;
        return this;
    }

    public CustomItem setHandleTextures(boolean handleTextures) {
        this.handleTextures = handleTextures;
        return this;
    }

    public CustomItem setItemRarity(ItemRarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public void resolve() {
        recipeBuilders.stream().forEach(r -> r.resolve());
    }

    public static File getItemJson(File texturepack) {
        return new File(texturepack, "assets/metallicspro/items/%EXAMPLE%.json");
    }

    public static File getModelJson(File texturepack) {
        return new File(texturepack, "assets/metallicspro/models/%EXAMPLE%.json");
    }
}
