package me.youm.morota.networking.packets;

import me.youm.morota.utils.math.RandomUtil;
import me.youm.morota.world.player.capability.MorotaEnergyCapabilityProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author YouM
 * Created on 2024/1/25
 */
public class ClientMorotaEnergyPacket {
    public ClientMorotaEnergyPacket() {

    }
    public ClientMorotaEnergyPacket(FriendlyByteBuf byteBuf) {
    }
    public void toBytes(FriendlyByteBuf byteBuf){

    }
    public void handler(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->{
            context.getSender().getCapability(MorotaEnergyCapabilityProvider.MOROTA_ENERGY_CAPABILITY).ifPresent(capability->{
                capability.addEnergyData(RandomUtil.getRandomInRange(3,6));
            });
        });
        context.setPacketHandled(true);
    }

}
