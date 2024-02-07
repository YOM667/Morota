package me.youm.morota.world.register.item;

import me.youm.morota.Morota;
import me.youm.morota.world.player.capability.MorotaItemEnergyCapability;
import me.youm.morota.world.player.capability.MorotaItemEnergyCapabilityProvider;
import me.youm.morota.world.register.ItemRegister;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;

/**
 * @author YouM
 * Created on 2024/2/3
 */
public class MorotaItemProperties {
    public static void customItemTexture(){
        ItemProperties.register(ItemRegister.MOROTA_BOTTLE.get(), new ResourceLocation(Morota.MOD_ID, "item_energy"),
                (ClampedItemPropertyFunction) (itemStack, clientLevel, livingEntity, seed) -> {
                    LazyOptional<MorotaItemEnergyCapability> capabilityLazyOptional = itemStack.getCapability(MorotaItemEnergyCapabilityProvider.MOROTA_ITEM_ENERGY_CAPABILITY);
                    if (capabilityLazyOptional.isPresent() && capabilityLazyOptional.resolve().isPresent()) {
                        return capabilityLazyOptional.resolve().get().transform();
                    }else{
                        return 0.0f;
                    }
                });
    }
}
