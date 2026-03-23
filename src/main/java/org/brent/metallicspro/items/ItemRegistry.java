package org.brent.metallicspro.items;

import org.brent.metallicspro.items.utility.MortarAndPestle;
import org.brent.metallicspro.items.utility.Sieve;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;

public class ItemRegistry {

    private final HashMap<String, CustomItem> customItems = new HashMap<>();

    public ItemRegistry() {
        add(new MortarAndPestle());
        add(new Sieve());
    }

    public void add(CustomItem item) {
        customItems.put(item.getRawName(), item);

        item.init(this);
    }

    public @Nullable CustomItem getFromName(String rawName) {
        return customItems.get(rawName);
    }

    public HashMap<String, CustomItem> getCustomItem() {
        return customItems;
    }
}
