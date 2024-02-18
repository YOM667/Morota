package me.youm.morota;

import com.mojang.logging.LogUtils;
import me.youm.morota.client.renderer.hud.HUDRendererManager;
import me.youm.morota.utils.render.FontLoader;
import me.youm.morota.world.register.ModRegisterManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Morota.MOD_ID)
public class Morota {
    //Mod id
    public static final String MOD_ID = "morota";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    //manage mod register
    public static ModRegisterManager registerManager;
    //manage hud renderer
    public static HUDRendererManager rendererManager;
    //manage font loading
    public static FontLoader fontLoader;
    public Morota() {
        // initializes all managers
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        registerManager = new ModRegisterManager();
        registerManager.registerAll(modEventBus);
        // Register the setup method for mod loading
        modEventBus.addListener(registerManager::clientSetup);
        modEventBus.addListener(registerManager::commonSetup);
        // listens to this forge events
        MinecraftForge.EVENT_BUS.register(this);
    }

}
