package me.youm.morota.events;

import me.youm.morota.Morota;
import me.youm.morota.client.key.KeyBindings;
import me.youm.morota.client.renderer.entity.MorotaLightningBoltRenderer;
import me.youm.morota.networking.Networking;
import me.youm.morota.networking.packets.ClientMorotaEnergyPacket;
import me.youm.morota.utils.player.PlayerUtil;
import me.youm.morota.world.player.capability.MorotaEntityEnergyCapability;
import me.youm.morota.world.register.EntityRegister;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author YouM
 * Created on 2024/1/31
 */
@Mod.EventBusSubscriber(modid = Morota.MOD_ID,value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvent {
    @SubscribeEvent
    public static void registerRenderer(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegister.MOROTA_LIGHTNING_BOLT.get(), MorotaLightningBoltRenderer::new);
    }

}
