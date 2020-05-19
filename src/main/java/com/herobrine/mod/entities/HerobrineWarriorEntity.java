package com.herobrine.mod.entities;

import com.herobrine.mod.util.items.ItemList;
import com.herobrine.mod.util.loot_tables.LootTableInit;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.block.material.Material;
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
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class HerobrineWarriorEntity extends EntityMob {
    private int blockBreakCounter = 100;

    public HerobrineWarriorEntity(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.95F);
        this.experienceValue = 5;
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityGolem.class, false));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 0.6D, true));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.4D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityGolem.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
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
        compound.setInteger("DestroyCooldown", this.blockBreakCounter);
    }

    @Override
    public void readEntityFromNBT(@NotNull NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.blockBreakCounter = compound.getInteger("DestroyCooldown");
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

    private boolean unableToAttackTarget() {
        if(this.getAttackTarget() != null) {
            return this.navigator.getPathToEntityLiving(this.getAttackTarget()) == null && this.getNavigator().noPath() && !this.isSwingInProgress;
        } else {
            return false;
        }
    }

    @Override
    protected void updateAITasks() {
        if (this.blockBreakCounter > 0) {
            --this.blockBreakCounter;
            if (this.blockBreakCounter == 0 && this.unableToAttackTarget() && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
                int i1 = MathHelper.floor(this.posY);
                int l1 = MathHelper.floor(this.posX);
                int i2 = MathHelper.floor(this.posZ);
                boolean flag1 = false;
                for (int k2 = -1; k2 <= 1; ++k2) {
                    for (int l2 = -1; l2 <= 1; ++l2) {
                        for (int j = 0; j <= 2; ++j) {
                            int i3 = l1 + k2;
                            int k = i1 + j;
                            int l = i2 + l2;
                            BlockPos blockpos = new BlockPos(i3, k, l);
                            IBlockState blockstate = this.world.getBlockState(blockpos);
                            if (blockstate.getBlock() != Blocks.FLOWING_WATER && blockstate.getBlock() != Blocks.FLOWING_LAVA && blockstate.getBlock() != Blocks.LAVA && blockstate.getBlock() != Blocks.WATER && blockstate.getBlock() != Blocks.PORTAL && blockstate.getBlock() != Blocks.END_PORTAL_FRAME && blockstate.getBlock() != Blocks.END_PORTAL && blockstate.getBlock() != Blocks.END_GATEWAY && blockstate.getBlock() != Blocks.OBSIDIAN && blockstate.getBlock() != Blocks.BEDROCK && blockstate.getMaterial() != Material.WEB && blockstate.getMaterial() != Material.VINE && blockstate.getMaterial() != Material.STRUCTURE_VOID && blockstate.getMaterial() != Material.PORTAL && blockstate.getMaterial() != Material.SNOW && blockstate.getMaterial() != Material.PLANTS && blockstate.getMaterial() != Material.PISTON && blockstate.getMaterial() != Material.LAVA && blockstate.getMaterial() != Material.DRAGON_EGG && blockstate.getMaterial() != Material.CAKE && blockstate.getMaterial() != Material.CORAL && blockstate.getMaterial() != Material.CACTUS && blockstate.getMaterial() != Material.CARPET && blockstate.getMaterial() != Material.CIRCUITS && blockstate.getMaterial() != Material.AIR && blockstate.getMaterial() != Material.BARRIER && net.minecraftforge.event.ForgeEventFactory.onEntityDestroyBlock(this, blockpos, blockstate)) {
                                flag1 = this.world.destroyBlock(blockpos, true) || flag1;
                                this.swingArm(EnumHand.MAIN_HAND);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onUpdate() {
        this.clearActivePotions();
        if (this.blockBreakCounter <= 0) {
            this.blockBreakCounter = 100;
        }
        if (this.blockBreakCounter > 100) {
            this.blockBreakCounter = 100;
        }
        super.onUpdate();
    }

    @Override
    public IEntityLivingData onInitialSpawn(@NotNull DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        if ((!(Variables.WorldVariables.get(world).Spawn))) {
            this.world.removeEntity(this);
        }
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ItemList.bedrock_sword));
        this.inventoryHandsDropChances[EntityEquipmentSlot.MAINHAND.getIndex()] = 0.0F;
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LootTableInit.HEROBRINE;
    }
}