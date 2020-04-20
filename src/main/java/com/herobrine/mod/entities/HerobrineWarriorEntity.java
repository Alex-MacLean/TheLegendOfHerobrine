package com.herobrine.mod.entities;

import com.herobrine.mod.util.entities.EntityRegistry;
import com.herobrine.mod.util.items.ItemList;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class HerobrineWarriorEntity extends MonsterEntity{
    private int blockBreakCounter;

    public HerobrineWarriorEntity(EntityType<? extends HerobrineWarriorEntity> type, World worldIn) {
        super(type, worldIn);
        experienceValue = 5;
    }

    public HerobrineWarriorEntity(World worldIn) {
        this((EntityType<? extends HerobrineWarriorEntity>) EntityRegistry.HEROBRINE_WARRIOR_ENTITY, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.6D, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, GolemEntity.class, true));
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.4D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(7, new LookAtGoal(this, GolemEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).setBaseValue(3.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof AreaEffectCloudEntity)
            return false;
        if (source.getImmediateSource() instanceof PotionEntity)
            return false;
        if (source.getImmediateSource() instanceof UnholyWaterEntity)
            return false;
        if (source == DamageSource.FALL)
            return false;
        if (source == DamageSource.CACTUS)
            return false;
        if (source == DamageSource.DROWN)
            return false;
        if (source == DamageSource.LIGHTNING_BOLT)
            return false;
        if (source == DamageSource.IN_FIRE)
            return false;
        if (source == DamageSource.ON_FIRE)
            return false;
        if (source == DamageSource.ANVIL)
            return false;
        if (source == DamageSource.CRAMMING)
            return false;
        if (source == DamageSource.DRAGON_BREATH)
            return false;
        if (source == DamageSource.DRYOUT)
            return false;
        if (source == DamageSource.FALLING_BLOCK)
            return false;
        if (source == DamageSource.FIREWORKS)
            return false;
        if (source == DamageSource.FLY_INTO_WALL)
            return false;
        if (source == DamageSource.HOT_FLOOR)
            return false;
        if (source == DamageSource.LAVA)
            return false;
        if (source == DamageSource.IN_WALL)
            return false;
        if (source == DamageSource.MAGIC)
            return false;
        if (source == DamageSource.STARVE)
            return false;
        if (source == DamageSource.SWEET_BERRY_BUSH)
            return false;
        if (source == DamageSource.WITHER)
            return false;
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void writeAdditional(@NotNull CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("DestroyCooldown", this.blockBreakCounter);
    }

    @Override
    public void readAdditional(@NotNull CompoundNBT compound) {
        super.readAdditional(compound);
        this.blockBreakCounter = compound.getInt("DestroyCooldown");
    }

    private boolean canReachTarget() {
        return !this.getNavigator().noPath();
    }

    @Override
    protected void updateAITasks() {
        if (this.blockBreakCounter > 0) {
            --this.blockBreakCounter;
            if (this.blockBreakCounter == 0 && this.isAggressive() && this.canReachTarget() && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
                int i1 = MathHelper.floor(this.posY);
                int l1 = MathHelper.floor(this.posX);
                int i2 = MathHelper.floor(this.posZ);
                boolean flag1 = false;

                for(int k2 = -1; k2 <= 1; ++k2) {
                    for(int l2 = -1; l2 <= 1; ++l2) {
                        for(int j = 0; j <= 2; ++j) {
                            int i3 = l1 + k2;
                            int k = i1 + j;
                            int l = i2 + l2;
                            BlockPos blockpos = new BlockPos(i3, k, l);
                            BlockState blockstate = this.world.getBlockState(blockpos);
                            if (!blockstate.isFoliage(world, blockpos) && blockstate.getMaterial() != Material.FIRE && !blockstate.isAir() && !BlockTags.WITHER_IMMUNE.contains(blockstate.getBlock()) && !BlockTags.DRAGON_IMMUNE.contains(blockstate.getBlock()) && !BlockTags.BEDS.contains(blockstate.getBlock()) && !BlockTags.CARPETS.contains(blockstate.getBlock()) && !BlockTags.BUTTONS.contains(blockstate.getBlock()) && !BlockTags.WOODEN_BUTTONS.contains(blockstate.getBlock()) && !BlockTags.CORAL_PLANTS.contains(blockstate.getBlock()) && !BlockTags.CORALS.contains(blockstate.getBlock()) && !BlockTags.FLOWER_POTS.contains(blockstate.getBlock()) && !BlockTags.RAILS.contains(blockstate.getBlock()) && !BlockTags.SAPLINGS.contains(blockstate.getBlock()) && !BlockTags.SMALL_FLOWERS.contains(blockstate.getBlock()) && !BlockTags.SIGNS.contains(blockstate.getBlock()) && !BlockTags.STANDING_SIGNS.contains(blockstate.getBlock()) && !BlockTags.UNDERWATER_BONEMEALS.contains(blockstate.getBlock()) && !BlockTags.WALL_CORALS.contains(blockstate.getBlock()) && !BlockTags.WALL_SIGNS.contains(blockstate.getBlock()) && !BlockTags.WOODEN_PRESSURE_PLATES.contains(blockstate.getBlock())
                                    && net.minecraftforge.event.ForgeEventFactory.onEntityDestroyBlock(this, blockpos, blockstate)) {
                                flag1 = this.world.destroyBlock(blockpos, true) || flag1;
                                this.swingArm(Hand.MAIN_HAND);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void livingTick() {
        if (this.blockBreakCounter <= 0) {
            this.blockBreakCounter = 100;
        }
        if (this.blockBreakCounter > 100) {
            this.blockBreakCounter = 100;
        }
        super.livingTick();
    }

    @Override
    public boolean attackEntityAsMob(@NotNull Entity entityIn) {
        boolean flag = super.attackEntityAsMob(entityIn);
        if (flag) {
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
            if (this.isBurning() && this.rand.nextFloat() < f * 0.3F) {
                entityIn.setFire(2 * (int)f);
            }
        }
        return flag;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        this.clearActivePotions();
    }

    @Override
    public ILivingEntityData onInitialSpawn(@NotNull IWorld worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        Variables.WorldVariables.get(world).syncData(world);
        if ((!(Variables.WorldVariables.get(world).Spawn))) {
            this.remove();
        }
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(ItemList.bedrock_sword));
        this.inventoryHandsDropChances[EquipmentSlotType.MAINHAND.getIndex()] = 0.0F;
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }
}