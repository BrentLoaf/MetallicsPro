package org.brent.metallicspro.blocks;

import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.items.ItemRegistry;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class CustomBlock {

    protected static final Random RANDOM = new Random();

    protected final String name;
    protected final Material toReplace;
    protected final CustomItem self;

    protected boolean dropSelf = false;
    protected boolean considerSilk = true;
    protected boolean considerFortune = false;
    protected List<Drop> drops = new ArrayList<>();
    protected List<EventOverride<?>> overrides = new ArrayList<>();

    public CustomBlock(String name, Material toReplace) {
        this.name = name;
        this.toReplace = toReplace;
        this.self = new CustomItem(name, toReplace).setModelPath("");
    }

    public String getName() {
        return name;
    }

    public String getRawName() {
        return name.toLowerCase().replace(' ', '_');
    }

    public Material getToReplace() {
        return toReplace;
    }

    public CustomItem getSelf() {
        return self;
    }

    public List<Drop> getDrops() {
        return drops;
    }

    public boolean willDropSelf() {
        return dropSelf;
    }

    public CustomBlock setDrop(Drop drop) {
        this.drops.add(drop);
        return this;
    }

    public CustomBlock setDropSelf(boolean dropSelf) {
        this.dropSelf = dropSelf;
        return this;
    }

    public CustomBlock considerSilkTouch(boolean considerSilk) {
        this.considerSilk = considerSilk;
        return this;
    }

    public CustomBlock considerFortune(boolean considerFortune) {
        this.considerFortune = considerFortune;
        return this;
    }

    public CustomBlock addOverride(EventOverride<?> override) {
        this.overrides.add(override);
        return this;
    }

    public List<ItemStack> getDrops(boolean hasSilk, int fortuneLevel) {
        List<ItemStack> droppedItems = new ArrayList<>();

        if (hasSilk && considerSilk || dropSelf) {
            droppedItems.add(self.getItemStack());
            return droppedItems;
        }

        for (Drop drop : drops) {
            ItemStack itemStack = drop.getItemStack();

            int min = drop.getMinAmount();
            int max = drop.getMaxAmount();

            int amount;

            if (min >= max) {
                amount = min;
            } else {
                amount = RANDOM.nextInt(min, max + 1);
            }

            if (fortuneLevel > 0 && considerFortune) {
                int bonus = RANDOM.nextInt(fortuneLevel + 2) - 1;

                if (bonus < 0) {
                    bonus = 0;
                }

                amount *= (bonus + 1);
            }

            itemStack.setAmount(amount);
            droppedItems.add(itemStack);
        }

        return droppedItems;
    }

    public void passOverride(Event event) {
        overrides.stream()
                .filter(o -> o.isEvent(event))
                .forEach(o -> o.execute(event));
    }

    public void resolve() {
        ItemRegistry registry = MetallicsPro.getItemRegistry();

        self.resolve();
        registry.add(self);

        for (CustomBlock.Drop drop : getDrops()) {
            CustomItem item = drop.getCustomItemDrop();
            if (item == null) continue;

            item.resolve();
            registry.add(item);
        }
    }

    public static class Drop {

        private final ItemStack drop;
        private CustomItem customItemDrop = null;

        private int minAmount = 1;
        private int maxAmount = 1;

        public Drop(ItemStack drop) {
            this.drop = drop;
        }

        public Drop(CustomItem drop) {
            this.customItemDrop = drop;
            this.drop = drop.getItemStack();
        }

        public Drop(Material drop) {
            this.drop = new ItemStack(drop);
        }

        public ItemStack getItemStack() {
            return drop;
        }

        @Nullable
        public CustomItem getCustomItemDrop() {
            return customItemDrop;
        }

        public int getMinAmount() {
            return minAmount;
        }

        public int getMaxAmount() {
            return maxAmount;
        }

        public Drop setMinDropAmount(int minAmount) {
            this.minAmount = minAmount;
            return this;
        }

        public Drop setMaxDropAmount(int maxAmount) {
            this.maxAmount = maxAmount;
            return this;
        }

        public Drop setDropAmount(int amount) {
            this.minAmount = amount;
            this.maxAmount = amount;
            return this;
        }
    }

    public record EventOverride<T extends Event>(Class<T> eventClass, Consumer<T> consumer) {

        public boolean isEvent(Event event) {
            return eventClass.isInstance(event);
        }

        @SuppressWarnings("unchecked")
        public void execute(Event event) {
            if (isEvent(event)) {
                consumer.accept((T) event);
            }
        }
    }
}
