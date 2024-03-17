package me.youm.morota.world.register.item;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * @author YouM
 * Created on 2024/1/24
 */
@SuppressWarnings("deprecation")
public enum MorotaTiers implements Tier {
    MOROTA(0, 10000, 20.0F, 12.0F, 25, Ingredient::of);
    // mined level
    private final int level;
    // max uses
    private final int uses;
    // mining speed
    private final float speed;
    // attack damage bonus
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    MorotaTiers(int level, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }
    @Override
    public int getUses() {
        return this.uses;
    }
    @Override
    public float getSpeed() {
        return this.speed;
    }
    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }
    @Override
    public int getLevel() {
        return this.level;
    }
    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }
    @Override
    @NotNull
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
