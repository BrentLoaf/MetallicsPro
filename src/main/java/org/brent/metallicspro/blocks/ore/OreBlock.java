package org.brent.metallicspro.blocks.ore;

import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.items.ore.OreItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class OreBlock {

    protected final Material replacedBlock;
    protected final OreItem oreItem;

    protected int dropAmount = 1;

    public OreBlock(Material replacedBlock, OreItem oreItem) {
        this.replacedBlock = replacedBlock;
        this.oreItem = oreItem;
    }

    public Material getReplacedBlock() {
        return replacedBlock;
    }

    public ItemStack getDroppedItem() {
        ItemStack drops = oreItem.getRawOre().getItemStack().clone();

        int amount = dropAmount == 1 ? 1 : new Random().nextInt(dropAmount) + 1;
        drops.setAmount(amount);

        return drops;
    }

    public ItemStack getDroppedItem(int fortuneLevel) {
        ItemStack drops = oreItem.getRawOre().getItemStack().clone();

        int baseAmount = dropAmount == 1 ? 1 : ThreadLocalRandom.current().nextInt(dropAmount) + 1;
        int finalAmount = baseAmount;

        if (fortuneLevel > 0) {
            int bonus = ThreadLocalRandom.current().nextInt(fortuneLevel + 2) - 1;
            if (bonus < 0) bonus = 0;

            finalAmount = baseAmount * (bonus + 1);
        }

        drops.setAmount(finalAmount);
        return drops;
    }
}
