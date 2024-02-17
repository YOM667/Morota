package me.youm.morota.utils.world;

import me.youm.morota.world.entity.MorotaLightningBolt;
import me.youm.morota.world.register.EntityRegister;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

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
        lightingBolt.setOrder(player);
        level.addFreshEntity(lightingBolt);
    }
    public static void specialAttack(Player player){
        Vec3 position = player.position();

    }
}
