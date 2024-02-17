package me.youm.morota.world.register;

import me.youm.morota.Morota;
import me.youm.morota.client.screen.SynthesizerMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author YouM
 * Created on 2024/2/16
 */
public class MenuRegister {
    public static final DeferredRegister<MenuType<?>> MENU_TYPE = DeferredRegister.create(ForgeRegistries.CONTAINERS, Morota.MOD_ID);
    public static final RegistryObject<MenuType<SynthesizerMenu>> SYNTHESIZER_MENU =
            registerMenu("energy_storage_menu", SynthesizerMenu::new);
    public static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenu(String name, IContainerFactory<T> containerFactory) {
        return MENU_TYPE.register(name,()-> IForgeMenuType.create(containerFactory));
    }
}
