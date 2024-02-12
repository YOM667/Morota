package me.youm.morota.networking.packets;

import me.youm.morota.networking.IPacket;
import me.youm.morota.networking.Networking;
import me.youm.morota.utils.player.PlayerUtil;
import me.youm.morota.world.player.capability.MorotaItemEnergyCapability;
import me.youm.morota.world.player.capability.MorotaItemEnergyCapabilityProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author YouM
 * Created on 2024/1/25
 */

public class ClientMorotaItemEnergyPacket implements IPacket {
    private final ItemStack itemStack;
    private final int consumption;

    public ClientMorotaItemEnergyPacket(ItemStack itemStack, int consumption) {
        this.itemStack = itemStack;
        this.consumption = consumption;
    }

    public ClientMorotaItemEnergyPacket(FriendlyByteBuf byteBuf) {
        this.itemStack = byteBuf.readItem();
        this.consumption = byteBuf.readInt();
    }

    @Override
    public void toBytes(FriendlyByteBuf byteBuf) {
        byteBuf.writeItem(this.itemStack);
        byteBuf.writeInt(this.consumption);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                log.debug("sender: " + player.getName().getString() + " | " + player.getStringUUID());
                ItemStack playerUseItem = player.getItemInHand(player.getUsedItemHand());
                playerUseItem.getCapability(MorotaItemEnergyCapabilityProvider.MOROTA_ITEM_ENERGY_CAPABILITY).ifPresent(itemCapability -> {
                    log.debug("get item capability data: " + itemCapability.getMorotaEnergy());
                    if (playerUseItem.is(itemStack.getItem())) {
                        MorotaItemEnergyCapability useItemCapability = playerUseItem.getCapability(MorotaItemEnergyCapabilityProvider.MOROTA_ITEM_ENERGY_CAPABILITY).resolve().get();
                        useItemCapability.setMorotaEnergy(itemCapability.getMorotaEnergy() - consumption);
                        log.debug("sync item data");
                    }else{
                        log.debug("not same item");
                    }
                });
                PlayerUtil.getMorotaEntityEnergyCapability(player).addEnergyData(consumption);
                Networking.sendToClient(new ServerMorotaEnergySyncPacket(PlayerUtil.getMorotaEntityEnergyCapability(player).getMorotaEnergy()),player);
                log.debug("sync player data");
                log.debug("player has been add energy in server, current energy: " + PlayerUtil.getMorotaEntityEnergyCapability(player).getMorotaEnergy());
            }else{
                log.warn("player is not exist,please resend packet");
            }
        });
        context.setPacketHandled(true);
    }
}
