package org.brent.metallicspro.listeners.blocks;

import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.blocks.CustomBlock;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
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

import java.util.List;
import java.util.Set;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {

        for (Block block : event.blockList()) {
            handleBlock(block, false, 0);
            block.setType(Material.AIR);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.isDropItems()) return;

        Block brokenBlock = event.getBlock();

        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.SURVIVAL) return;

        ItemStack itemStack = player.getInventory().getItemInMainHand();

        event.setDropItems(false);

        boolean hasSilk = itemStack.hasItemMeta() && itemStack.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH);
        int fortuneLevel = itemStack.getEnchantmentLevel(Enchantment.FORTUNE);

        handleBlock(brokenBlock, hasSilk, fortuneLevel);
    }

    private void handleBlock(Block block, boolean hasSilk, int fortuneLevel) {
        World world = block.getWorld();
        Set<CustomBlock> blocks = MetallicsPro.getBlockRegistry().getBlocks();

        for (CustomBlock customBlock : blocks) {
            if (customBlock.getToReplace() != block.getType()) continue;

            List<ItemStack> drops = customBlock.getDrops(hasSilk, fortuneLevel);
            for (ItemStack drop : drops) {
                world.dropItemNaturally(block.getLocation().add(0.5, 0.5, 0.5), drop);
            }
        }
    }

    public static void init() {
        JavaPlugin plugin = MetallicsPro.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(new BlockBreakListener(), plugin);
    }
}