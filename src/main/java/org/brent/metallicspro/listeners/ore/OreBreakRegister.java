package org.brent.metallicspro.listeners.ore;

import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.events.OreBreakEvent;
import org.brent.metallicspro.blocks.ore.OreBlock;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class OreBreakRegister implements Listener {

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        Iterator<Block> it = event.blockList().iterator();

        while (it.hasNext()) {
            Block block = it.next();

            OreBlock oreBlock = handleBlock(block);
            if (oreBlock == null) continue;

            it.remove();

            block.setType(Material.AIR);

            OreBreakEvent oreEvent = new OreBreakEvent(block, oreBlock);
            Bukkit.getPluginManager().callEvent(oreEvent);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.isDropItems()) return;

        Block brokenBlock = event.getBlock();

        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.SURVIVAL) return;

        ItemStack itemStack = player.getInventory().getItemInMainHand();

        Collection<ItemStack> drops = brokenBlock.getDrops(itemStack, player);
        if (drops.isEmpty()) return;

        OreBlock oreBlock = handleBlock(brokenBlock);
        if (oreBlock == null) return;

        event.setDropItems(false);
        int fortuneLevel = itemStack.getEnchantmentLevel(Enchantment.FORTUNE);

        OreBreakEvent oreEvent = new OreBreakEvent(brokenBlock, oreBlock).setFortuneLevel(fortuneLevel);
        Bukkit.getPluginManager().callEvent(oreEvent);
    }

    private @Nullable OreBlock handleBlock(Block block) {
        HashSet<OreBlock> oreBlocks = MetallicsPro.getOreBlockRegistry().getOreBlocks();

        for (OreBlock oreBlock : oreBlocks) {
            if (block.getType() != oreBlock.getReplacedBlock()) continue;
            return oreBlock;
        }

        return null;
    }

    public static void init() {
        JavaPlugin plugin = MetallicsPro.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(new OreBreakRegister(), plugin);
    }
}
