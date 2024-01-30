package me.youm.morota.networking;

import me.youm.morota.Morota;
import me.youm.morota.networking.packets.ClientMorotaEnergyPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

/**
 * @author YouM
 * Created on 2024/1/25
 */
public class Networking {
    private static SimpleChannel INSTANCE;
    private static int packetID = 0;

    private static int id() {
        return packetID++;
    }

    public static void register() {
        SimpleChannel channel = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Morota.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "0.1")
                .clientAcceptedVersions(version -> true)
                .serverAcceptedVersions(version -> true)
                .simpleChannel();
        INSTANCE = channel;

        channel.messageBuilder(ClientMorotaEnergyPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ClientMorotaEnergyPacket::new)
                .encoder(ClientMorotaEnergyPacket::toBytes)
                .consumer(ClientMorotaEnergyPacket::handler)
                .add();
    }
    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }
    public static <MSG> void sendToClient(MSG message, ServerPlayer serverPlayer){
        INSTANCE.send(PacketDistributor.PLAYER.with(()->serverPlayer),message);
    }
}
