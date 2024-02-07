package me.youm.morota.world.register.item;

import me.youm.morota.Morota;
import me.youm.morota.world.register.ItemRegister;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * @author YouM
 * Created on 2024/1/24
 */
public class MorotaCreativeTab {
    public static final CreativeModeTab TAB = new CreativeModeTab(Morota.MOD_ID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ItemRegister.MOROTA_SWORD.get());
        }
    };
}
