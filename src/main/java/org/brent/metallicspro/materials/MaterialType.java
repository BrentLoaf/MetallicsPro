package org.brent.metallicspro.materials;

import org.brent.metallicspro.MetallicsPro;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.function.Consumer;

public interface MaterialType {

    static NamespacedKey formKey() {
        return new NamespacedKey(MetallicsPro.getPlugin(), "form");
    }

    static boolean isType(ItemStack item, MaterialType type) {
        NamespacedKey key = formKey();

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if (!data.has(key)) return false;

        return data.get(key, PersistentDataType.STRING).equals(type.getTypeName());
    }

    default Consumer<ItemMeta> getApplier() {
        return meta -> {
            meta.getPersistentDataContainer().set(formKey(), PersistentDataType.STRING, getTypeName());
        };
    }

    String getTypeName();
}
