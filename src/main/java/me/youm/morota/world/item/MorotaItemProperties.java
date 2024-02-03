package me.youm.morota.world.item;

import me.youm.morota.Morota;
import me.youm.morota.world.register.ItemRegister;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

/**
 * @author YouM
 * Created on 2024/2/3
 */
public class MorotaItemProperties {
    public static void customItemTexture(){
        ItemProperties.register(
                ItemRegister.MOROTA_BOTTLE.get(),
                new ResourceLocation(Morota.MOD_ID,"energy"),
                (itemStack, clientLevel, livingEntity, pSeed)->{
                    if(itemStack.hasTag()){
                        return itemStack.getTag().getInt("energy");
                    }else{
                        return 0;
                    }
                });
    }
}
