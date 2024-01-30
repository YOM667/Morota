package me.youm.morota.world.register;

import me.youm.morota.Morota;
import me.youm.morota.world.item.MorotaCoal;
import me.youm.morota.world.item.MorotaCreativeTab;
import me.youm.morota.world.item.MorotaSword;
import me.youm.morota.world.item.MorotaTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author YouM
 * Created on 2024/1/24
 */
public class ItemRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Morota.MOD_ID);
    public static final RegistryObject<Item> MOROTA_SWORD = ITEMS.register("morota_sword",
            () -> new MorotaSword(
                    MorotaTiers.MOROTA,
                    4,
                    -2.8f,
                    new Item.Properties().tab(MorotaCreativeTab.TAB)
                            .rarity(Rarity.EPIC)
            )
    );
    public static final RegistryObject<Item> MOROTA_COAL = ITEMS.register("morota_coal",
            () -> new MorotaCoal(
                    new Item.Properties().tab(MorotaCreativeTab.TAB)
                    .defaultDurability(100)
                    .rarity(Rarity.UNCOMMON)
            )
    );
}