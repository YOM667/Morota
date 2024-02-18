package me.youm.morota.networking;

import com.mojang.logging.LogUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.slf4j.Logger;

import java.util.function.Supplier;

public interface IPacket {
    Logger log = LogUtils.getLogger();

    /**
     * write packet field to the networking
     * @param buf byte buffer
     */
    void toBytes(FriendlyByteBuf buf);

    /**
     * handle the packet in the network
     * @param context the context
     */
    void handle(Supplier<NetworkEvent.Context> context);
}
