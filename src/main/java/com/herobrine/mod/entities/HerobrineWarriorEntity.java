package com.herobrine.mod.entities;

import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.entities.EntityRegistry;
import com.herobrine.mod.util.items.ItemList;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeBlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

public class HerobrineWarriorEntity extends AbstractHerobrineEntity{
    private int blockBreakCounter = 100;

    public HerobrineWarriorEntity(EntityType<? extends HerobrineWarriorEntity> type, World worldIn) {
        super(type, worldIn);
        experienceValue = 5;
    }

    public HerobrineWarriorEntity(World worldIn) {
        this(EntityRegistry.HEROBRINE_WARRIOR_ENTITY, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.6D, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractIllagerEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractSurvivorEntity.class, false));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, GolemEntity.class, false));
        this.targetSelector.addGoal(6, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 0.4D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, AbstractIllagerEntity.class, 8.0F));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, AbstractSurvivorEntity.class, 8.0F));
        this.goalSelector.addGoal(11, new LookAtGoal(this, GolemEntity.class, 8.0F));
        this.goalSelector.addGoal(12, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
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

    private boolean unableToAttackTarget() {
        return this.getNavigator().noPath() && !this.canAttack(Objects.requireNonNull(this.getAttackTarget()), EntityPredicate.DEFAULT.setCustomPredicate(LivingEntity::attackable));
    }

    @Override
    protected void updateAITasks() {
        if (this.blockBreakCounter > 0) {
            --this.blockBreakCounter;
            if (this.blockBreakCounter == 0 && Config.COMMON.WarriorBreaksBlocks.get() && this.isAggressive() && this.unableToAttackTarget() && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
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
                            IForgeBlockState state = this.world.getBlockState(blockpos);
                            if (!blockstate.isFoliage(world, blockpos) && blockstate.getMaterial() != Material.FIRE && !state.isAir(world, blockpos) && blockstate.getMaterial() != Material.WATER && blockstate.getMaterial() != Material.LAVA && !BlockTags.WITHER_IMMUNE.contains(blockstate.getBlock()) && !BlockTags.DRAGON_IMMUNE.contains(blockstate.getBlock()) && !BlockTags.BEDS.contains(blockstate.getBlock()) && !BlockTags.CARPETS.contains(blockstate.getBlock()) && !BlockTags.BUTTONS.contains(blockstate.getBlock()) && !BlockTags.WOODEN_BUTTONS.contains(blockstate.getBlock()) && !BlockTags.CORAL_PLANTS.contains(blockstate.getBlock()) && !BlockTags.CORALS.contains(blockstate.getBlock()) && !BlockTags.FLOWER_POTS.contains(blockstate.getBlock()) && !BlockTags.RAILS.contains(blockstate.getBlock()) && !BlockTags.SAPLINGS.contains(blockstate.getBlock()) && !BlockTags.SMALL_FLOWERS.contains(blockstate.getBlock()) && !BlockTags.SIGNS.contains(blockstate.getBlock()) && !BlockTags.STANDING_SIGNS.contains(blockstate.getBlock()) && !BlockTags.UNDERWATER_BONEMEALS.contains(blockstate.getBlock()) && !BlockTags.WALL_CORALS.contains(blockstate.getBlock()) && !BlockTags.WALL_SIGNS.contains(blockstate.getBlock())  && !BlockTags.WOODEN_PRESSURE_PLATES.contains(blockstate.getBlock()) && net.minecraftforge.event.ForgeEventFactory.onEntityDestroyBlock(this, blockpos, blockstate)) {
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
        if (this.blockBreakCounter < 1 || this.blockBreakCounter > 500) {
            this.blockBreakCounter = 500;
        }
        super.livingTick();
    }

    @Override
    public ILivingEntityData onInitialSpawn(@NotNull IWorld worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(ItemList.bedrock_sword));
        if(!Config.COMMON.BedrockSwordDrops.get()) {
            this.inventoryHandsDropChances[EquipmentSlotType.MAINHAND.getIndex()] = 0.0F;
        }
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }
}