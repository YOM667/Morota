package me.youm.morota.world.player.capability;

import net.minecraft.nbt.CompoundTag;

/**
 * @author YouM
 * Created on 2024/1/26
 */
public class MorotaEnergyCapability {
    private int morotaEnergy = 0;
    private static final int MAX_ENERGY = 100;
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
    public void cloneCapability(MorotaEnergyCapability source){
        this.morotaEnergy = source.getMorotaEnergy();
        this.deserializeNBTData(source.serializeNBTData());
    }
    public boolean isMaxEnergy(){
        return this.morotaEnergy == MAX_ENERGY;
    }
    public CompoundTag serializeNBTData(){
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("morotaEnergy",this.morotaEnergy);
        return nbt;
    }
    public void deserializeNBTData(CompoundTag nbt){
        this.morotaEnergy = nbt.getInt("morotaEnergy");
    }
}
