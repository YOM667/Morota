package me.youm.morota.events;

import me.youm.morota.Morota;
import me.youm.morota.sever.command.CommandManager;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

/**
 * @author YouM
 * Created on 2024/2/3
 */
@Mod.EventBusSubscriber(modid = Morota.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonForgeEvent {
    @SubscribeEvent
    public static void registerCommand(final RegisterCommandsEvent event){
        CommandManager.load(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
    }
}
