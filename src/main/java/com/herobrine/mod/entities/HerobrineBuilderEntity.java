package com.herobrine.mod.entities;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.entities.EntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class HerobrineBuilderEntity extends AbstractHerobrineEntity {
    protected HerobrineBuilderEntity(EntityType<? extends HerobrineBuilderEntity> type, World worldIn) {
        super(type, worldIn);
        experienceValue = 5;
    }

    public HerobrineBuilderEntity(World worldIn) {
        this(EntityRegistry.HEROBRINE_BUILDER_ENTITY, worldIn);
    }

    private int placeTimer = 1000;
    private int lifeTimer = 5100;

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.8D, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractIllagerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractSurvivorEntity.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, GolemEntity.class, true));
        this.targetSelector.addGoal(6, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, AbstractIllagerEntity.class, 8.0F));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, AbstractSurvivorEntity.class, 8.0F));
        this.goalSelector.addGoal(11, new LookAtGoal(this, GolemEntity.class, 8.0F));
        this.goalSelector.addGoal(12, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
    }

    @Override
    public void writeAdditional(@NotNull CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("LifeTime", this.lifeTimer);
        compound.putInt("BuildingInterval", this.placeTimer);
    }

    @Override
    public void readAdditional(@NotNull CompoundNBT compound) {
        super.readAdditional(compound);
        this.lifeTimer = compound.getInt("LifeTime");
        this.placeTimer = compound.getInt("BuildingInterval");
    }

    @Override
    public ILivingEntityData onInitialSpawn(@NotNull IWorld worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.GOLDEN_PICKAXE));
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public void livingTick() {
        if (this.lifeTimer <= 0) {
            this.remove();
        }
        --this.lifeTimer;

        if (this.placeTimer <= 0) {
            this.placeTimer = 1000;
        }
        if (this.placeTimer > 1000) {
            this.placeTimer = 1000;
        }
        --this.placeTimer;
        if (this.placeTimer == 0 && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
            int x = (int) this.getPosX();
            int y = (int) this.getPosY();
            int z = (int) this.getPosZ();
            Rotation rotation = Rotation.values()[rand.nextInt(3)];
            Mirror mirror = Mirror.values()[rand.nextInt(2)];
            BlockState blockAt = world.getBlockState(new BlockPos(x, y - 1, z));
            if (!world.isRemote && Config.COMMON.BuilderBuilds.get()) {
                if (blockAt.getBlock() == Blocks.GRASS_BLOCK.getDefaultState().getBlock() && y >= 62 || blockAt.getBlock() == Blocks.DIRT.getDefaultState().getBlock() && y >= 62) {
                    Template template = ((ServerWorld) world.getWorld()).getSaveHandler().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "dirt_structure"));
                    template.addBlocksToWorldChunk(world, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false));
                    this.swingArm(Hand.MAIN_HAND);
                }
                if (blockAt.getBlock() == Blocks.STONE.getDefaultState().getBlock() && y <= 61 || blockAt.getBlock() == Blocks.ANDESITE.getDefaultState().getBlock() && y <= 61 || blockAt.getBlock() == Blocks.DIORITE.getDefaultState().getBlock() && y <= 61 || blockAt.getBlock() == Blocks.GRANITE.getDefaultState().getBlock() && y <= 61) {
                    Template template = ((ServerWorld) world.getWorld()).getSaveHandler().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "ominous_mineshaft"));
                    template.addBlocksToWorldChunk(world, new BlockPos(x + 1, y - 2, z + 1), new PlacementSettings().setRotation(rotation).setMirror(mirror).setIgnoreEntities(false));
                    this.swingArm(Hand.MAIN_HAND);
                }
                if (blockAt.getBlock() == Blocks.SAND.getDefaultState().getBlock() && y >= 62) {
                    Template template = ((ServerWorld) world.getWorld()).getSaveHandler().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "sand_structure"));
                    template.addBlocksToWorldChunk(world, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false));
                    this.swingArm(Hand.MAIN_HAND);
                }
                if (blockAt.getBlock() == Blocks.RED_SAND.getDefaultState().getBlock() && y >= 62) {
                    Template template = ((ServerWorld) world.getWorld()).getSaveHandler().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "red_sand_structure"));
                    template.addBlocksToWorldChunk(world, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false));
                    this.swingArm(Hand.MAIN_HAND);
                }
                if (blockAt.getBlock() == Blocks.TERRACOTTA.getDefaultState().getBlock() && y >= 62) {
                    Template template = ((ServerWorld) world.getWorld()).getSaveHandler().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "terracotta_structure"));
                    template.addBlocksToWorldChunk(world, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false));
                    this.swingArm(Hand.MAIN_HAND);
                }
                if (blockAt.getBlock() == Blocks.STONE.getDefaultState().getBlock() && y >= 62) {
                    Template template = ((ServerWorld) world.getWorld()).getSaveHandler().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "stone_structure"));
                    template.addBlocksToWorldChunk(world, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false));
                    this.swingArm(Hand.MAIN_HAND);
                }
                if (blockAt.getBlock() == Blocks.NETHERRACK.getDefaultState().getBlock()) {
                    Template template = ((ServerWorld) world.getWorld()).getSaveHandler().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "netherrack_structure"));
                    template.addBlocksToWorldChunk(world, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false));
                    this.swingArm(Hand.MAIN_HAND);
                }
                if (blockAt.getBlock() == Blocks.END_STONE.getDefaultState().getBlock()) {
                    Template template = ((ServerWorld) world.getWorld()).getSaveHandler().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "end_stone_structure"));
                    template.addBlocksToWorldChunk(world, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false));
                    this.swingArm(Hand.MAIN_HAND);
                }
            }
        }
        super.livingTick();
    }
}