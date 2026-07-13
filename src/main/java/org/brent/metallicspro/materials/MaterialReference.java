package org.brent.metallicspro.materials;

import org.brent.metallicspro.items.CustomItem;

public record MaterialReference(String id, MaterialType type) {

    public static MaterialRegistry REGISTRY;

    public CustomItem resolve() {
        return REGISTRY.getFromName(id).getItem(type);
    }
}
