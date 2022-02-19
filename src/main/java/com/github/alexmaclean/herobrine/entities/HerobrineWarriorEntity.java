package com.github.alexmaclean.herobrine.entities;

import com.github.alexmaclean.herobrine.items.ItemList;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HerobrineWarriorEntity extends HerobrineEntity {
    private int destroyCooldown;

    public HerobrineWarriorEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
        this.destroyCooldown = 500;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 0.6, true));
        this.goalSelector.add(2, new ActiveTargetGoal<>(this, IllagerEntity.class, false));
        this.goalSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        //this.goalSelector.add(4, new ActiveTargetGoal<>(this, SurvivorEntity.class, false));
        this.goalSelector.add(5, new ActiveTargetGoal<>(this, GolemEntity.class, false));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.4d));
        this.goalSelector.add(7, new LookAtEntityGoal(this, IllagerEntity.class, 8.0f));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        //this.goalSelector.add(9, new LookAtEntityGoal(this, SurvivorEntity.class, 8.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, GolemEntity.class, 8.0f));
        this.goalSelector.add(11, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder registerAttributes() {
        return HerobrineEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 50.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_ARMOR, 2.0)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("DestroyCooldown", this.destroyCooldown);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.destroyCooldown = nbt.getInt("DestroyCooldown");
    }

    public boolean canDestroy(@NotNull BlockState state) {
        return state.getMaterial() != Material.FIRE && !state.isAir() && !BlockTags.WITHER_IMMUNE.contains(state.getBlock()) && !BlockTags.DRAGON_IMMUNE.contains(state.getBlock()) && !BlockTags.BEDS.contains(state.getBlock()) && !BlockTags.CROPS.contains(state.getBlock()) && !BlockTags.CARPETS.contains(state.getBlock()) && !BlockTags.BUTTONS.contains(state.getBlock()) && !BlockTags.WOODEN_BUTTONS.contains(state.getBlock()) && !BlockTags.CORAL_PLANTS.contains(state.getBlock()) && !BlockTags.CORALS.contains(state.getBlock()) && !BlockTags.FLOWER_POTS.contains(state.getBlock()) && !BlockTags.PORTALS.contains(state.getBlock()) && !BlockTags.RAILS.contains(state.getBlock()) && !BlockTags.SAPLINGS.contains(state.getBlock()) && !BlockTags.SMALL_FLOWERS.contains(state.getBlock()) && !BlockTags.SIGNS.contains(state.getBlock()) && !BlockTags.STANDING_SIGNS.contains(state.getBlock()) && !BlockTags.UNDERWATER_BONEMEALS.contains(state.getBlock()) && !BlockTags.WALL_CORALS.contains(state.getBlock()) && !BlockTags.WALL_SIGNS.contains(state.getBlock()) && !BlockTags.TALL_FLOWERS.contains(state.getBlock()) && !BlockTags.WOODEN_PRESSURE_PLATES.contains(state.getBlock());
    }

    @Override
    public void mobTick() {
        super.mobTick();
        if(this.destroyCooldown < 1) {
            this.destroyCooldown = 500;
        }

        this.destroyCooldown --;
        if(this.destroyCooldown < 1 && this.unableToAttackTarget() && this.getTarget() instanceof PlayerEntity && world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            int x = MathHelper.floor(this.getY());
            int y = MathHelper.floor(this.getX());
            int z = MathHelper.floor(this.getZ());
            boolean b = false;
            for (int i = -1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    for (int k = 0; k <= 3; ++k) {
                        int l = y + i;
                        int m = x + k;
                        int n = z + j;
                        BlockPos blockPos = new BlockPos(l, m, n);
                        BlockState blockState = this.world.getBlockState(blockPos);
                        if (this.canDestroy(blockState)) {
                            b = this.world.breakBlock(blockPos, true, this) || b;
                            this.swingHand(Hand.MAIN_HAND);
                        }
                    }
                }
            }
        }
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ItemList.BEDROCK_SWORD));
        this.handDropChances[EquipmentSlot.MAINHAND.getEntitySlotId()] = 0.0f;
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }
}