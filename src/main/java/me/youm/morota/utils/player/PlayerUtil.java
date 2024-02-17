package me.youm.morota.utils.player;

import me.youm.morota.world.player.capability.MorotaEntityEnergyCapability;
import me.youm.morota.world.player.capability.MorotaEntityEnergyCapabilityProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;


/**
 * @author YouM
 * Created on 2024/2/6
 */
public class PlayerUtil {
    public static MorotaEntityEnergyCapability getMorotaEntityEnergyCapability(Entity entity){
        return entity.getCapability(MorotaEntityEnergyCapabilityProvider.MOROTA_ENTITY_ENERGY_CAPABILITY).orElse(new MorotaEntityEnergyCapability());
    }
}
