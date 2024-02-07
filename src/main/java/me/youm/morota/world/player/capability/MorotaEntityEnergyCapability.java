package me.youm.morota.world.player.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * @author YouM
 * Created on 2024/1/26
 */
public class MorotaEntityEnergyCapability implements INBTSerializable<CompoundTag> {
    private int morotaEnergy = 0;
    private static final int MAX_ENERGY = 100000;
    public int getMorotaEnergy() {
        return morotaEnergy;
    }
    public void addEnergyData(int data){
        this.morotaEnergy = Math.min(this.morotaEnergy + data,MAX_ENERGY);
    }
    public void subEnergyData(int data){
        this.morotaEnergy = Math.min(this.morotaEnergy - data,MAX_ENERGY);
    }
    public void setMorotaEnergy(int morotaEnergy) {
        this.morotaEnergy = morotaEnergy;
    }
    public void cloneCapability(MorotaEntityEnergyCapability source){
        this.morotaEnergy = source.getMorotaEnergy();
        this.deserializeNBT(source.serializeNBT());
    }
    public boolean isMaxEnergy(){
        return this.morotaEnergy == MAX_ENERGY;
    }
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("morota_energy",this.morotaEnergy);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.morotaEnergy = nbt.getInt("morota_energy");
    }
}
