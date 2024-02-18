package me.youm.morota.networking.packets;

import me.youm.morota.networking.IPacket;
import me.youm.morota.networking.Networking;
import me.youm.morota.utils.player.PlayerUtil;
import me.youm.morota.world.player.capability.MorotaEntityEnergyCapability;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
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
            ServerPlayer player = context.getSender();
            assert player != null;
            MorotaEntityEnergyCapability morotaCapability = PlayerUtil.getMorotaCapability(player);
            morotaCapability.setMorotaEnergy(energy);
            // sync the energy to the sender's client
            Networking.sendToClient(new ServerMorotaEnergySyncPacket(morotaCapability.getMorotaEnergy()), player);
        });
        context.setPacketHandled(true);
    }

}
