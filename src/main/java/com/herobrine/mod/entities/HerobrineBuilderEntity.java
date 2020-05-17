package com.herobrine.mod.entities;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.util.loot_tables.LootTableInit;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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

public class HerobrineBuilderEntity extends EntityMob {
    private int placeTimer = 1000;
    private int lifeTimer = 5100;

    public HerobrineBuilderEntity(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.95F);
        this.experienceValue = 5;
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityGolem.class, true));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 0.8D, true));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityGolem.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

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
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof UnholyWaterEntity)
            return false;
        if (source.getImmediateSource() instanceof EntityAreaEffectCloud)
            return false;
        if (source.getImmediateSource() instanceof EntityPotion)
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
        if (source == DamageSource.WITHER)
            return false;
        return super.attackEntityFrom(source, amount);
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
        this.clearActivePotions();
        if(this.lifeTimer <= 0){
            this.world.removeEntity(this);
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
            int x = (int) this.posX;
            int y = (int) this.posY;
            int z = (int) this.posZ;
            Rotation rotation = Rotation.values()[rand.nextInt(3)];
            Mirror mirror = Mirror.values()[rand.nextInt(2)];
            IBlockState blockAt = world.getBlockState(new BlockPos(x, y - 1, z));
            if (!world.isRemote) {
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
    public boolean attackEntityAsMob(@NotNull Entity entityIn) {
        boolean flag = super.attackEntityAsMob(entityIn);
        if (flag)
        {
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();

            if (this.isBurning() && this.rand.nextFloat() < f * 0.3F)
            {
                entityIn.setFire(2 * (int)f);
            }
        }
        return flag;
    }

    @Override
    public IEntityLivingData onInitialSpawn(@NotNull DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        if ((!(Variables.WorldVariables.get(world).Spawn))) {
            this.world.removeEntity(this);
        }
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_PICKAXE));
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LootTableInit.HEROBRINE;
    }
}
