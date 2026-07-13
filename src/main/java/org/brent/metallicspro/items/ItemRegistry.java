package org.brent.metallicspro.items;

import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.items.utility.Sieve;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ItemRegistry {

    private final HashMap<String, CustomItem> customItems = new HashMap<>();
    private final Set<CustomItemBuilder> itemBuilders = new HashSet<>();

    public ItemRegistry() {
        add(new Sieve());
    }

    public void add(CustomItem item) {
        customItems.put(item.getRawName(), item);

        item.init(this);
    }

    public void add(CustomItemBuilder itemBuilder) {
        Bukkit.getScheduler().runTask(MetallicsPro.getPlugin(), task -> {
            itemBuilders.add(itemBuilder);
            itemBuilder.init(this);
        });
    }

    public @Nullable CustomItem getFromName(String rawName) {
        return customItems.get(rawName);
    }

    public HashMap<String, CustomItem> getCustomItem() {
        return customItems;
    }

    public HashMap<String, CustomItem> getCustomItems() {
        return customItems;
    }

    public Set<CustomItemBuilder> getItemBuilders() {
        return itemBuilders;
    }
}
