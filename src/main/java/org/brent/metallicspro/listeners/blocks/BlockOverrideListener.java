package org.brent.metallicspro.listeners.blocks;

import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.blocks.CustomBlock;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockOverrideListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block block = event.getClickedBlock();
        if (block == null) return;

        for (CustomBlock customBlock : MetallicsPro.getBlockRegistry().getBlocks()) {
            if (customBlock.getToReplace() != block.getType()) continue;

            customBlock.passOverride(event);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        for (CustomBlock customBlock : MetallicsPro.getBlockRegistry().getBlocks()) {
            if (customBlock.getToReplace() != block.getType()) continue;

            customBlock.passOverride(event);
        }
    }

    public static void init() {
        JavaPlugin plugin = MetallicsPro.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(new BlockOverrideListener(), plugin);
    }
}
