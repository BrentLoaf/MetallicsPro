package org.brent.triggerlib.metallicspro.events;

import org.brent.triggerlib.metallicspro.ores.blocks.OreBlock;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OreBreakEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Block block;
    private final OreBlock oreBlock;
    private int fortuneLevel = 0;

    public OreBreakEvent(Block block, OreBlock oreBlock) {
        this.block = block;
        this.oreBlock = oreBlock;
    }

    public Block getBlock() {
        return block;
    }

    public OreBlock getOreBlock() {
        return oreBlock;
    }

    public OreBreakEvent setFortuneLevel(int fortuneLevel) {
        this.fortuneLevel = fortuneLevel;
        return this;
    }

    public int getFortuneLevel() {
        return fortuneLevel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
