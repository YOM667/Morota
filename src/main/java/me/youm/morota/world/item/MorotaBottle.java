package me.youm.morota.world.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

/**
 * @author YouM
 * Created on 2024/2/2
 */
public class MorotaBottle extends Item {
    public MorotaBottle(Properties pProperties) {
        super(pProperties);
    }
    public int getUseDuration(ItemStack pStack) {
        return 16;
    }
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
    }
}
