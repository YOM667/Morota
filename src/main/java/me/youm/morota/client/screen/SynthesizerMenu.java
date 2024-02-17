package me.youm.morota.client.screen;

import me.youm.morota.world.entity.SynthesizerBlockEntity;
import me.youm.morota.world.register.BlockRegister;
import me.youm.morota.world.register.MenuRegister;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;


/**
 * @author YouM
 * Created on 2024/2/16
 */
public class SynthesizerMenu extends AbstractContainerMenu {
    public final SynthesizerBlockEntity entity;
    private Level level;
    private ContainerData data;
    public SynthesizerMenu(int containerId, @NotNull Inventory playerInventory, @NotNull FriendlyByteBuf byteBuf) {
        this(containerId,playerInventory,playerInventory.player.level.getBlockEntity(byteBuf.readBlockPos()),new SimpleContainerData(2));
    }
    public SynthesizerMenu(int containerId, @NotNull Inventory playerInventory, @NotNull BlockEntity entity, ContainerData data) {
        super(MenuRegister.SYNTHESIZER_MENU.get(),containerId);
        this.data = data;
        this.entity = (SynthesizerBlockEntity) entity;
        this.level = playerInventory.player.level;
        this.addPlayerInventoryAndHotBar(playerInventory);
        this.entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler,0,12,15));
            this.addSlot(new SlotItemHandler(handler,1,24,15));
            this.addSlot(new SlotItemHandler(handler,2,18,26));
            this.addSlot(new SlotItemHandler(handler,3,50,22));
        });
        addDataSlots(data);
    }
    public boolean isCrafting(){
        return data.get(0) > 0;
    }
    public int getScaledProgress(){
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 24;
        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return super.quickMoveStack(pPlayer, pIndex);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level,entity.getBlockPos()),player, BlockRegister.MOROTA_SYNTHESIZER.get());
    }
    private void addPlayerInventoryAndHotBar(Inventory playerInventory){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
