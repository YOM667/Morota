package me.youm.morota.world.register;

import me.youm.morota.Morota;
import me.youm.morota.world.block.SynthesizerBlock;
import me.youm.morota.world.register.item.MorotaCreativeTab;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * @author YouM
 * Created on 2024/1/25
 */
public class BlockRegister {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Morota.MOD_ID);
    public static final RegistryObject<Block> MOROTA_SYNTHESIZER = registerBlock("morota_synthesizer",
            ()-> new SynthesizerBlock(BlockBehaviour.Properties
                    .of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(30.0f,1200.0f)),
            MorotaCreativeTab.TAB
    );
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> object = BLOCKS.register(name,block);
        registerBlockItem(name,object,tab);
        return object;
    }
    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        ItemRegister.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

}
