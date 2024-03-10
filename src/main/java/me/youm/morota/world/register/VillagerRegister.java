package me.youm.morota.world.register;

import com.google.common.collect.ImmutableSet;
import me.youm.morota.Morota;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author YouM
 * Created on 2024/3/10
 */
public class VillagerRegister {
    public static final DeferredRegister<PoiType> POI_TYPE = DeferredRegister.create(ForgeRegistries.POI_TYPES, Morota.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, Morota.MOD_ID);

    public static final RegistryObject<PoiType> MOROTA_BLOCK_POT = POI_TYPE.register("morota_block_poi", ()->
        new PoiType("morota_poi",ImmutableSet.copyOf(BlockRegister.MOROTA_SYNTHESIZER.get().getStateDefinition().getPossibleStates()), 1, 1)
    );
    public static final RegistryObject<VillagerProfession> MOROTA_MASTER = VILLAGER_PROFESSIONS.register("morota_master", ()->
        new VillagerProfession("morota_master", MOROTA_BLOCK_POT.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_ARMORER)
    );

}
