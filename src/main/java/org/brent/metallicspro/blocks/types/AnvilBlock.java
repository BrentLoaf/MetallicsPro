package org.brent.metallicspro.blocks.types;

import org.brent.metallicspro.blocks.CustomBlock;
import org.brent.metallicspro.materials.FormType;
import org.brent.metallicspro.materials.MaterialReference;
import org.brent.metallicspro.materials.MaterialType;
import org.brent.metallicspro.recpies.Ingredient;
import org.brent.metallicspro.recpies.types.CraftBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnvilBlock extends CustomBlock {

    private static final HashMap<Location, AnvilWorldData> IN_WORLD_ANVILS = new HashMap<>();

    private final MaterialReference craftingItem;

    public AnvilBlock(String name, Material toReplace, MaterialReference craftingItem) {
        super(name, toReplace);
        this.craftingItem = craftingItem;

        setDropSelf(true);

        addOverride(new EventOverride<>(PlayerInteractEvent.class, event -> {
            if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
            event.setCancelled(true);

            Player player = event.getPlayer();
            if (player.isSneaking()) return;

            ItemStack heldItem = event.getItem();
            if (heldItem == null || heldItem.getType() == Material.AIR) return;
            if (!MaterialType.isType(heldItem, FormType.FIRED_INGOT)) return;

            Location location = event.getClickedBlock().getLocation();

            if (!IN_WORLD_ANVILS.containsKey(location)) IN_WORLD_ANVILS.put(location,
                    new AnvilWorldData(0, new ArrayList<>())
            );

            boolean setItem = IN_WORLD_ANVILS.get(location).addItem(heldItem.clone(), location);

            if (!setItem) return;

            if (heldItem.getAmount() <= 1) {
                player.getInventory().setItemInMainHand(null);
            } else {
                heldItem.setAmount(heldItem.getAmount() - 1);
            }
        }));

        addOverride(new EventOverride<>(PlayerInteractEvent.class, event -> {
            if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
            event.setCancelled(true);

            Location location = event.getClickedBlock().getLocation();
            AnvilWorldData data = IN_WORLD_ANVILS.get(location);
            if (data == null || data.displayItems.isEmpty()) return;

            Player player = event.getPlayer();
            ItemStack heldItem = event.getItem();

            if (!player.isSneaking()) return;
            if (!(heldItem == null || heldItem.getType() == Material.AIR)) return;

            player.give(data.getLatest());
            data.removeItem(location);
        }));

        addOverride(new EventOverride<>(BlockBreakEvent.class, event -> {
            Location location = event.getBlock().getLocation();
            if (!IN_WORLD_ANVILS.containsKey(location)) return;

            AnvilWorldData data = IN_WORLD_ANVILS.get(location);
            data.dropAll(location);
        }));
    }

    @Override
    public void resolve() {
        if (craftingItem != null) {
            self.addRecipeBuilder(new CraftBuilder(CraftBuilder.Type.SHAPED)
                    .setShape(
                            "III",
                            " I ",
                            "III"
                    )
                    .addIngredient('I', Ingredient.of(craftingItem))
            );
        }

        super.resolve();
    }

    public class AnvilWorldData {

        private static final double ITEM_SPACING = 0.0125;
        private int clickAmount;
        private final List<ItemDisplay> displayItems;

        public AnvilWorldData(int clickAmount, List<ItemDisplay> displayItems) {
            this.clickAmount = clickAmount;
            this.displayItems = displayItems;
        }

        public void removeItem(Location location) {
            displayItems.getLast().remove();
            displayItems.removeLast();

            if (displayItems.isEmpty()) IN_WORLD_ANVILS.remove(location);
        }

        public ItemStack getLatest() {
            ItemStack itemStack = displayItems.getLast().getItemStack();
            itemStack.setAmount(1);
            return itemStack;
        }

        private boolean addItem(ItemStack heldItem, Location location) {
            World world = location.getWorld();

            heldItem.setAmount(1);

            int itemAmount = displayItems.size();

            if (itemAmount >= 8) return false;

            if (!displayItems.isEmpty() && !displayItems.getFirst().getItemStack().isSimilar(heldItem)) return false;

            ItemDisplay display = world.spawn(location.clone().add(0.5, 1 + ITEM_SPACING + itemAmount * (ITEM_SPACING * 3), 0.5), ItemDisplay.class);
            display.setItemStack(heldItem);

            Vector3f scale = new Vector3f(0.5f, 0.5f, 0.5f);

            Quaternionf rotation = new Quaternionf().rotationX((float) Math.toRadians(90));

            Vector3f translation = new Vector3f(0, 0, 0);

            display.setTransformation(new Transformation(
                    translation,
                    rotation,
                    scale,
                    new Quaternionf()
            ));

            displayItems.add(display);
            return true;
        }

        public void dropAll(Location location) {
            World world = location.getWorld();
            Location dropLocation = location.clone().add(0.5, 1.5, 0.5);

            for (ItemDisplay display : displayItems) {
                world.dropItemNaturally(dropLocation, display.getItemStack());
            }

            delete(location);
        }

        public void delete(Location location) {
            for (ItemDisplay display : displayItems) display.remove();
            IN_WORLD_ANVILS.remove(location);
        }
    }
}
