package com.herobrine.mod.entities;

import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.entities.EntityRegistry;
import com.herobrine.mod.util.items.ItemList;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeBlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class HerobrineWarriorEntity extends AbstractHerobrineEntity{
    private int blockBreakCounter = 100;

    public HerobrineWarriorEntity(EntityType<? extends HerobrineWarriorEntity> type, World worldIn) {
        super(type, worldIn);
        xpReward = 5;
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

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.6D);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("DestroyCooldown", this.blockBreakCounter);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.blockBreakCounter = compound.getInt("DestroyCooldown");
    }

    private boolean unableToAttackTarget() {
        if (this.getTarget() != null) {
            return this.getNavigation().isDone() && !this.canAttack(this.getTarget(), EntityPredicate.DEFAULT.selector(LivingEntity::attackable));
        }
        return false;
    }

    @Override
    public void aiStep() {
        if (this.blockBreakCounter < 1 || this.blockBreakCounter > 500) {
            this.blockBreakCounter = 500;
        }
        --this.blockBreakCounter;
        if (this.blockBreakCounter < 1 && Config.COMMON.WarriorBreaksBlocks.get() && this.isAggressive() && this.unableToAttackTarget() && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this)) {
            int l1 = MathHelper.floor(this.getX());
            int i1 = MathHelper.floor(this.getY());
            int i2 = MathHelper.floor(this.getZ());
            boolean flag1 = false;
            for (int k2 = -1; k2 <= 1; ++k2) {
                for (int l2 = -1; l2 <= 1; ++l2) {
                    for (int j = 0; j <= 2; ++j) {
                        int i3 = l1 + k2;
                        int k = i1 + j;
                        int l = i2 + l2;
                        BlockPos blockpos = new BlockPos(i3, k, l);
                        BlockState blockstate = this.level.getBlockState(blockpos);
                        IForgeBlockState state = this.level.getBlockState(blockpos);
                        if (blockstate.getMaterial() != Material.FIRE && !state.isAir(level, blockpos) && !blockstate.canBeReplaced(Fluids.EMPTY) && !blockstate.canBeReplaced(Fluids.WATER) && !blockstate.canBeReplaced(Fluids.LAVA) && !blockstate.canBeReplaced(Fluids.FLOWING_LAVA) && !blockstate.canBeReplaced(Fluids.FLOWING_WATER) && !BlockTags.WITHER_IMMUNE.contains(blockstate.getBlock()) && !BlockTags.DRAGON_IMMUNE.contains(blockstate.getBlock()) && !BlockTags.BEDS.contains(blockstate.getBlock()) && !BlockTags.CROPS.contains(blockstate.getBlock()) && !BlockTags.CARPETS.contains(blockstate.getBlock()) && !BlockTags.BUTTONS.contains(blockstate.getBlock()) && !BlockTags.WOODEN_BUTTONS.contains(blockstate.getBlock()) && !BlockTags.CORAL_PLANTS.contains(blockstate.getBlock()) && !BlockTags.CORALS.contains(blockstate.getBlock()) && !BlockTags.FLOWER_POTS.contains(blockstate.getBlock()) && !BlockTags.PORTALS.contains(blockstate.getBlock()) && !BlockTags.RAILS.contains(blockstate.getBlock()) && !BlockTags.SAPLINGS.contains(blockstate.getBlock()) && !BlockTags.SMALL_FLOWERS.contains(blockstate.getBlock()) && !BlockTags.SIGNS.contains(blockstate.getBlock()) && !BlockTags.STANDING_SIGNS.contains(blockstate.getBlock()) && !BlockTags.UNDERWATER_BONEMEALS.contains(blockstate.getBlock()) && !BlockTags.WALL_CORALS.contains(blockstate.getBlock()) && !BlockTags.WALL_SIGNS.contains(blockstate.getBlock()) && !BlockTags.TALL_FLOWERS.contains(blockstate.getBlock()) && !BlockTags.WOODEN_PRESSURE_PLATES.contains(blockstate.getBlock()) && net.minecraftforge.event.ForgeEventFactory.onEntityDestroyBlock(this, blockpos, blockstate)) {
                            flag1 = this.level.destroyBlock(blockpos, true, this) || flag1;
                            this.swing(Hand.MAIN_HAND);
                        }
                    }
                }
            }
        }
        super.aiStep();
    }

    @Override
    public ILivingEntityData finalizeSpawn(@NotNull IServerWorld worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(ItemList.bedrock_sword));
        if (!Config.COMMON.BedrockSwordDrops.get()) {
            this.handDropChances[EquipmentSlotType.MAINHAND.getIndex()] = 0.0F;
        }
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }
}