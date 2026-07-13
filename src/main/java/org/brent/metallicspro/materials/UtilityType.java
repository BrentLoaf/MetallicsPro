package org.brent.metallicspro.materials;

import org.brent.metallicspro.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum UtilityType implements MaterialType {
    UNFIRED_CRUCIBLE("unfired_crucible") {
        @Override
        public CustomItem getItem(CustomMaterial material) {
            return new CustomItem("Unfired " + material.getName() + " Crucible", Material.BOWL)
                    .addMetaModifier(meta -> meta.setMaxStackSize(16))
                    .setItemRarity(material.getRarity());
        }
    },
    FIRED_CRUCIBLE("fired_crucible") {
        @Override
        public CustomItem getItem(CustomMaterial material) {
            return new CustomItem(material.getName() + " Crucible", Material.BOWL)
                    .addMetaModifier(meta -> meta.setMaxStackSize(16))
                    .setItemRarity(material.getRarity())
                    .addLore(ChatColor.RESET + "" + ChatColor.GRAY + "Contents: Empty");
        }
    },
    MORTAR_AND_PESTLE("mortar_and_pestle") {
        @Override
        public CustomItem getItem(CustomMaterial material) {
            return new CustomItem(material.getName() + " Mortar and Pestle", Material.BOWL)
                    .addMetaModifier(meta -> meta.setMaxStackSize(1))
                    .setItemRarity(material.getRarity());
        }
    };

    private final String typeName;

    UtilityType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    public abstract CustomItem getItem(CustomMaterial material);
}