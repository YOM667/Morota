package me.youm.morota.world.player.capability;

import me.youm.morota.world.player.PredicateTransformer;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * @author YouM
 * Created on 2024/2/4
 */
public class MorotaItemEnergyCapability implements INBTSerializable<CompoundTag>, PredicateTransformer {
    private int energy = 100;
    public int getMorotaEnergy() {
        return this.energy;
    }
    public void setMorotaEnergy(int energy) {
        this.energy = energy;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("item_energy",energy);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.energy = nbt.getInt("item_energy");
    }

    @Override
    public float transform() {
        return this.energy / 100.0f;
    }
}
