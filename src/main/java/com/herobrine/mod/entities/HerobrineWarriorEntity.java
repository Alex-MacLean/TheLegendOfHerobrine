package com.herobrine.mod.entities;

import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.items.ItemList;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class HerobrineWarriorEntity extends AbstractHerobrineEntity {
    private int blockBreakCounter = 100;

    public HerobrineWarriorEntity(World worldIn) {
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
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 0.4d));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, AbstractIllager.class, 8.0f));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, AbstractSurvivorEntity.class, 8.0f));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityGolem.class, 8.0f));
        this.tasks.addTask(12, new EntityAILookIdle(this));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
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
            if (Config.WarriorBreaksBlocks && this.blockBreakCounter == 0 && this.unableToAttackTarget() && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
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
        if (this.blockBreakCounter < 1 || this.blockBreakCounter > 500) {
            this.blockBreakCounter = 500;
        }
        super.onUpdate();
    }

    @Override
    public IEntityLivingData onInitialSpawn(@NotNull DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ItemList.bedrock_sword));
        if(!Config.BedrockSwordDrops) {
            this.inventoryHandsDropChances[EntityEquipmentSlot.MAINHAND.getIndex()] = 0.0F;
        }
        return super.onInitialSpawn(difficulty, livingdata);
    }
}