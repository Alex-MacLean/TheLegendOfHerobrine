package com.herobrine.mod.entities;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.config.Config;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class HerobrineBuilderEntity extends AbstractHerobrineEntity {
    private int placeTimer = 1000;
    private int lifeTimer = 5100;

    public HerobrineBuilderEntity(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.95F);
        this.experienceValue = 5;
    }

    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, AbstractIllager.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, AbstractSurvivorEntity.class, true));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityGolem.class, true));
        this.targetTasks.addTask(5, new EntityAIHurtByTarget(this, false));
        this.tasks.addTask(6, new EntityAIAttackMelee(this, 1.0d, true));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 0.6d));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, AbstractIllager.class, 8.0f));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, AbstractSurvivorEntity.class, 8.0f));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityGolem.class, 8.0f));
        this.tasks.addTask(12, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
    }

    @Override
    public void writeEntityToNBT(@NotNull NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("LifeTime", this.lifeTimer);
        compound.setInteger("BuildingInterval", this.placeTimer);
    }

    @Override
    public void readEntityFromNBT(@NotNull NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.lifeTimer = compound.getInteger("LifeTime");
        this.placeTimer = compound.getInteger("BuildingInterval");
    }

    @Override
    public void onUpdate() {
        if(this.lifeTimer <= 0){
            if (this.world.isRemote) {
                if (!this.isSilent()) {
                    this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.ITEM_FIRECHARGE_USE, this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
                }
                for (int i = 0; i < 20; ++i) {
                    double d0 = this.rand.nextGaussian() * 0.02D;
                    double d1 = this.rand.nextGaussian() * 0.02D;
                    double d2 = this.rand.nextGaussian() * 0.02D;
                    this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1);
                }
            }
            this.world.removeEntity(this);
        }
        --this.lifeTimer;

        //This was 2 if statements with the same result (GJ said)
        if (this.placeTimer < 1 || this.placeTimer > 1000) {
            this.placeTimer = 1000;
        }
        --this.placeTimer;
        if (this.placeTimer == 0 && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
            int x = (int) this.posX;
            int y = (int) this.posY;
            int z = (int) this.posZ;
            Rotation rotation = Rotation.values()[rand.nextInt(3)];
            Mirror mirror = Mirror.values()[rand.nextInt(2)];
            IBlockState blockAt = world.getBlockState(new BlockPos(x, y - 1, z));
            //Checks that the world is not remote and that the builder builds config is true. This code determines what structure the builder places. A ton of if statements probably isn't the best way to do this
            if (!world.isRemote && Config.BuilderBuilds) {
                if (blockAt.getBlock() == Blocks.GRASS.getDefaultState().getBlock() && y >= 62 || blockAt.getBlock() == Blocks.DIRT.getDefaultState().getBlock() && y >= 62) {
                    Template template = ((WorldServer) world).getStructureTemplateManager().getTemplate(world.getMinecraftServer(), new ResourceLocation(HerobrineMod.MODID, "dirt_structure"));
                    template.addBlocksToWorldChunk(world, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false));
                    this.swingArm(EnumHand.MAIN_HAND);
                }
                if (blockAt.getBlock() == Blocks.STONE.getBlockState().getBlock() && y <= 61) {
                    Template template = ((WorldServer) world).getStructureTemplateManager().getTemplate(world.getMinecraftServer(), new ResourceLocation(HerobrineMod.MODID, "ominous_mineshaft"));
                    template.addBlocksToWorldChunk(world, new BlockPos(x + 1, y - 2, z + 1), new PlacementSettings().setRotation(rotation).setMirror(mirror).setIgnoreEntities(false));
                    this.swingArm(EnumHand.MAIN_HAND);
                }
                if (blockAt.getBlock() == Blocks.SAND.getDefaultState().getBlock() && y >= 62) {
                    Template template = ((WorldServer) world).getStructureTemplateManager().getTemplate(world.getMinecraftServer(), new ResourceLocation(HerobrineMod.MODID, "sand_structure"));
                    template.addBlocksToWorldChunk(world, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false));
                    this.swingArm(EnumHand.MAIN_HAND);
                }
                if (blockAt.getBlock() == Blocks.SAND.getStateFromMeta(1).getBlock() && y >= 62) {
                    Template template = ((WorldServer) world).getStructureTemplateManager().getTemplate(world.getMinecraftServer(), new ResourceLocation(HerobrineMod.MODID, "red_sand_structure"));
                    template.addBlocksToWorldChunk(world, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false));
                    this.swingArm(EnumHand.MAIN_HAND);
                }
                if (blockAt.getBlock() == Blocks.HARDENED_CLAY.getBlockState().getBlock() && y >= 62) {
                    Template template = ((WorldServer) world).getStructureTemplateManager().getTemplate(world.getMinecraftServer(), new ResourceLocation(HerobrineMod.MODID, "terracotta_structure"));
                    template.addBlocksToWorldChunk(world, new BlockPos(x, y, z), new PlacementSettings().setRotation(rotation).setMirror(mirror).setIgnoreEntities(false));
                    this.swingArm(EnumHand.MAIN_HAND);
                }
                if (blockAt.getBlock() == Blocks.STONE.getDefaultState().getBlock() && y >= 62) {
                    Template template = ((WorldServer) world).getStructureTemplateManager().getTemplate(world.getMinecraftServer(), new ResourceLocation(HerobrineMod.MODID, "stone_structure"));
                    template.addBlocksToWorldChunk(world, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false));
                    this.swingArm(EnumHand.MAIN_HAND);
                }
                if (blockAt.getBlock() == Blocks.NETHERRACK.getDefaultState().getBlock()) {
                    Template template = ((WorldServer) world).getStructureTemplateManager().getTemplate(world.getMinecraftServer(), new ResourceLocation(HerobrineMod.MODID, "netherrack_structure"));
                    template.addBlocksToWorldChunk(world, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false));
                    this.swingArm(EnumHand.MAIN_HAND);
                }
                if (blockAt.getBlock() == Blocks.END_STONE.getDefaultState().getBlock()) {
                    Template template = ((WorldServer) world).getStructureTemplateManager().getTemplate(world.getMinecraftServer(), new ResourceLocation(HerobrineMod.MODID, "end_stone_structure"));
                    template.addBlocksToWorldChunk(world, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false));
                    this.swingArm(EnumHand.MAIN_HAND);
                }
            }
        }
        super.onUpdate();
    }

    @Override
    public IEntityLivingData onInitialSpawn(@NotNull DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_PICKAXE));
        return super.onInitialSpawn(difficulty, livingdata);
    }
}
