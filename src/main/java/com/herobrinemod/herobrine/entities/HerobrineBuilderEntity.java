package com.herobrinemod.herobrine.entities;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.savedata.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class HerobrineBuilderEntity extends HerobrineEntity {
    private int lifeTimer;
    private int buildTimer;

    public HerobrineBuilderEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.lifeTimer = 5100;
        this.buildTimer = this.getRandom().nextBetween(800, 1200);
        this.experiencePoints = 5;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 0.6 ,false));
        this.goalSelector.add(2, new ActiveTargetGoal<>(this, IllagerEntity.class, false));
        this.goalSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        this.goalSelector.add(4, new ActiveTargetGoal<>(this, GolemEntity.class, false));
        this.goalSelector.add(5, new ActiveTargetGoal<>(this, SurvivorEntity.class, false));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.4));
        this.goalSelector.add(7, new LookAtEntityGoal(this, IllagerEntity.class, 32.0f));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 32.0f));
        this.goalSelector.add(9, new LookAtEntityGoal(this, SurvivorEntity.class, 32.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, GolemEntity.class, 32.0f));
        this.goalSelector.add(11, new LookAroundGoal(this));

    }

    public static DefaultAttributeContainer.Builder registerAttributes() {
        return createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_ARMOR, 0.0)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 0.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.55);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("LifeTimer", this.lifeTimer);
        nbt.putInt("BuildingInterval", this.buildTimer);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.lifeTimer = nbt.getInt("LifeTimer");
        this.buildTimer = nbt.getInt("BuildingInterval");
    }

    @Override
    public void mobTick() {
        if(this.lifeTimer < 1) {
            this.getWorld().sendEntityStatus(this, (byte) 4);
            this.remove(RemovalReason.DISCARDED);
        }
        this.lifeTimer --;
        super.mobTick();

        if(this.buildTimer < 1 && ConfigHandler.getHerobrineConfig().readBoolean("BuilderBuilds")) {
            this.buildTimer = random.nextBetween(800, 1200);
            if (getWorld().getServer() != null) {
                int x = 0;
                int z = 0;

                switch (this.getHorizontalFacing()) {
                    case NORTH -> z = z - 2;
                    case EAST -> x = x + 2;
                    case SOUTH -> z = z + 2;
                    case WEST ->  x = x - 2;
                }

                BlockState state = getWorld().getBlockState(new BlockPos(this.getBlockX() + x, this.getBlockY() - 1, this.getBlockZ() + z));
                ServerWorldAccess serverWorldAccess = (ServerWorld) getWorld();

                if(random.nextInt(5) > 0) {
                    if(this.getY() < getWorld().getSeaLevel()) {
                        if (getWorld().getRegistryKey().getValue().equals(DimensionTypes.OVERWORLD_ID) && state.isIn(BlockTags.BASE_STONE_OVERWORLD) && random.nextInt(3) == 0) {
                            Objects.requireNonNull(getWorld().getServer()).getStructureTemplateManager().getTemplateOrBlank(new Identifier(HerobrineMod.MODID, "ominous_mineshaft")).place(serverWorldAccess, this.getBlockPos(), this.getBlockPos(), new StructurePlacementData().setRotation(BlockRotation.random(random)), random, Block.NOTIFY_ALL);
                            this.swingHand(Hand.MAIN_HAND);
                            this.getWorld().sendEntityStatus(this, (byte) 5);
                            return;
                        }
                    } else if(state == Blocks.SAND.getDefaultState()) {
                        Objects.requireNonNull(getWorld().getServer()).getStructureTemplateManager().getTemplateOrBlank(new Identifier(HerobrineMod.MODID, "sand_structure")).place(serverWorldAccess, new BlockPos(this.getBlockX() + x, this.getBlockY(), this.getBlockZ() + z), this.getBlockPos(), new StructurePlacementData(), random, Block.NOTIFY_ALL);
                        this.swingHand(Hand.MAIN_HAND);
                        this.getWorld().sendEntityStatus(this, (byte) 5);
                        return;
                    } else if(state == Blocks.RED_SAND.getDefaultState()) {
                        Objects.requireNonNull(getWorld().getServer()).getStructureTemplateManager().getTemplateOrBlank(new Identifier(HerobrineMod.MODID, "red_sand_structure")).place(serverWorldAccess, new BlockPos(this.getBlockX() + x, this.getBlockY(), this.getBlockZ() + z), this.getBlockPos(), new StructurePlacementData(), random, Block.NOTIFY_ALL);
                        this.swingHand(Hand.MAIN_HAND);
                        this.getWorld().sendEntityStatus(this, (byte) 5);
                        return;
                    } else if(state == Blocks.NETHERRACK.getDefaultState() || state == Blocks.NETHER_BRICKS.getDefaultState() || state == Blocks.NETHER_BRICK_FENCE.getDefaultState() || state == Blocks.NETHER_BRICK_SLAB.getDefaultState() || state == Blocks.NETHER_BRICK_STAIRS.getDefaultState() || state == Blocks.NETHER_BRICK_WALL.getDefaultState() || state == Blocks.WARPED_NYLIUM.getDefaultState() || state == Blocks.CRIMSON_NYLIUM.getDefaultState() || state == Blocks.NETHER_WART_BLOCK.getDefaultState() || state == Blocks.WARPED_WART_BLOCK.getDefaultState() || state == Blocks.NETHER_WART.getDefaultState() || state == Blocks.WARPED_ROOTS.getDefaultState() || state == Blocks.CRIMSON_ROOTS.getDefaultState() || state == Blocks.TWISTING_VINES.getDefaultState() || state == Blocks.WEEPING_VINES.getDefaultState() || state == Blocks.CRIMSON_FUNGUS.getDefaultState() || state == Blocks.WARPED_FUNGUS.getDefaultState() || state == Blocks.WARPED_HYPHAE.getDefaultState() || state == Blocks.CRIMSON_HYPHAE.getDefaultState() || state == Blocks.SOUL_SAND.getDefaultState() || state == Blocks.SOUL_SOIL.getDefaultState()) {
                        Objects.requireNonNull(getWorld().getServer()).getStructureTemplateManager().getTemplateOrBlank(new Identifier(HerobrineMod.MODID, "netherrack_structure")).place(serverWorldAccess, new BlockPos(this.getBlockX() + x, this.getBlockY(), this.getBlockZ() + z), this.getBlockPos(), new StructurePlacementData(), random, Block.NOTIFY_ALL);
                        this.swingHand(Hand.MAIN_HAND);
                        this.getWorld().sendEntityStatus(this, (byte) 5);
                        return;
                    } else if(state == Blocks.END_STONE.getDefaultState() || state == Blocks.END_STONE_BRICKS.getDefaultState() || state == Blocks.END_STONE_BRICK_SLAB.getDefaultState() || state == Blocks.END_STONE_BRICK_STAIRS.getDefaultState() || state == Blocks.END_STONE_BRICK_WALL.getDefaultState() || state == Blocks.PURPUR_BLOCK.getDefaultState() || state == Blocks.PURPUR_PILLAR.getDefaultState() || state == Blocks.PURPUR_SLAB.getDefaultState() || state == Blocks.PURPUR_STAIRS.getDefaultState() || state == Blocks.CHORUS_PLANT.getDefaultState() || state == Blocks.CHORUS_FLOWER.getDefaultState() || state == Blocks.END_ROD.getDefaultState()) {
                        Objects.requireNonNull(getWorld().getServer()).getStructureTemplateManager().getTemplateOrBlank(new Identifier(HerobrineMod.MODID, "end_stone_structure")).place(serverWorldAccess, new BlockPos(this.getBlockX() + x, this.getBlockY(), this.getBlockZ() + z), this.getBlockPos(), new StructurePlacementData(), random, Block.NOTIFY_ALL);
                        this.swingHand(Hand.MAIN_HAND);
                        this.getWorld().sendEntityStatus(this, (byte) 5);
                        return;
                    } else if(state == Blocks.TERRACOTTA.getDefaultState()) {
                        Objects.requireNonNull(getWorld().getServer()).getStructureTemplateManager().getTemplateOrBlank(new Identifier(HerobrineMod.MODID, "terracotta_structure")).place(serverWorldAccess, new BlockPos(this.getBlockX() + x, this.getBlockY(), this.getBlockZ() + z), this.getBlockPos(), new StructurePlacementData(), random, Block.NOTIFY_ALL);
                        this.swingHand(Hand.MAIN_HAND);
                        this.getWorld().sendEntityStatus(this, (byte) 5);
                        return;
                    } else if(state.isIn(BlockTags.BASE_STONE_OVERWORLD)) {
                        Objects.requireNonNull(getWorld().getServer()).getStructureTemplateManager().getTemplateOrBlank(new Identifier(HerobrineMod.MODID, "stone_structure")).place(serverWorldAccess, new BlockPos(this.getBlockX() + x, this.getBlockY(), this.getBlockZ() + x), this.getBlockPos(), new StructurePlacementData(), random, Block.NOTIFY_ALL);
                        this.swingHand(Hand.MAIN_HAND);
                        this.getWorld().sendEntityStatus(this, (byte) 5);
                        return;
                    } else {
                        Objects.requireNonNull(getWorld().getServer()).getStructureTemplateManager().getTemplateOrBlank(new Identifier(HerobrineMod.MODID, "dirt_structure")).place(serverWorldAccess, new BlockPos(this.getBlockX() + x, this.getBlockY(), this.getBlockZ() + z), this.getBlockPos(), new StructurePlacementData(), random, Block.NOTIFY_ALL);
                        this.swingHand(Hand.MAIN_HAND);
                        this.getWorld().sendEntityStatus(this, (byte) 5);
                        return;
                    }
                } else {
                    if(random.nextBoolean() && this.getBlockY() > this.getWorld().getSeaLevel()) {
                        Objects.requireNonNull(getWorld().getServer()).getStructureTemplateManager().getTemplateOrBlank(new Identifier(HerobrineMod.MODID, "glowstone_pillar")).place(serverWorldAccess, new BlockPos(this.getBlockX() + x, this.getBlockY(), this.getBlockZ() + z), this.getBlockPos(), new StructurePlacementData().setRotation(BlockRotation.random(random)), random, Block.NOTIFY_ALL);
                    } else {
                        int type = random.nextInt(8);
                        switch (type) {
                            case 0 -> Objects.requireNonNull(getWorld().getServer()).getStructureTemplateManager().getTemplateOrBlank(new Identifier(HerobrineMod.MODID, "signs/normal1")).place(serverWorldAccess, new BlockPos(this.getBlockX() + x, this.getBlockY(), this.getBlockZ() + z), this.getBlockPos(), new StructurePlacementData().setRotation(BlockRotation.random(random)), random, Block.NOTIFY_ALL);
                            case 1 -> Objects.requireNonNull(getWorld().getServer()).getStructureTemplateManager().getTemplateOrBlank(new Identifier(HerobrineMod.MODID, "signs/normal2")).place(serverWorldAccess, new BlockPos(this.getBlockX() + x, this.getBlockY(), this.getBlockZ() + z), this.getBlockPos(), new StructurePlacementData().setRotation(BlockRotation.random(random)), random, Block.NOTIFY_ALL);
                            case 2 -> Objects.requireNonNull(getWorld().getServer()).getStructureTemplateManager().getTemplateOrBlank(new Identifier(HerobrineMod.MODID, "signs/normal3")).place(serverWorldAccess, new BlockPos(this.getBlockX() + x, this.getBlockY(), this.getBlockZ() + z), this.getBlockPos(), new StructurePlacementData().setRotation(BlockRotation.random(random)), random, Block.NOTIFY_ALL);
                            case 3 -> Objects.requireNonNull(getWorld().getServer()).getStructureTemplateManager().getTemplateOrBlank(new Identifier(HerobrineMod.MODID, "signs/lore1")).place(serverWorldAccess, new BlockPos(this.getBlockX() + x, this.getBlockY(), this.getBlockZ() + z), this.getBlockPos(), new StructurePlacementData().setRotation(BlockRotation.random(random)), random, Block.NOTIFY_ALL);
                            case 4 -> Objects.requireNonNull(getWorld().getServer()).getStructureTemplateManager().getTemplateOrBlank(new Identifier(HerobrineMod.MODID, "signs/lore2")).place(serverWorldAccess, new BlockPos(this.getBlockX() + x, this.getBlockY(), this.getBlockZ() + z), this.getBlockPos(), new StructurePlacementData().setRotation(BlockRotation.random(random)), random, Block.NOTIFY_ALL);
                            case 5 -> Objects.requireNonNull(getWorld().getServer()).getStructureTemplateManager().getTemplateOrBlank(new Identifier(HerobrineMod.MODID, "signs/lore3")).place(serverWorldAccess, new BlockPos(this.getBlockX() + x, this.getBlockY(), this.getBlockZ() + z), this.getBlockPos(), new StructurePlacementData().setRotation(BlockRotation.random(random)), random, Block.NOTIFY_ALL);
                            case 6 -> Objects.requireNonNull(getWorld().getServer()).getStructureTemplateManager().getTemplateOrBlank(new Identifier(HerobrineMod.MODID, "signs/lore4")).place(serverWorldAccess, new BlockPos(this.getBlockX() + x, this.getBlockY(), this.getBlockZ() + z), this.getBlockPos(), new StructurePlacementData().setRotation(BlockRotation.random(random)), random, Block.NOTIFY_ALL);
                            case 7 -> Objects.requireNonNull(getWorld().getServer()).getStructureTemplateManager().getTemplateOrBlank(new Identifier(HerobrineMod.MODID, "signs/lore5")).place(serverWorldAccess, new BlockPos(this.getBlockX() + x, this.getBlockY(), this.getBlockZ() + z), this.getBlockPos(), new StructurePlacementData().setRotation(BlockRotation.random(random)), random, Block.NOTIFY_ALL);
                            case 8 -> Objects.requireNonNull(getWorld().getServer()).getStructureTemplateManager().getTemplateOrBlank(new Identifier(HerobrineMod.MODID, "signs/lore6")).place(serverWorldAccess, new BlockPos(this.getBlockX() + x, this.getBlockY(), this.getBlockZ() + z), this.getBlockPos(), new StructurePlacementData().setRotation(BlockRotation.random(random)), random, Block.NOTIFY_ALL);
                        }
                    }
                    this.swingHand(Hand.MAIN_HAND);
                    this.getWorld().sendEntityStatus(this, (byte) 5);
                    return;
                }
            }
        }
        this.buildTimer --;
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        switch (status) {
            case 5 -> {
                if (this.getWorld().isClient && !this.isSilent()) {
                    this.getWorld().playSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.AMBIENT_CAVE.value(), this.getSoundCategory(), 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                }
            }

            case 4 -> {
                if (this.getWorld().isClient) {
                    if (!this.isSilent()) {
                        this.getWorld().playSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ITEM_FIRECHARGE_USE, this.getSoundCategory(), 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                    }

                    for (int i = 0; i < 20; i++) {
                        this.getWorld().addParticle(ParticleTypes.POOF, this.getParticleX(1.0), this.getRandomBodyY(), this.getParticleZ(1.0), random.nextGaussian() * 0.02, random.nextGaussian() * 0.02, random.nextGaussian() * 0.02);
                    }
                }
            }
        }
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setPersistent();
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_PICKAXE));
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }
}