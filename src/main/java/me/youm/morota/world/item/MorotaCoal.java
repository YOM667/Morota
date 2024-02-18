package me.youm.morota.world.item;

import me.youm.morota.networking.Networking;
import me.youm.morota.networking.packets.ClientMorotaEnergyPacket;
import me.youm.morota.utils.math.RandomUtil;
import me.youm.morota.utils.player.PlayerUtil;
import me.youm.morota.world.player.capability.MorotaEntityEnergyCapability;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * @author YouM
 * Created on 2024/1/27
 */
public class MorotaCoal extends Item {
    public MorotaCoal(Properties properties) {
        super(properties);
    }

    /**
     * when a player uses this item consume the item 10 energy to give player random energy between 3 and 6;
     * @param level the world level
     * @param player use the coal's player
     * @param hand use the coal's hand
     * @return the item result
     */
    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        // add use cool-down 40 ticks
        player.getCooldowns().addCooldown(this, 40);
        MorotaEntityEnergyCapability morotaCapability = PlayerUtil.getMorotaCapability(player);
        if (!morotaCapability.isMaxEnergy()) {
            // send the packet to give the player random energy
            Networking.sendToServer(new ClientMorotaEnergyPacket(morotaCapability.getMorotaEnergy() + RandomUtil.getRandomInRange(3,6)));
            // consume the item uses
            this.setDamage(itemstack, itemstack.getDamageValue() + 10);
            if(itemstack.getDamageValue() >= itemstack.getMaxDamage()){
                // destroy the item
                itemstack.shrink(1);
                player.awardStat(Stats.ITEM_BROKEN.get(itemstack.getItem()));
            }
            // play sound
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        }
        // award the player
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    /**
     * the method is unuseful
     * @return item can hurt
     */
    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        return true;
    }

}
