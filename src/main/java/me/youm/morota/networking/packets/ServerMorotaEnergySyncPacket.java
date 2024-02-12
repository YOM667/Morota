package me.youm.morota.networking.packets;

import me.youm.morota.networking.IPacket;
import me.youm.morota.world.player.capability.MorotaEntityEnergyCapabilityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author YouM
 * Created on 2024/2/11
 */
public class ServerMorotaEnergySyncPacket implements IPacket {
    private int data;
    public ServerMorotaEnergySyncPacket(int data) {
        this.data = data;
    }

    public ServerMorotaEnergySyncPacket(FriendlyByteBuf byteBuf) {
        this.data = byteBuf.readInt();
    }

    @Override
    public void toBytes(FriendlyByteBuf byteBuf) {
        byteBuf.writeInt(data);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->{
            Minecraft.getInstance().player.getCapability(MorotaEntityEnergyCapabilityProvider.MOROTA_ENTITY_ENERGY_CAPABILITY).ifPresent(capability->{
                capability.setMorotaEnergy(data);
            });
        });

    }
}
