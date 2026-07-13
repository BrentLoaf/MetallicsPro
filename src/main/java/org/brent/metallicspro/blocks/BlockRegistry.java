package org.brent.metallicspro.blocks;

import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.blocks.types.AnvilBlock;
import org.brent.metallicspro.blocks.types.OreBlock;
import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.items.ItemRegistry;
import org.brent.metallicspro.materials.FormType;
import org.brent.metallicspro.materials.MaterialReference;
import org.brent.metallicspro.materials.UtilityType;
import org.brent.metallicspro.recpies.Ingredient;
import org.brent.metallicspro.recpies.types.CraftBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemRarity;

import java.util.HashSet;
import java.util.Set;

public final class BlockRegistry {

    private final Set<CustomBlock> customBlocks = new HashSet<>();

    public BlockRegistry() {
        add(new OreBlock("Low-Grade Metallic Ore", Material.COPPER_ORE, Material.RAW_COPPER)
                .addMetal("copper", 12)
                .addMetal("zinc", 6)
                .addMetal("tin", 5)
                .addMetal("aluminum", 2));

        add(new OreBlock("Medium-Grade Metallic Ore", Material.IRON_ORE, Material.RAW_IRON)
                .addMetal("iron", 10)
                .addMetal("lead", 6)
                .addMetal("nickel", 6)
                .addMetal("cobalt", 12));
        add(new OreBlock("High-Grade Metallic Ore", Material.GOLD_ORE, Material.RAW_GOLD)
                .addMetal("sodium", 3)
                .addMetal("gold", 3)
                .addMetal("tungsten", 2)
        );

        add(new AnvilBlock("Steel Anvil", Material.ANVIL, new MaterialReference("steel", FormType.INGOT)));
        add(new AnvilBlock("Brass Anvil", Material.CHIPPED_ANVIL, new MaterialReference("brass", FormType.INGOT)));
        add(new AnvilBlock("Stone Anvil", Material.DAMAGED_ANVIL, new MaterialReference("copper", FormType.POWDER)));

        resolveBlocks();
    }

    public void add(CustomBlock customBlock) {
        customBlocks.add(customBlock);
    }

    public void resolveBlocks() {
        for (CustomBlock block : customBlocks) block.resolve();
    }

    public Set<CustomBlock> getBlocks() {
        return customBlocks;
    }
}
