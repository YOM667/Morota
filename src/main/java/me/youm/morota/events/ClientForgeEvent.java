package me.youm.morota.events;

import me.youm.morota.Morota;
import me.youm.morota.client.key.KeyBindings;
import me.youm.morota.networking.Networking;
import me.youm.morota.networking.packets.ClientMorotaEnergyPacket;
import me.youm.morota.utils.player.PlayerUtil;
import me.youm.morota.world.item.MorotaBottle;
import me.youm.morota.world.player.capability.MorotaEntityEnergyCapability;
import me.youm.morota.world.player.capability.MorotaEntityEnergyCapabilityProvider;
import me.youm.morota.world.player.capability.MorotaItemEnergyCapabilityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
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
            if(!event.getObject().getCapability(MorotaEntityEnergyCapabilityProvider.MOROTA_ENTITY_ENERGY_CAPABILITY).isPresent()) {
                event.addCapability(new ResourceLocation(Morota.MOD_ID, "properties"), new MorotaEntityEnergyCapabilityProvider());
            }
        }
    }
    @SubscribeEvent
    public static void onAttachItemStackCapabilityEvent(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() instanceof MorotaBottle) {
            event.addCapability(new ResourceLocation(Morota.MOD_ID, "item_energy"), new MorotaItemEnergyCapabilityProvider());
        }
    }
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event){
        event.getOriginal().reviveCaps();
        LazyOptional<MorotaEntityEnergyCapability> oldCapability = event.getOriginal().getCapability(MorotaEntityEnergyCapabilityProvider.MOROTA_ENTITY_ENERGY_CAPABILITY);
        LazyOptional<MorotaEntityEnergyCapability> newCapability = event.getPlayer().getCapability(MorotaEntityEnergyCapabilityProvider.MOROTA_ENTITY_ENERGY_CAPABILITY);
        if (oldCapability.isPresent() && newCapability.isPresent()) {
            oldCapability.ifPresent(old ->
                newCapability.ifPresent(current -> current.cloneCapability(old))
            );
        }
        event.getOriginal().invalidateCaps();
    }
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
        event.register(MorotaEntityEnergyCapability.class);
    }
    @SubscribeEvent
    public static void registerHUD(final RenderGameOverlayEvent event){
        Morota.rendererManager.load(event);
    }
    @SubscribeEvent
    public static void reigsterKey(InputEvent.KeyInputEvent event){
        if (KeyBindings.SPECIAL_ATTACK.consumeClick()) {
            MorotaEntityEnergyCapability capability = PlayerUtil.getMorotaCapability(Minecraft.getInstance().player);
            if (capability.isMaxEnergy()) {
                Networking.sendToServer(new ClientMorotaEnergyPacket(0));
            }
        }
    }
}
