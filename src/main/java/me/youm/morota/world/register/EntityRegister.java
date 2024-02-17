package me.youm.morota.world.register;

import me.youm.morota.Morota;
import me.youm.morota.world.entity.MorotaLightningBolt;
import me.youm.morota.world.entity.SynthesizerBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author YouM
 * Created on 2024/1/31
 */
public class EntityRegister {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITIES, Morota.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Morota.MOD_ID);
    public static final RegistryObject<EntityType<MorotaLightningBolt>> MOROTA_LIGHTNING_BOLT = ENTITY_TYPE.register("morota_lightning_bolt",()->
            EntityType.Builder.of(MorotaLightningBolt::new, MobCategory.MISC)
                    .sized(0.0F, 0.0F)
                    .clientTrackingRange(16)
                    .build(new ResourceLocation(Morota.MOD_ID,"morota_lightning_bolt").toString())
    );
    public static final RegistryObject<BlockEntityType<SynthesizerBlockEntity>> MOROTA_SYNTHESIZER_BLOCK = BLOCK_ENTITY_TYPE.register("morota_synthesizer",()->
            BlockEntityType.Builder.of(SynthesizerBlockEntity::new, BlockRegister.MOROTA_SYNTHESIZER.get())
                    .build(null)
    );
}
