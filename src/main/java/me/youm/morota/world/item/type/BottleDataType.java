package me.youm.morota.world.item.type;

/**
 * @author YouM
 * Created on 2024/2/6
 */
public enum BottleDataType {
    BARE(0,0),
    LITTLE(10,10),
    SEVERAL(30,20),
    MIDDLE(50,20),
    SOME(70,20),
    MANY(90,20),
    FULL(100,10);
    public final int data;
    public final int consumption;
    BottleDataType(int data,int consumption){
        this.data = data;
        this.consumption = consumption;
    }
}
