package me.youm.morota.world.register;

import me.youm.morota.Morota;
import me.youm.morota.world.item.MorotaBottle;
import me.youm.morota.world.item.MorotaCoal;
import me.youm.morota.world.item.MorotaSword;
import me.youm.morota.world.register.item.MorotaTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author YouM
 * Created on 2024/1/24
 */
@SuppressWarnings("all")
public class ItemRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Morota.MOD_ID);
    public static final RegistryObject<Item> MOROTA_SWORD = ITEMS.register("morota_sword",
            () -> new MorotaSword(
                    MorotaTiers.MOROTA,
                    4,
                    -2.8f,
                    new Item.Properties().tab(ModRegisterManager.TAB)
                            .rarity(Rarity.EPIC)
            )
    );
    public static final RegistryObject<Item> MOROTA_COAL = ITEMS.register("morota_coal",
            () -> new MorotaCoal(
                    new Item.Properties().tab(ModRegisterManager.TAB)
                            .defaultDurability(50)
                            .rarity(Rarity.UNCOMMON)
            )
    );
    public static final RegistryObject<Item> MOROTA_BOTTLE = ITEMS.register("morota_bottle",
            () -> new MorotaBottle(
                    new Item.Properties().stacksTo(1).tab(ModRegisterManager.TAB)
            )
    );


}
