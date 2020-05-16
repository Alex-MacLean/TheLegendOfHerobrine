package com.herobrine.mod.entities;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.util.entities.EntityRegistry;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class HerobrineBuilderEntity extends MonsterEntity {
    protected HerobrineBuilderEntity(EntityType<? extends HerobrineBuilderEntity> type, World worldIn) {
        super(type, worldIn);
        experienceValue = 5;
    }

    public HerobrineBuilderEntity(World worldIn) {
        this((EntityType<? extends HerobrineBuilderEntity>) EntityRegistry.HEROBRINE_BUILDER_ENTITY, worldIn);
    }

    private int placeTimer = 1000;
    private int lifeTimer = 5100;

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.8D, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, GolemEntity.class, true));
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(7, new LookAtGoal(this, GolemEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
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
            if (!world.isRemote) {
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