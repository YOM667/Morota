package me.youm.morota.world.item;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

/**
 * @author YouM
 * Created on 2024/1/29
 */
public class MorotaSword extends SwordItem {
    public MorotaSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }
}
