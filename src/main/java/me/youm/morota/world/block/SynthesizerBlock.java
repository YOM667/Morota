package me.youm.morota.world.block;

import me.youm.morota.Morota;
import me.youm.morota.world.entity.SynthesizerBlockEntity;
import me.youm.morota.world.register.EntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

/**
 * @author YouM
 * Created on 2024/1/25
 */
public class SynthesizerBlock extends BaseEntityBlock {
    public SynthesizerBlock(Properties pProperties) {
        super(pProperties);
    }
    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof SynthesizerBlockEntity synthesizer){
                NetworkHooks.openGui((ServerPlayer) pPlayer, synthesizer, pPos);
            }else{
                Morota.LOGGER.error("not open gui");
            }
        }
        return super.use(pState,pLevel,pPos,pPlayer,pHand,pHit);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SynthesizerBlockEntity(pos,state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newBlockState, boolean isMoving) {
        if(state.getBlock() != newBlockState.getBlock()){
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(blockEntity instanceof SynthesizerBlockEntity entity){
                entity.dropItems();
            }
        }
        super.onRemove(state, level, pos, newBlockState, isMoving);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, EntityRegister.MOROTA_SYNTHESIZER_BLOCK.get(), SynthesizerBlockEntity::serverTick);
    }

    @Override
    @SuppressWarnings("deprecation")
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

}
