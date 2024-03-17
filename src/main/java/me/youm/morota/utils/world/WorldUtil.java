package me.youm.morota.utils.world;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import me.youm.morota.world.entity.MorotaLightningBolt;
import me.youm.morota.world.register.EntityRegister;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;

/**
 * @author YouM
 * Created on 2024/1/30
 */
public class WorldUtil {
    public static void createLightingBolt(Level level, Player player, Vec3 location, int damage){
        MorotaLightningBolt lightingBolt = EntityRegister.MOROTA_LIGHTNING_BOLT.get().create(level);
        assert lightingBolt != null;
        lightingBolt.moveTo(Vec3.atBottomCenterOf(new Vec3i(location.x,location.y,location.z)));
        lightingBolt.setVisualOnly(false);
        lightingBolt.setDamage(damage);
        lightingBolt.setCanSpawnFire(false);
        lightingBolt.setOwner(player);
        level.addFreshEntity(lightingBolt);
    }
    public static void addVillagerTradingRecipes(
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trade,
            HashMap<ItemStack, ItemStack> map,
            int villagerLevel
    ) {
        map.forEach((currency, commodity) -> {
            trade.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    currency,
                    commodity, 10,8,0.02F));
        });
    }

}
