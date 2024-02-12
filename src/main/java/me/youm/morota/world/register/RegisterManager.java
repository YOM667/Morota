package me.youm.morota.world.register;

import net.minecraftforge.eventbus.api.IEventBus;

/**
 * @author YouM
 * Created on 2024/1/25
 */
public class RegisterManager {
    public void registerAll(IEventBus eventBus){
        ItemRegister.ITEMS.register(eventBus);
        BlockRegister.BLOCKS.register(eventBus);
        EntityRegister.ENTITY_TYPE.register(eventBus);

    }
}
