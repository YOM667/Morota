package me.youm.morota.world.entity;

import me.youm.morota.Morota;
import me.youm.morota.client.screen.SynthesizerMenu;
import me.youm.morota.utils.Util;
import me.youm.morota.world.register.EntityRegister;
import me.youm.morota.world.register.ItemRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author YouM
 * Created on 2024/1/25
 */
public class SynthesizerBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(4){
        @Override
        protected void onContentsChanged(int slot) {
            SynthesizerBlockEntity.this.setChanged();
        }
    };
    private final ContainerData data;
    private int progress;
    private int maxProgress = 100;
    private LazyOptional<ItemStackHandler> itemHandlerOptional = LazyOptional.empty();
    public SynthesizerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(EntityRegister.MOROTA_SYNTHESIZER_BLOCK.get(), pPos, pBlockState);
        data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> SynthesizerBlockEntity.this.progress;
                    case 1 -> SynthesizerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> SynthesizerBlockEntity.this.progress = pValue;
                    case 1 -> SynthesizerBlockEntity.this.maxProgress = pValue;
                    default -> {
                        Morota.LOGGER.error("ERROR in MorotaSynthesizerBlockEntity: index not exist");
                    }
                };
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }
    @NotNull
    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.morota.morota_synthesizer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
        return new SynthesizerMenu(pContainerId,pPlayerInventory,this,this.data);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return itemHandlerOptional.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        itemHandlerOptional = LazyOptional.of(()->itemStackHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandlerOptional.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory",itemStackHandler.serializeNBT());
        nbt.putInt("morota_synthesizer.progress",this.progress);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        itemStackHandler.deserializeNBT(nbt.getCompound("inventory"));
        this.progress = nbt.getInt("morota_synthesizer.progress");
        super.load(nbt);
    }
    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, SynthesizerBlockEntity entity){
        if(level.isClientSide) return;
        if(canCraft(entity)){
            entity.progress++;
            setChanged(level,blockPos,blockState);
            if(entity.progress >= entity.maxProgress){
                entity.itemStackHandler.extractItem(1,1,false);
                entity.itemStackHandler.setStackInSlot(4, new ItemStack(ItemRegister.MOROTA_BOTTLE.get(), 1));
                entity.progress = 0;
            }
        }else{
            entity.progress = 0;
            setChanged(level,blockPos,blockState);
        }
    }

    private static boolean canCraft(SynthesizerBlockEntity entity) {
        SimpleContainer container = new SimpleContainer();
        Util.repeat(entity.itemStackHandler.getSlots(),
                slot -> container.setItem(slot, entity.itemStackHandler.getStackInSlot(slot))
        );
        return checkSameItem(entity)
                && canInsertItem(container,ItemRegister.MOROTA_BOTTLE.get().getDefaultInstance())
                && canInsertCount(container);
    }
    public static boolean checkSameItem(SynthesizerBlockEntity entity){
        return entity.itemStackHandler.getStackInSlot(1).sameItem(Items.GLASS_BOTTLE.getDefaultInstance()) &&
                entity.itemStackHandler.getStackInSlot(2).sameItem(Items.LAPIS_LAZULI.getDefaultInstance()) &&
                entity.itemStackHandler.getStackInSlot(3).sameItem(ItemRegister.MOROTA_COAL.get().getDefaultInstance());
    }
    private static boolean canInsertCount(SimpleContainer container){
        return container.getItem(4).getMaxStackSize() > container.getItem(4).getMaxStackSize();
    }
    private static boolean canInsertItem(SimpleContainer container,ItemStack stack){
        return container.getItem(4).sameItem(stack) || container.getItem(4).isEmpty();
    }

    public void dropItems(){
        SimpleContainer container = new SimpleContainer(itemStackHandler.getSlots());
        for (int index = 0; index < itemStackHandler.getSlots(); index++) {
            container.setItem(index,itemStackHandler.getStackInSlot(index));
        }
        Containers.dropContents(this.level,this.worldPosition,container);
    }

}
