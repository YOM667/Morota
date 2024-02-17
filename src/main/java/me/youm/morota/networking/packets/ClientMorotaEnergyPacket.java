package me.youm.morota.networking.packets;

import me.youm.morota.networking.IPacket;
import me.youm.morota.networking.Networking;
import me.youm.morota.world.player.capability.MorotaEntityEnergyCapabilityProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author YouM
 * Created on 2024/1/25
 */
public class ClientMorotaEnergyPacket implements IPacket {
    private int energy;
    public ClientMorotaEnergyPacket(int energy) {
        this.energy = energy;
    }

    public ClientMorotaEnergyPacket(FriendlyByteBuf byteBuf) {
        this.energy = byteBuf.readInt();
    }

    public void toBytes(FriendlyByteBuf byteBuf) {
        byteBuf.writeInt(energy);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            context.getSender().getCapability(MorotaEntityEnergyCapabilityProvider.MOROTA_ENTITY_ENERGY_CAPABILITY).ifPresent(capability -> {
                capability.setMorotaEnergy(energy);
                Networking.sendToClient(new ServerMorotaEnergySyncPacket(capability.getMorotaEnergy()), context.getSender());
            });
        });
        context.setPacketHandled(true);
    }

}
