package com.herobrine.mod.entities;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.entities.EntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

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

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MonsterEntity.func_234295_eP_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 20.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 5.0D)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .createMutableAttribute(Attributes.ARMOR, 2.0D)
                .createMutableAttribute(Attributes.ARMOR_TOUGHNESS, 2.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 32.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.45D);
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
    public ILivingEntityData onInitialSpawn(@NotNull IServerWorld worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.GOLDEN_PICKAXE));
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        super.handleStatusUpdate(id);
        if (id == 4) {
            for (int i = 0; i < 20; ++i) {
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                double d2 = this.rand.nextGaussian() * 0.02D;
                this.world.addParticle(ParticleTypes.POOF, this.getPosXWidth(1.0D) - d0 * 10.0D, this.getPosYRandom() - d1 * 10.0D, this.getPosZRandom(1.0D) - d2 * 10.0D, d0, d1, d2);
            }
            if (!this.isSilent()) {
                this.world.playSound(this.getPosX() + 0.5D, this.getPosY() + 0.5D, this.getPosZ() + 0.5D, SoundEvents.ITEM_FIRECHARGE_USE, this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
            }
        }
        if(id == 5) {
            if(!this.isSilent()) {
                this.world.playSound(this.getPosX() + 0.5D, this.getPosY() + 0.5D, this.getPosZ() + 0.5D, SoundEvents.AMBIENT_CAVE, this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
            }
        }
    }

    @Override
    public void livingTick() {
        if(this.lifeTimer > 6000) {
            this.lifeTimer = 6000;
        }
        if (this.lifeTimer < 1) {
            if (!this.world.isRemote) {
                this.world.setEntityState(this, (byte)4);
            }
            this.remove();
        }
        --this.lifeTimer;

        //This was 2 if statements with the same result (GJ said)
        if (this.placeTimer < 1 || this.placeTimer > 1000) {
            this.placeTimer = 1000;
        }
        --this.placeTimer;
        if (this.placeTimer == 0 && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
            int x = (int) this.getPosX();
            int y = (int) this.getPosY();
            int z = (int) this.getPosZ();
            ServerWorld serverWorld = this.getCommandSource().getWorld();
            Rotation rotation = Rotation.values()[rand.nextInt(3)];
            Mirror mirror = Mirror.values()[rand.nextInt(2)];
            Random rand = new Random();
            BlockState blockAt = world.getBlockState(new BlockPos(x, y - 1, z));
            //Checks that the world is not remote and that the builder builds config is true. This code determines what structure the builder places. A ton of if statements probably isn't the best way to do this
            if (!world.isRemote && Config.COMMON.BuilderBuilds.get()) {
                if (rand.nextInt(10) >= 1) {
                    if (blockAt.getBlock() == Blocks.GRASS_BLOCK.getDefaultState().getBlock() && y >= 62 || blockAt.getBlock() == Blocks.DIRT.getDefaultState().getBlock() && y >= 62) {
                        Template template = serverWorld.getWorld().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "dirt_structure"));
                        template.func_237144_a_(serverWorld, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false), rand);
                        this.swingArm(Hand.MAIN_HAND);
                        this.world.setEntityState(this, (byte)5);
                    }
                    if (blockAt.getBlock() == Blocks.STONE.getDefaultState().getBlock() && y <= 61 || blockAt.getBlock() == Blocks.ANDESITE.getDefaultState().getBlock() && y <= 61 || blockAt.getBlock() == Blocks.DIORITE.getDefaultState().getBlock() && y <= 61 || blockAt.getBlock() == Blocks.GRANITE.getDefaultState().getBlock() && y <= 61) {
                        Template template = serverWorld.getWorld().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "ominous_mineshaft"));
                        template.func_237144_a_(serverWorld, new BlockPos(x, y, z), new PlacementSettings().setRotation(rotation).setMirror(mirror).setIgnoreEntities(false), rand);
                        this.swingArm(Hand.MAIN_HAND);
                        this.world.setEntityState(this, (byte)5);
                    }
                    if (blockAt.getBlock() == Blocks.SAND.getDefaultState().getBlock() && y >= 62) {
                        Template template = serverWorld.getWorld().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "sand_structure"));
                        template.func_237144_a_(serverWorld, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false), rand);
                        this.swingArm(Hand.MAIN_HAND);
                        this.world.setEntityState(this, (byte)5);
                    }
                    if (blockAt.getBlock() == Blocks.RED_SAND.getDefaultState().getBlock() && y >= 62) {
                        Template template = serverWorld.getWorld().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "red_sand_structure"));
                        template.func_237144_a_(serverWorld, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false), rand);
                        this.swingArm(Hand.MAIN_HAND);
                        this.world.setEntityState(this, (byte)5);
                    }
                    if (blockAt.getBlock() == Blocks.TERRACOTTA.getDefaultState().getBlock() && y >= 62) {
                        Template template = serverWorld.getWorld().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "terracotta_structure"));
                        template.func_237144_a_(serverWorld, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false), rand);
                        this.swingArm(Hand.MAIN_HAND);
                        this.world.setEntityState(this, (byte)5);
                    }
                    if (blockAt.getBlock() == Blocks.STONE.getDefaultState().getBlock() && y >= 62) {
                        Template template = serverWorld.getWorld().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "stone_structure"));
                        template.func_237144_a_(serverWorld, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false), rand);
                        this.swingArm(Hand.MAIN_HAND);
                        this.world.setEntityState(this, (byte)5);
                    }
                    if (blockAt.getBlock() == Blocks.NETHERRACK.getDefaultState().getBlock() || blockAt.getBlock() == Blocks.WARPED_NYLIUM.getDefaultState().getBlock() || blockAt.getBlock() == Blocks.CRIMSON_NYLIUM.getDefaultState().getBlock()) {
                        Template template = serverWorld.getWorld().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "netherrack_structure"));
                        template.func_237144_a_(serverWorld, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false), rand);
                        this.swingArm(Hand.MAIN_HAND);
                        this.world.setEntityState(this, (byte)5);
                    }
                    if (blockAt.getBlock() == Blocks.END_STONE.getDefaultState().getBlock()) {
                        Template template = serverWorld.getWorld().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "end_stone_structure"));
                        template.func_237144_a_(serverWorld, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false), rand);
                        this.swingArm(Hand.MAIN_HAND);
                        this.world.setEntityState(this, (byte)5);
                    }
                } else {
                    int type = rand.nextInt(8);
                    Template template;
                    switch (type) {
                        case 0:
                            template = serverWorld.getWorld().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "signs/normal1"));
                            break;
                        case 1:
                            template = serverWorld.getWorld().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "signs/normal2"));
                            break;
                        case 2:
                            template = serverWorld.getWorld().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "signs/normal3"));
                            break;
                        case 3:
                            template = serverWorld.getWorld().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "signs/lore1"));
                            break;
                        case 4:
                            template = serverWorld.getWorld().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "signs/lore2"));
                            break;
                        case 5:
                            template = serverWorld.getWorld().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "signs/lore3"));
                            break;
                        case 6:
                            template = serverWorld.getWorld().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "signs/lore4"));
                            break;
                        case 7:
                            template = serverWorld.getWorld().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "signs/lore5"));
                            break;
                        case 8:
                            template = serverWorld.getWorld().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "signs/lore6"));
                            break;
                        default:
                            //I don't know how a value below zero or above eight would happen with the bound of 8, but the IDE would error if a default state is not set.
                            throw new IllegalStateException("[The Legend of Herobrine] Illegal type for Herobrine Builder Signs: " + type + ". Please report this to the issue tracker.");
                    }
                    template.func_237144_a_(serverWorld, new BlockPos(x, y, z), new PlacementSettings().setRotation(rotation).setMirror(Mirror.NONE).setIgnoreEntities(false), rand);
                    this.swingArm(Hand.MAIN_HAND);
                    this.world.setEntityState(this, (byte)5);
                }
            }
        }
        super.livingTick();
    }
}