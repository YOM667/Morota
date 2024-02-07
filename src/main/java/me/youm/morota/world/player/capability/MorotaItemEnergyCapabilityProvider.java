package me.youm.morota.world.player.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MorotaItemEnergyCapabilityProvider implements ICapabilityProvider, NonNullSupplier<MorotaItemEnergyCapability>, ICapabilitySerializable<CompoundTag> {
    public static Capability<MorotaItemEnergyCapability> MOROTA_ITEM_ENERGY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    private final MorotaItemEnergyCapability capability;

    public MorotaItemEnergyCapabilityProvider() {
        this.capability = new MorotaItemEnergyCapability();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == MOROTA_ITEM_ENERGY_CAPABILITY ? LazyOptional.of(this).cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.capability.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        capability.deserializeNBT(nbt);
    }


    @NotNull
    @Override
    public MorotaItemEnergyCapability get() {
        return capability;
    }
}