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
    public MorotaBottle(Properties properties) {
        super(properties);
    }
    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 24;
    }
    @NotNull
    @Override
    public UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.DRINK;
    }
    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level,@NotNull Player player,@NotNull InteractionHand hand) {
        ItemStack bottleStack = player.getItemInHand(hand);
        AtomicReference<InteractionResultHolder<ItemStack>> resultHolder = new AtomicReference<>();
        bottleStack.getCapability(MorotaItemEnergyCapabilityProvider.MOROTA_ITEM_ENERGY_CAPABILITY).ifPresent(capability-> {
            if(capability.getMorotaEnergy() > 0){
                resultHolder.set(ItemUtils.startUsingInstantly(level, player, hand));
            }else{
                resultHolder.set(InteractionResultHolder.fail(bottleStack));
            }
        });
        return resultHolder.get();
    }
    @NotNull
    @Override
    public ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
        if(!level.isClientSide){
            return stack;
        }
        stack.getCapability(MorotaItemEnergyCapabilityProvider.MOROTA_ITEM_ENERGY_CAPABILITY).ifPresent(capability-> {
            int energy = capability.getMorotaEnergy();
            for (BottleDataType dataType : BottleDataType.values()) {
                if (energy == dataType.data) {
                    Networking.sendToServer(new ClientMorotaItemEnergyPacket(stack,dataType.consumption));
                }
            }
        });
        level.gameEvent(livingEntity, GameEvent.DRINKING_FINISH, livingEntity.eyeBlockPosition());
        return stack;
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, @NotNull TooltipFlag tooltipFlag) {
        AtomicInteger energy = new AtomicInteger();
        stack.getCapability(MorotaItemEnergyCapabilityProvider.MOROTA_ITEM_ENERGY_CAPABILITY).ifPresent(capability -> {
            energy.set(capability.getMorotaEnergy());
        });
        components.add(new TextComponent("energy: " + energy));
        super.appendHoverText(stack,level,components,tooltipFlag);
    }

}
