package me.youm.morota;

import com.mojang.logging.LogUtils;
import me.youm.morota.client.key.KeyBindings;
import me.youm.morota.client.renderer.hud.HUDRendererManager;
import me.youm.morota.client.screen.SynthesizerScreen;
import me.youm.morota.networking.Networking;
import me.youm.morota.sever.command.CommandManager;
import me.youm.morota.utils.render.FontLoader;
import me.youm.morota.world.register.MenuRegister;
import me.youm.morota.world.register.RegisterManager;
import me.youm.morota.world.register.item.MorotaItemProperties;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Morota.MOD_ID)
public class Morota {
    //Mod id
    public static final String MOD_ID = "morota";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    //manage mod command
    public static CommandManager commandManager;
    //manage mod register
    public static RegisterManager registerManager;
    //manage hud renderer
    public static HUDRendererManager rendererManager;
    //manage font loading
    public static FontLoader fontLoader;
    public Morota() {
        // initializes all managers
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        registerManager = new RegisterManager();
        registerManager.registerAll(modEventBus);
        commandManager = new CommandManager();
        // Register the setup method for mod loading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);

    }

    /**
     * asynchronously initializes fontLoader and rendererManager,
     * logs setup message, and registers networking
     * @param event Common Setup
     */
    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(()->{
            fontLoader = new FontLoader();
            rendererManager = new HUDRendererManager();
            LOGGER.info("MOD SETUP...");
            Networking.register();
        });
    }

    /**
     * asynchronously registers keybinds
     * and load custom item textures
     * @param event Client Setup
     */
    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(()->{
            MorotaItemProperties.customItemTexture();
            ClientRegistry.registerKeyBinding(KeyBindings.SPECIAL_ATTACK);
            MenuScreens.register(MenuRegister.SYNTHESIZER_MENU.get(), SynthesizerScreen::new);
        });
    }

}
