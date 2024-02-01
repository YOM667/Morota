package me.youm.morota;

import com.mojang.logging.LogUtils;
import me.youm.morota.networking.Networking;
import me.youm.morota.world.register.RegisterManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Morota.MOD_ID)
public class Morota
{
    //Mod id
    public static final String MOD_ID = "morota";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public RegisterManager registerManager;
    public Morota() {
        this.registerManager = new RegisterManager();
        // Register the setup method for mod loading
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
        registerManager.registerAll(modEventBus);
    }
    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("MOD SETUP...");
        //register networking
        Networking.register();
    }

}
