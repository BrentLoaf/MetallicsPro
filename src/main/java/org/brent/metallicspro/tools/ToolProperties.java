package org.brent.metallicspro.tools;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;

public class ToolProperties {

    private boolean hideToolTips = false;
    private ItemFlag[] itemFlags;

    public ToolProperties() {}

    public ToolProperties setHideToolTips(boolean hideToolTips) {
        this.hideToolTips = hideToolTips;
        return this;
    }

    public boolean isHideToolTips() {
        return this.hideToolTips;
    }

    public ToolProperties setItemFlags(ItemFlag... flags) {
        this.itemFlags = flags;
        return this;
    }

    public List<ItemFlag> getItemFlags() {
        return List.of(itemFlags);
    }

    public Consumer<ItemMeta> getMetaModifier() {
        return meta -> {
            meta.setHideTooltip(hideToolTips);
            meta.addItemFlags(itemFlags);
            meta.addAttributeModifier(
                    Attribute.LUCK,
                    new AttributeModifier(
                            NamespacedKey.minecraft("dummy"),
                            0.0,
                            AttributeModifier.Operation.ADD_NUMBER,
                            EquipmentSlotGroup.MAINHAND
                    )
            );
        };
    }
}
