package me.youm.morota.networking;

import com.mojang.logging.LogUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.slf4j.Logger;

import java.util.function.Supplier;

public interface IPacket {
    Logger log = LogUtils.getLogger();
    void toBytes(FriendlyByteBuf buf);

    void handle(Supplier<NetworkEvent.Context> context);
}
