package me.youm.morota.world.item;

import me.youm.morota.networking.Networking;
import me.youm.morota.networking.packets.ClientMorotaEnergyPacket;
import me.youm.morota.utils.Util;
import me.youm.morota.utils.render.ColorUtil;
import me.youm.morota.utils.world.WorldUtil;
import me.youm.morota.world.player.capability.MorotaEntityEnergyCapabilityProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author YouM
 * Created on 2024/1/29
 */
public class MorotaSword extends SwordItem {
    public MorotaSword(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    /**
     * when a player uses this item consume energy 10 to release three lightning bolts in front of the player
     * @param level the world level
     * @param player use the sword's player
     * @param hand use the sword's hand
     * @return the item result
     */
    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);

        AtomicBoolean energyEnough = new AtomicBoolean(true);
        player.getCapability(MorotaEntityEnergyCapabilityProvider.MOROTA_ENTITY_ENERGY_CAPABILITY).ifPresent(capability -> {
            if (capability.getMorotaEnergy() >= 10) {
                capability.subEnergyData(10);
                if(level.isClientSide) {
                    //send the packet to handle the energy
                    Networking.sendToServer(new ClientMorotaEnergyPacket(capability.getMorotaEnergy()));
                }
                energyEnough.set(true);
            } else {
                energyEnough.set(false);
            }
        });
        if (!energyEnough.get()) return InteractionResultHolder.fail(item);
        // get player look angle
        Vec3 lookAngle = player.getLookAngle();
        // move player to the front of 4 blocks
        player.moveTo(player.getX() + lookAngle.x * 4f, player.getY(), player.getZ() + lookAngle.z * 4f);
        // any lighting bolt padding
        float padding = 3F;
        Util.repeat(3, index -> {
            // index default is 0
            index += 1;
            // set lighting bolt location
            Vec3 lightingLocation = new Vec3(
                    player.getX() + lookAngle.x * index * padding,
                    player.getY() - player.fallDistance,
                    player.getZ() + lookAngle.z * index * padding
            );
            // create lightning bolt, damage 50
            WorldUtil.createLightingBolt(level, player, lightingLocation, 50);
        });
        player.getCooldowns().addCooldown(this, 30);
        return InteractionResultHolder.success(item);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        tooltipComponents.add(new TranslatableComponent("tip.morota_sword.1")
                .setStyle(Style.EMPTY.withBold(true)
                        .withItalic(true)
                        .withColor(TextColor.fromRgb(ColorUtil.THEME.getRGB()))
                ));
        tooltipComponents.add(new TranslatableComponent("tip.morota_sword.2")
                .setStyle(Style.EMPTY.withUnderlined(true)
                        .withColor(ChatFormatting.RED)
                ));
        super.appendHoverText(stack, level, tooltipComponents, tooltipFlag);
    }

}
