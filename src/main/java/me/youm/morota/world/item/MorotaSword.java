package me.youm.morota.world.item;

import me.youm.morota.utils.Utils;
import me.youm.morota.utils.world.WorldUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * @author YouM
 * Created on 2024/1/29
 */
public class MorotaSword extends SwordItem {
    public MorotaSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide) {
            return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
        }
        Vec3 lookAngle = pPlayer.getLookAngle();
        pPlayer.moveTo(pPlayer.getX() + lookAngle.x * 4f,pPlayer.getY(),pPlayer.getZ() + lookAngle.z * 4f);
        Utils.repeat(3, index -> {
            Vec3 lightingLocation = new Vec3(pPlayer.getX() + lookAngle.x * index * 3f,pPlayer.getY() - pPlayer.fallDistance,pPlayer.getZ() + lookAngle.z * index * 3f);
            WorldUtil.createLightingBolt(pLevel,pPlayer,lightingLocation,50);
        });
        return super.use(pLevel, pPlayer, pUsedHand);

    }
}
