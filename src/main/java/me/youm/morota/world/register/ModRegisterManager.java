package me.youm.morota.world.register;

import me.youm.morota.Morota;
import me.youm.morota.client.key.KeyBindings;
import me.youm.morota.client.renderer.hud.HUDRendererManager;
import me.youm.morota.client.screen.SynthesizerScreen;
import me.youm.morota.networking.Networking;
import me.youm.morota.utils.render.FontLoader;
import me.youm.morota.world.register.item.MorotaItemProperties;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author YouM
 * Created on 2024/1/25
 */
public class ModRegisterManager {
    /**
     * registers all items, blocks, entities, block entities, and menus
     * @param eventBus event bus
     */
    public void registerAll(IEventBus eventBus){
        ItemRegister.ITEMS.register(eventBus);
        BlockRegister.BLOCKS.register(eventBus);
        EntityRegister.ENTITY_TYPE.register(eventBus);
        EntityRegister.BLOCK_ENTITY_TYPE.register(eventBus);
        MenuRegister.MENU_TYPE.register(eventBus);
    }

    /**
     * asynchronously registers keybindings
     * and load custom item textures
     */
    public void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(()->{
            MorotaItemProperties.customItemTexture();
            ClientRegistry.registerKeyBinding(KeyBindings.SPECIAL_ATTACK);
            MenuScreens.register(MenuRegister.SYNTHESIZER_MENU.get(), SynthesizerScreen::new);
        });
    }
    /**
     * asynchronously initializes fontLoader and rendererManager,
     * logs setup message, and registers networking
     * @param event Common Setup
     */
    public void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(()->{
            Morota.fontLoader = new FontLoader();
            Morota.rendererManager = new HUDRendererManager();
            Networking.register();
            Morota.LOGGER.info("MOD SETUP...");
        });
    }
    public static final CreativeModeTab TAB = new CreativeModeTab(Morota.MOD_ID) {

        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ItemRegister.MOROTA_COAL.get());
        }
    };
}
