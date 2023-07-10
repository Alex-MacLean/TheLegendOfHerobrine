package com.herobrinemod.herobrine.entities;

import com.herobrinemod.herobrine.items.ItemList;
import com.herobrinemod.herobrine.savedata.ConfigHandler;
import net.minecraft.block.BlockState;
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
import net.minecraft.registry.tag.BlockTags;
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
        this.destroyCooldown = this.getRandom().nextBetween(400, 600);
        this.experiencePoints = 5;

    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 0.6, false));
        this.goalSelector.add(2, new ActiveTargetGoal<>(this, IllagerEntity.class, false));
        this.goalSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        this.goalSelector.add(4, new ActiveTargetGoal<>(this, SurvivorEntity.class, false));
        this.goalSelector.add(5, new ActiveTargetGoal<>(this, GolemEntity.class, false));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.4));
        this.goalSelector.add(7, new LookAtEntityGoal(this, IllagerEntity.class, 64.0f));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 64.0f));
        this.goalSelector.add(9, new LookAtEntityGoal(this, SurvivorEntity.class, 8.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, GolemEntity.class, 64.0f));
        this.goalSelector.add(11, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder registerAttributes() {
        return createHostileAttributes()
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
        return !state.isIn(BlockTags.FIRE)  && !state.isAir() && !state.isIn(BlockTags.WITHER_IMMUNE) && !state.isIn(BlockTags.DRAGON_IMMUNE) && !state.isIn(BlockTags.BEDS) && !state.isIn(BlockTags.CROPS) && !state.isIn(BlockTags.WOOL_CARPETS) && !state.isIn(BlockTags.BUTTONS) && !state.isIn(BlockTags.WOODEN_BUTTONS) && !state.isIn(BlockTags.FLOWER_POTS) && !state.isIn(BlockTags.PORTALS) && !state.isIn(BlockTags.RAILS) && !state.isIn(BlockTags.SAPLINGS) && !state.isIn(BlockTags.SMALL_FLOWERS) && !state.isIn(BlockTags.FLOWERS) && !state.isIn(BlockTags.SIGNS) && !state.isIn(BlockTags.STANDING_SIGNS) && !state.isIn(BlockTags.UNDERWATER_BONEMEALS) && !state.isIn(BlockTags.WALL_CORALS) && !state.isIn(BlockTags.CORAL_PLANTS) && !state.isIn(BlockTags.WALL_SIGNS) && !state.isIn(BlockTags.TALL_FLOWERS) && !state.isIn(BlockTags.WOODEN_PRESSURE_PLATES) && !state.isIn(BlockTags.PRESSURE_PLATES);
    }

    @Override
    public void mobTick() {
        super.mobTick();
        if(this.destroyCooldown < 1 && ConfigHandler.getHerobrineConfig().readBoolean("WarriorBreaksBlocks") && this.unableToAttackTarget() && this.getTarget() instanceof PlayerEntity && getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            this.destroyCooldown = random.nextBetween(400, 600);
            for (int y = 0; y <= 1; y ++) {
                int x = 0;
                int z = 0;

                switch (this.getHorizontalFacing()) {
                    case NORTH -> z --;
                    case EAST -> x ++;
                    case SOUTH -> z ++;
                    case WEST ->  x --;
                }

                BlockPos blockPos = new BlockPos(this.getBlockX() + x, MathHelper.floor(this.getY()) + y, this.getBlockZ() + z);
                BlockState blockState = this.getWorld().getBlockState(blockPos);
                if (this.canDestroy(blockState)) {
                    this.getWorld().breakBlock(blockPos, true, this);
                    this.swingHand(Hand.MAIN_HAND);
                }
            }
        }
        this.destroyCooldown --;
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ItemList.BEDROCK_SWORD));
        if(!ConfigHandler.getHerobrineConfig().readBoolean("BedrockSwordDrops")) {
            this.handDropChances[EquipmentSlot.MAINHAND.getEntitySlotId()] = 0.0f;
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }
}