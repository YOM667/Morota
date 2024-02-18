package me.youm.morota.world.entity;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.List;
import java.util.Optional;

/**
 * @author YouM
 * Created on 2024/1/31
 *
 * extend {@link net.minecraft.world.entity.LightningBolt}, purpose is: <br/>
 * implement when lightning bolt struck not spawn fire {@link #canSpawnFire} and not attack owner {@link #attackOwner}
 */
public class MorotaLightningBolt extends LightningBolt {
    private boolean canSpawnFire = true;
    private LazyOptional<Entity> attackOwner = LazyOptional.empty();
    public MorotaLightningBolt(EntityType<? extends LightningBolt> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void spawnFire(int pExtraIgnitions) {
        if(canSpawnFire) super.spawnFire(pExtraIgnitions);
    }

    @Override
    public void tick() {
        if (this.life == 2) {
            if (this.level.isClientSide()) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, 10000.0F, 0.8F + this.random.nextFloat() * 0.2F, false);
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.WEATHER, 2.0F, 0.5F + this.random.nextFloat() * 0.2F, false);
            } else {
                Difficulty difficulty = this.level.getDifficulty();
                if (difficulty == Difficulty.NORMAL || difficulty == Difficulty.HARD) {
                    this.spawnFire(4);
                }

                this.powerLightningRod();
                clearCopperOnLightningStrike(this.level, this.getStrikePosition());
                this.gameEvent(GameEvent.LIGHTNING_STRIKE);
            }
        }

        --this.life;
        if (this.life < 0) {
            if (this.flashes == 0) {
                if (this.level instanceof ServerLevel) {
                    List<Entity> list = this.level.getEntities(this, new AABB(this.getX() - 15.0D, this.getY() - 15.0D, this.getZ() - 15.0D, this.getX() + 15.0D, this.getY() + 6.0D + 15.0D, this.getZ() + 15.0D), (p_147140_) -> p_147140_.isAlive() && !this.hitEntities.contains(p_147140_));

                    for(ServerPlayer serverplayer : ((ServerLevel)this.level).getPlayers((p_147157_) -> p_147157_.distanceTo(this) < 256.0F)) {
                        CriteriaTriggers.LIGHTNING_STRIKE.trigger(serverplayer, this, list);
                    }
                }

                this.discard();
            } else if (this.life < -this.random.nextInt(10)) {
                --this.flashes;
                this.life = 1;
                this.seed = this.random.nextLong();
                this.spawnFire(0);
            }
        }

        if (this.life >= 0) {
            if (!(this.level instanceof ServerLevel)) {
                this.level.setSkyFlashTime(2);
            } else if (!this.visualOnly) {
                List<Entity> list1 = this.level.getEntities(this, new AABB(this.getX() - 3.0D, this.getY() - 3.0D, this.getZ() - 3.0D, this.getX() + 3.0D, this.getY() + 6.0D + 3.0D, this.getZ() + 3.0D), Entity::isAlive);

                for(Entity entity : list1) {
                    if (!ForgeEventFactory.onEntityStruckByLightning(entity, this)) {
                        Optional<Entity> resolve = attackOwner.resolve();
                        if(resolve.isPresent() && resolve.get().is(entity)){
                            continue;
                        }
                        entity.thunderHit((ServerLevel) this.level, this);
                    }
                }

                this.hitEntities.addAll(list1);
                if (this.getCause() != null) {
                    CriteriaTriggers.CHANNELED_LIGHTNING.trigger(this.getCause(), list1);
                }
            }
        }
    }

    private void powerLightningRod() {
        BlockPos blockpos = this.getStrikePosition();
        BlockState blockstate = this.level.getBlockState(blockpos);
        if (blockstate.is(Blocks.LIGHTNING_ROD)) {
            ((LightningRodBlock)blockstate.getBlock()).onLightningStrike(blockstate, this.level, blockpos);
        }
    }

    private BlockPos getStrikePosition() {
        Vec3 vec3 = this.position();
        return new BlockPos(vec3.x, vec3.y - 1.0E-6D, vec3.z);
    }
    public boolean isCanSpawnFire() {
        return canSpawnFire;
    }

    public void setCanSpawnFire(boolean canSpawnFire) {
        this.canSpawnFire = canSpawnFire;
    }
    public void setOwner(Entity entity){
        this.attackOwner = LazyOptional.of(() -> entity);
    }
}
