package me.youm.morota.events;

import me.youm.morota.Morota;
import me.youm.morota.world.player.capability.MorotaEnergyCapability;
import me.youm.morota.world.player.capability.MorotaEnergyCapabilityProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


/**
 * @author YouM
 * Created on 2024/1/25
 */
@Mod.EventBusSubscriber(modid = Morota.MOD_ID,value = Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientForgeEvent {
    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof Player){
            if(!event.getObject().getCapability(MorotaEnergyCapabilityProvider.MOROTA_ENERGY_CAPABILITY).isPresent()) {
                event.addCapability(new ResourceLocation(Morota.MOD_ID, "properties"), new MorotaEnergyCapabilityProvider());
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event){
        event.getOriginal().reviveCaps();
        LazyOptional<MorotaEnergyCapability> oldCapability = event.getOriginal().getCapability(MorotaEnergyCapabilityProvider.MOROTA_ENERGY_CAPABILITY);
        LazyOptional<MorotaEnergyCapability> newCapability = event.getPlayer().getCapability(MorotaEnergyCapabilityProvider.MOROTA_ENERGY_CAPABILITY);
        if (oldCapability.isPresent() && newCapability.isPresent()) {
            oldCapability.ifPresent(old ->
                newCapability.ifPresent(current -> current.cloneCapability(old))
            );
        }
        event.getOriginal().invalidateCaps();
    }
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
        event.register(MorotaEnergyCapability.class);
    }
}
