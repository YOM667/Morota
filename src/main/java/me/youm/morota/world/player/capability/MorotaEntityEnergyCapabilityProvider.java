package me.youm.morota.world.player.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author YouM
 * Created on 2024/1/26
 */
public class MorotaEntityEnergyCapabilityProvider implements ICapabilityProvider, NonNullSupplier<MorotaEntityEnergyCapability>, INBTSerializable<CompoundTag> {
    public static Capability<MorotaEntityEnergyCapability> MOROTA_ENTITY_ENERGY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    private final MorotaEntityEnergyCapability morotaEnergyCapability = new MorotaEntityEnergyCapability();

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == MOROTA_ENTITY_ENERGY_CAPABILITY ? LazyOptional.of(this).cast() : LazyOptional.empty();

    }

    @Override
    public CompoundTag serializeNBT() {
        return this.morotaEnergyCapability.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.morotaEnergyCapability.deserializeNBT(nbt);
    }

    @NotNull
    @Override
    public MorotaEntityEnergyCapability get() {
        return this.morotaEnergyCapability;
    }
}
