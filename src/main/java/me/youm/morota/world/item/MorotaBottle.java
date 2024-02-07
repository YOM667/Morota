package me.youm.morota.world.item;

import me.youm.morota.networking.Networking;
import me.youm.morota.networking.packets.ClientMorotaItemEnergyPacket;
import me.youm.morota.world.item.type.BottleDataType;
import me.youm.morota.world.player.capability.MorotaItemEnergyCapabilityProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author YouM
 * Created on 2024/2/2
 */
public class MorotaBottle extends Item {
    public MorotaBottle(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 24;
    }
    @NotNull
    @Override
    public UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return UseAnim.DRINK;
    }
    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level pLevel,@NotNull Player pPlayer,@NotNull InteractionHand pHand) {
        ItemStack bottleStack = pPlayer.getItemInHand(pHand);
        AtomicReference<InteractionResultHolder<ItemStack>> resultHolder = new AtomicReference<>();
        bottleStack.getCapability(MorotaItemEnergyCapabilityProvider.MOROTA_ITEM_ENERGY_CAPABILITY).ifPresent(capability-> {
            if(capability.getMorotaEnergy() > 0){
                resultHolder.set(ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand));
            }else{
                resultHolder.set(InteractionResultHolder.pass(bottleStack));
            }
        });
        return resultHolder.get();
    }
    @NotNull
    @Override
    public ItemStack finishUsingItem(ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pEntityLiving) {
        if(!pLevel.isClientSide){
            return pStack;
        }
        pStack.getCapability(MorotaItemEnergyCapabilityProvider.MOROTA_ITEM_ENERGY_CAPABILITY).ifPresent(capability-> {
            int energy = capability.getMorotaEnergy();
            for (BottleDataType dataType : BottleDataType.values()) {
                if (energy == dataType.data) {
                    Networking.sendToServer(new ClientMorotaItemEnergyPacket(pStack,dataType.consumption));
                }
            }
        });
        pLevel.gameEvent(pEntityLiving, GameEvent.DRINKING_FINISH, pEntityLiving.eyeBlockPosition());
        return pStack;
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        AtomicInteger energy = new AtomicInteger();
        pStack.getCapability(MorotaItemEnergyCapabilityProvider.MOROTA_ITEM_ENERGY_CAPABILITY).ifPresent(capability -> {
            energy.set(capability.getMorotaEnergy());
        });
        pTooltipComponents.add(new TextComponent("energy: " + energy));
        super.appendHoverText(pStack,pLevel,pTooltipComponents,pIsAdvanced);
    }

}
