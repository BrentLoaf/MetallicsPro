package org.brent.metallicspro.materials;

import org.brent.metallicspro.items.CustomItem;
import org.bukkit.Material;

public enum FormType implements MaterialType {
    INGOT("ingot") {
        @Override
        public CustomItem getItem(CustomMaterial material) {
            return new CustomItem(material.getName() + " Ingot", Material.IRON_INGOT)
                    .setItemRarity(material.getRarity())
                    .setSectionAdd(material.getName().toLowerCase());
        }
    },
    FIRED_INGOT("fired_ingot") {
        @Override
        public CustomItem getItem(CustomMaterial material) {
            return new CustomItem("Fired " + material.getName() + " Ingot", Material.IRON_INGOT)
                    .setItemRarity(material.getRarity())
                    .setHandleTextures(false)
                    .setModelPath("fired_ingot");
        }
    },
    NUGGET("nugget") {
        @Override
        public CustomItem getItem(CustomMaterial material) {
            return new CustomItem(material.getName() + " Nugget", Material.IRON_NUGGET)
                    .setItemRarity(material.getRarity())
                    .setSectionAdd(material.getName().toLowerCase());
        }
    },
    POWDER("powder") {
        @Override
        public CustomItem getItem(CustomMaterial material) {
            return new CustomItem(material.getName() + " Powder Pile", Material.SUGAR)
                    .setItemRarity(material.getRarity())
                    .setSectionAdd(material.getName().toLowerCase());
        }
    },
    SMALL_POWDER("small_powder") {
        @Override
        public CustomItem getItem(CustomMaterial material) {
            return new CustomItem("Small " + material.getName() + " Powder Pile", Material.SUGAR)
                    .setItemRarity(material.getRarity())
                    .setSectionAdd(material.getName().toLowerCase());
        }
    };

    private final String typeName;

    FormType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    public abstract CustomItem getItem(CustomMaterial material);
}
