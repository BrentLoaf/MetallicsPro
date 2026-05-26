package org.brent.metallicspro.blocks.ore;

import org.brent.metallicspro.items.ore.OreItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public abstract class OreBlock {

    protected final Map<Material, int[]> materials;
    protected final OreItem oreItem;

    public OreBlock(OreItem oreItem, Map<Material, int[]> materials) {
        this.materials = materials;
        this.oreItem = oreItem;
    }

    public Map<Material, int[]> getReplacedBlocks() {
        return materials;
    }

    public int getAmount(Material material, int fortuneLevel) {
        if (!materials.containsKey(material)) return 0;

        int[] values = materials.get(material);
        if (values.length != 2) return values[0];

        int min = values[0];
        int max = values[1];

        int amount = (int) (Math.random() * (max - min + 1)) + min;

        if (fortuneLevel > 0) {
            int bonus = java.util.concurrent.ThreadLocalRandom.current().nextInt(fortuneLevel + 2) - 1;
            if (bonus < 0) bonus = 0;

            amount = amount * (bonus + 1);
        }

        return amount;
    }

    public @Nullable ItemStack getDroppedItem(Material blockBroken) {
        ItemStack drops = oreItem.getRawOre().getItemStack().clone();

        int amount = getAmount(blockBroken, 0);
        if (amount <= 0) return null;
        drops.setAmount(amount);

        return drops;
    }

    public @Nullable ItemStack getDroppedItem(Material blockBroken, int fortuneLevel) {
        ItemStack drops = oreItem.getRawOre().getItemStack().clone();

        int amount = getAmount(blockBroken, fortuneLevel);
        if (amount <= 0) return null;
        drops.setAmount(amount);

        return drops;
    }
}
