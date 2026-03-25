package org.brent.metallicspro.recpies.types;

import org.brent.metallicspro.recpies.Ingredient;
import org.bukkit.Bukkit;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FurnaceBuilder extends RecipeBuilder<FurnaceBuilder, List<CookingRecipe<?>>> {

    protected final HashSet<Type> types = new HashSet<>();

    protected Ingredient input;
    protected float xp = 0.0f;
    protected int timeTicks = 200;

    public FurnaceBuilder(ItemStack result, Ingredient input, String key) {
        super(result, key);
        this.input = input;
        keyAdd = "_furnace";
    }

    public List<Type> getTypes() {
        return types.stream().toList();
    }

    public FurnaceBuilder addTypes(Type... types) {
        this.types.addAll(List.of(types));
        return this;
    }

    public Ingredient getInput() {
        return input;
    }

    public float getXp() {
        return xp;
    }

    public FurnaceBuilder setXp(float xp) {
        this.xp = xp;
        return this;
    }

    public int getTimeTicks() {
        return timeTicks;
    }

    public int getTimeSeconds() {
        return timeTicks / 20;
    }

    public FurnaceBuilder setTimeTicks(int ticks) {
        this.timeTicks = ticks;
        return this;
    }

    public FurnaceBuilder setTimeSeconds(double seconds) {
        this.timeTicks = (int) Math.round(seconds * 20);
        return this;
    }

    @Override
    public @Nullable List<CookingRecipe<?>> getRecipe() {
        List<CookingRecipe<?>> recipes = new ArrayList<>();

        for (Type type : types) {
            CookingRecipe<?> r = type.getRecipe(this);
            recipes.add(r);
        }

        return recipes;
    }

    @Override
    public void registerRecipe() {
        List<CookingRecipe<?>> recipes = getRecipe();
        if (recipes == null || recipes.isEmpty()) return;
        recipes.forEach(Bukkit::addRecipe);
    }

    @Override
    public boolean shouldReturn(ItemStack itemStack) {
        return false;
    }

    public enum Type {
        NORMAL {
            @Override
            public CookingRecipe<?> getRecipe(FurnaceBuilder builder) {
                return new FurnaceRecipe(
                        builder.getKey("_normal"),
                        builder.getResult(),
                        builder.getInput().getChoice(),
                        builder.getXp(),
                        builder.getTimeTicks()
                );
            }
        },
        BLAST {
            @Override
            public CookingRecipe<?> getRecipe(FurnaceBuilder builder) {
                return new BlastingRecipe(
                        builder.getKey("_blast"),
                        builder.getResult(),
                        builder.getInput().getChoice(),
                        builder.getXp() / 2,
                        builder.getTimeTicks()
                );
            }
        },
        SMOKE {
            @Override
            public CookingRecipe<?> getRecipe(FurnaceBuilder builder) {
                return new SmokingRecipe(
                        builder.getKey("_smoke"),
                        builder.getResult(),
                        builder.getInput().getChoice(),
                        builder.getXp() / 2,
                        builder.getTimeTicks()
                );
            }
        };

        public abstract CookingRecipe<?> getRecipe(FurnaceBuilder recipeBuilder);
    }
}
