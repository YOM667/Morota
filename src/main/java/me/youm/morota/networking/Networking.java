package me.youm.morota.networking;

import me.youm.morota.Morota;
import me.youm.morota.networking.packets.ClientMorotaItemEnergyPacket;
import me.youm.morota.networking.packets.ServerMorotaEnergySyncPacket;
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
    public static final String VERSION = "0.1";
    private static int packetID = 0;

    private static int nextID() {
        return packetID++;
    }

    public static void register() {
        SimpleChannel channel = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Morota.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "0.1")
                .clientAcceptedVersions(version -> version.equals(VERSION))
                .serverAcceptedVersions(version -> version.equals(VERSION))
                .simpleChannel();
        INSTANCE = channel;

        channel.messageBuilder(ClientMorotaItemEnergyPacket.class, nextID(), NetworkDirection.PLAY_TO_SERVER)
                .encoder(ClientMorotaItemEnergyPacket::toBytes)
                .decoder(ClientMorotaItemEnergyPacket::new)
                .consumer(ClientMorotaItemEnergyPacket::handle)
                .add();
        channel.messageBuilder(ServerMorotaEnergySyncPacket.class, nextID(), NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ServerMorotaEnergySyncPacket::toBytes)
                .decoder(ServerMorotaEnergySyncPacket::new)
                .consumer(ServerMorotaEnergySyncPacket::handle)
                .add();
    }
    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }
    public static <MSG> void sendToClient(MSG message, ServerPlayer serverPlayer){
        INSTANCE.send(PacketDistributor.PLAYER.with(()->serverPlayer),message);
    }
}
