package org.brent.metallicspro.tools;


import org.brent.metallicspro.recpies.Ingredient;
import org.brent.metallicspro.recpies.types.CraftBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ToolRegistry {

    private final HashMap<String, ToolDefinition> tools = new HashMap<>();

    private final String[] METALS = {
            "aluminum",
            "cobalt",
            "copper",
            "gold",
            "iron",
            "lead",
            "nickel",
            "sodium",
            "tin",
            "tungsten",
            "zinc",
            "brass",
            "nitinol",
            "steel"
    };

    public ToolRegistry() {
        add(new ToolDefinition(
                materialName -> materialName + " Foraging Hammer",
                Material.STONE_SHOVEL,
                new ToolProperties()
                        .setItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                )
                .setMaterials(METALS)
                .addMaterial("stone", new ItemStack(Material.COBBLESTONE))
                .setNonForgableFallback(itemStack ->
                        new CraftBuilder(CraftBuilder.Type.SHAPED)
                                .setShape(
                                        " I ",
                                        "ISI",
                                        " S "
                                        )
                                .addIngredient('I', Ingredient.of(itemStack))
                                .addIngredient('S', Ingredient.of(Material.STICK))
                )
        );

        resolveTools();
    }

    public void add(ToolDefinition definition) {
        tools.put(definition.getRawName(), definition);
    }

    public ToolDefinition getFromName(String name) {
        return tools.get(name);
    }

    public HashMap<String, ToolDefinition> getTools() {
        return tools;
    }

    private void resolveTools() {
        tools.values().forEach(ToolDefinition::resolve);
    }
}
