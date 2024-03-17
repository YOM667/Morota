package me.youm.morota.events;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import me.youm.morota.Morota;
import me.youm.morota.sever.command.CommandManager;
import me.youm.morota.utils.world.WorldUtil;
import me.youm.morota.world.register.ItemRegister;
import me.youm.morota.world.register.VillagerRegister;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.HashMap;
import java.util.List;

/**
 * @author YouM
 * Created on 2024/2/3
 */
@Mod.EventBusSubscriber(modid = Morota.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonForgeEvent {
    @SubscribeEvent
    public static void registerCommand(final RegisterCommandsEvent event){
        CommandManager.load(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
    }
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if(event.getType() == VillagerProfession.TOOLSMITH) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ItemRegister.MOROTA_COAL.get(), 1);
            int villagerLevel = 1;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    stack,10,8,0.02F));
        }

        if(event.getType() == VillagerRegister.MOROTA_MASTER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            int villagerLevel = 1;
            HashMap<ItemStack, ItemStack> map = new HashMap<>() {{
                put(new ItemStack(Items.DIAMOND, 3), new ItemStack(ItemRegister.MOROTA_COAL.get(), 8));
                put(new ItemStack(Items.EMERALD, 4), new ItemStack(ItemRegister.MOROTA_BOTTLE.get(), 1));
            }};
            WorldUtil.addVillagerTradingRecipes(trades,map,villagerLevel);

        }

    }
}
