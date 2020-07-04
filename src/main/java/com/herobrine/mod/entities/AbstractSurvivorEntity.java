package com.herobrine.mod.entities;

import com.google.common.collect.Lists;
import com.herobrine.mod.util.items.ItemList;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AbstractSurvivorEntity extends EntityCreature implements IMob, IMerchant {
    private int healTimer = 80;
    @Nullable
    private EntityPlayer customer;
    private MerchantRecipeList buyingList;
    private final List<List<AbstractSurvivorEntity.ITradeList>> trades = Lists.newArrayList();

    public AbstractSurvivorEntity(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.8F);
        this.experienceValue = 5;
    }
    EntityAIWanderAvoidWater wanderGoal = new EntityAIWanderAvoidWater(this, 0.8D);


    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityMob.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, InfectedLlamaEntity.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, AbstractHerobrineEntity.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, AbstractIllager.class, true));
        this.targetTasks.addTask(4, new EntityAIHurtByTarget(this, false));
        this.tasks.addTask(6, new EntityAIAttackMelee(this, 1.2D, true));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 64.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, AbstractSurvivorEntity.class, 64.0F));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityMob.class, 64.0F));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, InfectedLlamaEntity.class, 64.0F));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, AbstractHerobrineEntity.class, 64.0F));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityGolem.class, 64.0F));
        this.tasks.addTask(12, new EntityAIWatchClosest(this, EntityVillager.class, 64.0F));
        this.tasks.addTask(13, new EntityAIWatchClosest(this, AbstractIllager.class, 64.0F));
        this.tasks.addTask(14, new EntityAILookIdle(this));
    }

    @Override
    public void updateAITasks() {
        if(this.isTrading()) {
            this.tasks.removeTask(this.wanderGoal);
        }

        if(!this.isTrading()) {
            this.tasks.addTask(15, this.wanderGoal);
        }
        super.updateAITasks();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
    }

    @Override
    protected boolean canDropLoot() {
        return true;
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        return !this.isEntityInvulnerable(source) && super.attackEntityFrom(source, amount);
    }

    @Override
    public boolean attackEntityAsMob(@NotNull Entity entityIn) {
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
        if (flag) {
            float d = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
            if (this.isBurning() && this.rand.nextFloat() < d * 0.3F) {
                entityIn.setFire(2 * (int)d);
            }
        }
        return flag;
    }

    @Override
    public IEntityLivingData onInitialSpawn(@NotNull DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.enablePersistence();
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
        this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
        this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
        this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Override
    public void writeEntityToNBT(@NotNull NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("RegenSpeed", this.healTimer);
    }

    @Override
    public void readEntityFromNBT(@NotNull NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.healTimer = compound.getInteger("RegenSpeed");
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.updateArmSwingProgress();
        if (this.healTimer <= 0 && this.getHealth() < this.getMaxHealth()) {
            this.healTimer = 80;
            this.heal(1.0F);
        }
        if (this.healTimer > 80) {
            this.healTimer = 80;
        }
        --this.healTimer;
        this.updateAITasks();
    }

    @Override
    public boolean canBeLeashedTo(@NotNull EntityPlayer player) {
        return false;
    }

    public boolean isTrading() {
        return this.getCustomer() != null;
    }

    @Override
    public void setCustomer(@Nullable EntityPlayer player) {
        this.customer = player;
    }

    @Nullable
    @Override
    public EntityPlayer getCustomer() {
        return this.customer;
    }

    @Nullable
    @Override
    public MerchantRecipeList getRecipes(@NotNull EntityPlayer player) {
        if (this.buyingList == null)
        {
            this.populateBuyingList();
        }
        return net.minecraftforge.event.ForgeEventFactory.listTradeOffers(this, player, buyingList);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setRecipes(@Nullable MerchantRecipeList recipeList) {
    }

    @Override
    public void useRecipe(@NotNull MerchantRecipe recipe) {
        this.livingSoundTime = -this.getTalkInterval();
        int i = 3 + this.rand.nextInt(4);

        if (recipe.getToolUses() == 1 || this.rand.nextInt(5) == 0) {
            i += 5;
        }
        if (recipe.getRewardsExp()) {
            this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY + 0.5D, this.posZ, i));
        }
    }

    @Override
    public void verifySellingItem(@NotNull ItemStack stack) {
    }

    @Override
    public @NotNull World getWorld() {
        return world;
    }

    @Override
    public @NotNull BlockPos getPos() {
        return this.getPosition();
    }

    public interface ITradeList {
        void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random);
    }

    @Nullable
    public List<AbstractSurvivorEntity.ITradeList> getTrades(int level) {
        return level >= 0 && level < this.trades.size() ? Collections.unmodifiableList(this.trades.get(level)) : null;
    }

    private void populateBuyingList() {
        if (this.buyingList == null) {
            this.buyingList = new MerchantRecipeList();
            buyingList.add(new MerchantRecipe(new ItemStack(Items.GOLD_INGOT, 8), new ItemStack(ItemList.cursed_dust)));
            buyingList.add(new MerchantRecipe(new ItemStack(Items.GOLD_INGOT, 2), new ItemStack(Item.getItemFromBlock(Blocks.REDSTONE_TORCH))));
            buyingList.add(new MerchantRecipe(new ItemStack(Items.GOLD_INGOT, 7), new ItemStack(ItemList.unholy_water, 2)));
            buyingList.add(new MerchantRecipe(new ItemStack(Items.GOLD_INGOT, 8), new ItemStack(Items.BLAZE_ROD)));
            buyingList.add(new MerchantRecipe(new ItemStack(Items.GOLD_INGOT, 48), new ItemStack(Items.DIAMOND)));
            buyingList.add(new MerchantRecipe(new ItemStack(Items.IRON_INGOT, 16), new ItemStack(ItemList.holy_water, 2)));
            buyingList.add(new MerchantRecipe(new ItemStack(Items.IRON_INGOT, 3), new ItemStack(Items.LAVA_BUCKET)));
            buyingList.add(new MerchantRecipe(new ItemStack(Items.IRON_INGOT, 16), new ItemStack(Item.getItemFromBlock(Blocks.GOLD_BLOCK))));
            buyingList.add(new MerchantRecipe(new ItemStack(Items.IRON_INGOT, 1), new ItemStack(Item.getItemFromBlock(Blocks.NETHERRACK), 4)));
            buyingList.add(new MerchantRecipe(new ItemStack(Items.IRON_INGOT, 8), new ItemStack(Items.GHAST_TEAR)));
            buyingList.add(new MerchantRecipe(new ItemStack(Items.IRON_INGOT, 8), new ItemStack(Items.BLAZE_POWDER)));
            buyingList.add(new MerchantRecipe(new ItemStack(Items.IRON_INGOT, 3), new ItemStack(Items.WATER_BUCKET)));
            buyingList.add(new MerchantRecipe(new ItemStack(Items.IRON_INGOT, 1), new ItemStack(Items.GLASS_BOTTLE, 3)));
            buyingList.add(new MerchantRecipe(new ItemStack(ItemList.cursed_diamond, 1), new ItemStack(ItemList.purified_diamond)));
        }


        java.util.List<AbstractSurvivorEntity.ITradeList> trades = this.getTrades(6);

        if (trades != null)
        {
            for (AbstractSurvivorEntity.ITradeList iTradeList : trades)
            {
                iTradeList.addMerchantRecipe(this, this.buyingList, this.rand);
            }
        }
    }

    @Override
    public boolean processInteract(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
        player.swingArm(hand);
        if (this.isEntityAlive() && !this.isTrading() && !player.isSneaking()) {
            if (this.buyingList == null) {
                this.populateBuyingList();
            }
            if (!this.world.isRemote) {
                this.setCustomer(player);
                player.displayVillagerTradeGui(this);
            }
            return true;
        } else return super.processInteract(player, hand);

    }
}
