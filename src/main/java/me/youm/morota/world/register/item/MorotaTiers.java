package me.youm.morota.world.register.item;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

/**
 * @author YouM
 * Created on 2024/1/24
 */
public enum MorotaTiers implements Tier {
    MOROTA(0, 10000, 20.0F, 10.0F, 25, Ingredient::of);
    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    private MorotaTiers(int pLevel, int pUses, float pSpeed, float pDamage, int pEnchantmentValue, Supplier<Ingredient> pRepairIngredient) {
        this.level = pLevel;
        this.uses = pUses;
        this.speed = pSpeed;
        this.damage = pDamage;
        this.enchantmentValue = pEnchantmentValue;
        this.repairIngredient = new LazyLoadedValue<>(pRepairIngredient);
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
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    private static TagKey<Item> bind(String pName) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(pName));
    }

}
