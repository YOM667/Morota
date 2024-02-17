package me.youm.morota.world.item;

import me.youm.morota.networking.Networking;
import me.youm.morota.networking.packets.ClientMorotaEnergyPacket;
import me.youm.morota.utils.math.RandomUtil;
import me.youm.morota.world.player.capability.MorotaEntityEnergyCapabilityProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author YouM
 * Created on 2024/1/27
 */
public class MorotaCoal extends Item {
    public MorotaCoal(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        pPlayer.getCooldowns().addCooldown(this, 40);

        pPlayer.getCapability(MorotaEntityEnergyCapabilityProvider.MOROTA_ENTITY_ENERGY_CAPABILITY).ifPresent(capability -> {
            if (!capability.isMaxEnergy()) {
                Networking.sendToServer(new ClientMorotaEnergyPacket(capability.getMorotaEnergy() + RandomUtil.getRandomInRange(3,6)));
                this.setDamage(itemstack, itemstack.getDamageValue() + 10);
                pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            }
        });
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(new TextComponent("damage: " + (pStack.getMaxDamage() - pStack.getDamageValue())));
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        return true;
    }

}
