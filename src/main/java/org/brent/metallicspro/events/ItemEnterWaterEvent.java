package org.brent.metallicspro.events;

import org.bukkit.entity.Item;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ItemEnterWaterEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Item item;
    private boolean remove;

    public ItemEnterWaterEvent(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public boolean isRemove() {
        return remove;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
