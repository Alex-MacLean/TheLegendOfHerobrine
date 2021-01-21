package com.herobrine.mod.entities;

import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.items.ItemList;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLlamaSpit;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

public class InfectedLlamaEntity extends EntityLlama implements IMob {
    private static final DataParameter<Integer> DATA_VARIANT_ID = EntityDataManager.createKey(InfectedLlamaEntity.class, DataSerializers.VARINT);
    public InfectedLlamaEntity(World worldIn) {
        super(worldIn);
        this.experienceValue = 3;
        this.setSize(0.9F, 1.87F);
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 16) {
            if (!this.isSilent()) {
                this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.NEUTRAL, 2.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F, false);
            }
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof HolyWaterEntity) {
            EntityLlama llamaEntity = new EntityLlama(this.world);
            llamaEntity.copyLocationAndAnglesFrom(this);
            llamaEntity.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(llamaEntity)), null);
            llamaEntity.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                llamaEntity.setCustomNameTag(this.getCustomNameTag());
                llamaEntity.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
            }
            llamaEntity.enablePersistence();
            llamaEntity.setVariant(this.getVariant());
            llamaEntity.setGrowingAge(0);
            this.world.setEntityState(this, (byte)16);
            this.world.spawnEntity(llamaEntity);
            this.world.removeEntity(this);
        }
        if (source.getImmediateSource() instanceof UnholyWaterEntity)
            return false;
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, AbstractSurvivorEntity.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityGolem.class, true));
        this.targetTasks.addTask(4, new InfectedLlamaEntity.AIHurtByTarget(this));
        this.tasks.addTask(5, new EntityAIAttackRanged(this, 1.25D, 40, 20.0F));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.7D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, AbstractSurvivorEntity.class, 8.0f));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityGolem.class, 8.0f));
        this.tasks.addTask(10, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
    }

    @Override
    public float getBlockPathWeight(@NotNull BlockPos pos)
    {
        return 0.5F - this.world.getLightBrightness(pos);
    }

    protected boolean isValidLightLevel()
    {
        BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);

        if (this.world.getLightFor(EnumSkyBlock.SKY, blockpos) > this.rand.nextInt(32))
        {
            return false;
        }
        else
        {
            int i = this.world.getLightFromNeighbors(blockpos);

            if (this.world.isThundering())
            {
                int j = this.world.getSkylightSubtracted();
                this.world.setSkylightSubtracted(10);
                i = this.world.getLightFromNeighbors(blockpos);
                this.world.setSkylightSubtracted(j);
            }

            return i <= this.rand.nextInt(8);
        }
    }

    public boolean isChild() {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_LLAMA_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_LLAMA_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_LLAMA_DEATH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull Block blockIn) {
        this.playSound(SoundEvents.ENTITY_LLAMA_STEP, 0.15F, 1.0F);
    }

    @Override
    protected @NotNull SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_HOSTILE_SWIM;
    }

    @Override
    protected @NotNull SoundEvent getSplashSound() {
        return SoundEvents.ENTITY_HOSTILE_SPLASH;
    }

    @Override
    protected @NotNull SoundEvent getFallSound(int heightIn) {
        return heightIn > 4 ? SoundEvents.ENTITY_HOSTILE_BIG_FALL : SoundEvents.ENTITY_HOSTILE_SMALL_FALL;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_LLAMA;
    }

    public boolean hasViewOfSky() {
        return world.canSeeSky(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ));
    }

    public boolean livingEntityCanSpawnHere() {
        IBlockState iblockstate = this.world.getBlockState((new BlockPos(this)).down());
        return iblockstate.canEntitySpawn(this);
    }

    public boolean canCreatureSpawnHere() {
        return livingEntityCanSpawnHere() && this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0F;
    }

    public boolean canMobSpawnHere() {
       return this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.isValidLightLevel() && canCreatureSpawnHere();
    }

    @Override
    public boolean getCanSpawnHere() {
        return canMobSpawnHere() && hasViewOfSky() && Variables.SaveData.get(world).Spawn || canMobSpawnHere() && hasViewOfSky() && Config.HerobrineAlwaysSpawns;
    }

    @Override
    public void writeEntityToNBT(@NotNull NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant", this.getVariant());
    }

    @Override
    public void readEntityFromNBT(@NotNull NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setVariant(compound.getInteger("Variant"));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(DATA_VARIANT_ID, 0);
    }

    @Override
    public int getVariant() {
        return MathHelper.clamp(this.dataManager.get(DATA_VARIANT_ID), 0, 3);
    }

    @Override
    public void setVariant(int variantIn) {
        this.dataManager.set(DATA_VARIANT_ID, variantIn);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(@NotNull DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        int i;
        i = this.rand.nextInt(4);
        this.setVariant(i);
        this.setGrowingAge(0);
        return super.onInitialSpawn(difficulty, livingdata);
    }

    private void spit(@NotNull EntityLivingBase target) {
        EntityLlamaSpit entityllamaspit = new EntityLlamaSpit(this.world, this);
        double d0 = target.posX - this.posX;
        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 3.0F) - entityllamaspit.posY;
        double d2 = target.posZ - this.posZ;
        float f = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;
        entityllamaspit.shoot(d0, d1 + (double)f, d2, 1.5F, 10.0F);
        this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LLAMA_SPIT, this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
        this.world.spawnEntity(entityllamaspit);
    }

    @Override
    protected int getInventorySize() {
        return 0;
    }

    @Override
    protected boolean handleEating(@NotNull EntityPlayer player, @NotNull ItemStack stack) {
       return false;
    }

    @Override
    protected boolean isMovementBlocked() {
        return this.getHealth() <= 0.0F;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasColor() {
        return false;
    }

    @Override
    public int getInventoryColumns() {
        return 0;
    }

    @Override
    public boolean wearsArmor() {
        return false;
    }

    @Override
    public boolean isArmor(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public boolean canBeSaddled() {
        return false;
    }

    @Override
    protected void playChestEquipSound() {
    }

    @Nullable
    @Override
    public EnumDyeColor getColor() {
        return null;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        Variables.SaveData.get(world).syncData(world);
        if (!Variables.SaveData.get(world).Spawn && !Config.HerobrineAlwaysSpawns && !world.isRemote) {
            this.world.removeEntity(this);
        }
        if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            this.setDead();
        }
    }

    @Override
    public @NotNull SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    public void onLivingUpdate() {
        this.updateArmSwingProgress();
        float f = this.getBrightness();
        if (f > 0.5F) {
            this.idleTime += 2;
        }
        super.onLivingUpdate();
    }

    @Override
    public int getMaxTemper() {
        return 0;
    }

    @Override
    public boolean canMateWith(@NotNull EntityAnimal otherAnimal) {
        return false;
    }

    @Override
    public InfectedLlamaEntity createChild(@NotNull EntityAgeable ageable) {
        return null;
    }

    @Override
    public boolean attackEntityAsMob(@NotNull Entity entityIn) {
        boolean flag1 = super.attackEntityAsMob(entityIn);
        if (flag1) {
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
            if (this.isBurning() && this.rand.nextFloat() < f * 0.3F) {
                entityIn.setFire(2 * (int)f);
            }
        }
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        int i = 0;
        if (entityIn instanceof EntityLivingBase) {
            f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase)entityIn).getCreatureAttribute());
            i += EnchantmentHelper.getKnockbackModifier(this);
        }
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);
        if (flag) {
            if (i > 0) {
                ((EntityLivingBase)entityIn).knockBack(this, (float)i * 0.5F, MathHelper.sin(this.rotationYaw * 0.017453292F), -MathHelper.cos(this.rotationYaw * 0.017453292F));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }
            int j = EnchantmentHelper.getFireAspectModifier(this);
            if (j > 0) {
                entityIn.setFire(j * 4);
            }
            if (entityIn instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer)entityIn;
                ItemStack itemstack = this.getHeldItemMainhand();
                ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;

                if (!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.getItem().canDisableShield(itemstack, itemstack1, entityplayer, this) && itemstack1.getItem().isShield(itemstack1, entityplayer)) {
                    float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;
                    if (this.rand.nextFloat() < f1) {
                        entityplayer.getCooldownTracker().setCooldown(itemstack1.getItem(), 100);
                        this.world.setEntityState(entityplayer, (byte)30);
                    }
                }
            }
            this.applyEnchantments(this, entityIn);
        }
        return flag;
    }

    @Override
    public void attackEntityWithRangedAttack(@NotNull EntityLivingBase target, float distanceFactor) {
        this.spit(target);
    }

    @Override
    public boolean hasCaravanTrail() {
        return false;
    }

    @Override
    public boolean inCaravan() {
        return false;
    }

    @Nullable
    @Override
    public InfectedLlamaEntity getCaravanHead() {
        return null;
    }

    static class AIHurtByTarget extends EntityAIHurtByTarget {
        public AIHurtByTarget(InfectedLlamaEntity llama) {
            super(llama, false);
        }
        @Override
        public boolean shouldContinueExecuting() {
            if (this.taskOwner instanceof InfectedLlamaEntity) {
                InfectedLlamaEntity llamaEntity = (InfectedLlamaEntity)this.taskOwner;
                return llamaEntity.getAttackTarget() != null;
            }
            return false;
        }
    }

    @Override
    protected boolean canDropLoot() {
        return true;
    }

    @Override
    public boolean processInteract(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
        return false;
    }

    @Override
    public void onDeath(@NotNull DamageSource source) {
        super.onDeath(source);
        EntityLivingBase entity = (EntityLivingBase) source.getTrueSource();
        Random rand = new Random();
        if(entity != null) {
            int lootingModifier = EnchantmentHelper.getLootingModifier(entity);
            if (rand.nextInt(100) <= 20 * (lootingModifier + 1)) {
                this.dropItem(ItemList.cursed_dust, 1);
            }
        }
    }
}
