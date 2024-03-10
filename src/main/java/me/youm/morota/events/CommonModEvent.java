package me.youm.morota.events;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import me.youm.morota.Morota;
import me.youm.morota.world.register.ItemRegister;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

/**
 * @author YouM
 * Created on 2024/2/11
 */
@Mod.EventBusSubscriber(modid = Morota.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvent {
    public static void onCustomTrades(VillagerTradesEvent event){
        if (event.getType() == VillagerProfession.TOOLSMITH) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack itemStack = new ItemStack(ItemRegister.MOROTA_COAL.get(), 1);
            int villagerLevel = 1;
            trades.get(villagerLevel).add((trader,rand)->
                new MerchantOffer(new ItemStack(Items.COAL,2),
                        itemStack,10,2,0.2f
            ));
        }
    }
}
