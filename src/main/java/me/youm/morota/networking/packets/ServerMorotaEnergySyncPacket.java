package me.youm.morota.networking.packets;

import me.youm.morota.networking.IPacket;
import me.youm.morota.utils.player.PlayerUtil;
import me.youm.morota.world.player.capability.MorotaEntityEnergyCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author YouM
 * Created on 2024/2/11
 * sync morota energy to local player capability
 */
public class ServerMorotaEnergySyncPacket implements IPacket {
    private final int data;
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
            assert Minecraft.getInstance().player != null;
            MorotaEntityEnergyCapability morotaCapability = PlayerUtil.getMorotaCapability(Minecraft.getInstance().player);
            morotaCapability.setMorotaEnergy(data);
        });
        context.setPacketHandled(true);
    }
}
