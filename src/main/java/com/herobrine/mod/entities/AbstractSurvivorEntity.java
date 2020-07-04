package com.herobrine.mod.entities;

import com.google.common.collect.Sets;
import com.herobrine.mod.util.entities.SurvivorTrades;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Set;

public class AbstractSurvivorEntity extends CreatureEntity implements IMob, IMerchant, INPC {
    protected AbstractSurvivorEntity(EntityType<? extends AbstractSurvivorEntity> type, World world) {
        super(type, world);
        this.experienceValue = 5;
    }

    @Nullable
    private PlayerEntity customer;
    @Nullable
    protected MerchantOffers offers;
    private final Inventory survivorInventory = new Inventory(27);
    private int healTimer = 80;
    WaterAvoidingRandomWalkingGoal wanderGoal = new WaterAvoidingRandomWalkingGoal(this, 0.8D);

    protected static class LookAtCustomerGoal extends LookAtGoal {
        private final AbstractSurvivorEntity survivorEntity;

        public LookAtCustomerGoal(AbstractSurvivorEntity survivorIn) {
            super(survivorIn, PlayerEntity.class, 8.0F);
            this.survivorEntity = survivorIn;
        }

        @Override
        public boolean shouldExecute() {
            if (!this.survivorEntity.hasNoCustomer()) {
                this.closestEntity = this.survivorEntity.getCustomer();
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, InfectedLlamaEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractHerobrineEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractIllagerEntity.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AbstractRaiderEntity.class, true));
        this.targetSelector.addGoal(6, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(7, new LookAtCustomerGoal(this));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 64.0F));
        this.goalSelector.addGoal(9, new LookAtGoal(this, AbstractSurvivorEntity.class, 64.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MonsterEntity.class, 64.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, InfectedLlamaEntity.class, 64.0F));
        this.goalSelector.addGoal(11, new LookAtGoal(this, AbstractHerobrineEntity.class, 64.0F));
        this.goalSelector.addGoal(12, new LookAtGoal(this, GolemEntity.class, 64.0F));
        this.goalSelector.addGoal(13, new LookAtGoal(this, AbstractVillagerEntity.class, 64.0F));
        this.goalSelector.addGoal(14, new LookAtGoal(this, AbstractIllagerEntity.class, 64.0F));
        this.goalSelector.addGoal(15, new LookAtGoal(this, AbstractRaiderEntity.class, 64.0F));
        this.goalSelector.addGoal(16, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
    }

    @Override
    public void updateAITasks() {
        if(!this.hasNoCustomer()) {
            this.goalSelector.removeGoal(this.wanderGoal);
        }

        if(this.hasNoCustomer()) {
            this.goalSelector.addGoal(17, this.wanderGoal);
        }
    }

    @Override
    protected boolean canDropLoot() {
        return true;
    }

    protected boolean isDespawnPeaceful() {
        return false;
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        return !this.isInvulnerableTo(source) && super.attackEntityFrom(source, amount);
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
    public ILivingEntityData onInitialSpawn(@NotNull IWorld worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.enablePersistence();
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));
        this.setItemStackToSlot(EquipmentSlotType.OFFHAND, new ItemStack(Items.SHIELD));
        this.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(Items.IRON_HELMET));
        this.setItemStackToSlot(EquipmentSlotType.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
        this.setItemStackToSlot(EquipmentSlotType.LEGS, new ItemStack(Items.IRON_LEGGINGS));
        this.setItemStackToSlot(EquipmentSlotType.FEET, new ItemStack(Items.IRON_BOOTS));
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public void writeAdditional(@NotNull CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("RegenSpeed", this.healTimer);
        MerchantOffers merchantoffers = this.getOffers();
        if (!merchantoffers.isEmpty()) {
            compound.put("Offers", merchantoffers.write());
        }

        ListNBT listnbt = new ListNBT();

        for(int i = 0; i < this.survivorInventory.getSizeInventory(); ++i) {
            ItemStack itemstack = this.survivorInventory.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                listnbt.add(itemstack.write(new CompoundNBT()));
            }
        }

        compound.put("Inventory", listnbt);
    }

    @Override
    public void readAdditional(@NotNull CompoundNBT compound) {
        super.readAdditional(compound);
        this.healTimer = compound.getInt("RegenSpeed");
        if (compound.contains("Offers", 10)) {
            this.offers = new MerchantOffers(compound.getCompound("Offers"));
        }

        ListNBT listnbt = compound.getList("Inventory", 10);

        for(int i = 0; i < listnbt.size(); ++i) {
            ItemStack itemstack = ItemStack.read(listnbt.getCompound(i));
            if (!itemstack.isEmpty()) {
                this.survivorInventory.addItem(itemstack);
            }
        }
    }

    protected void resetCustomer() {
        this.setCustomer(null);
    }

    public void onDeath(@NotNull DamageSource cause) {
        super.onDeath(cause);
        this.resetCustomer();
    }

    @Override
    public void livingTick() {
        super.livingTick();
        this.updateArmSwingProgress();
        if(this.isAlive()) {
            if (this.healTimer <= 0 && this.getHealth() < this.getMaxHealth()) {
                this.healTimer = 80;
                this.heal(1.0F);
            }
            if (this.healTimer > 80) {
                this.healTimer = 80;
            }
            --this.healTimer;
            //this.restockTrades();
            this.updateAITasks();
        }
    }

    public boolean hasNoCustomer() {
        return this.customer == null;
    }

    @Override
    public void setCustomer(@Nullable PlayerEntity player) {
        this.customer = player;
    }

    @Nullable
    @Override
    public PlayerEntity getCustomer() {
        return this.customer;
    }

    @Override
    public boolean processInteract(@NotNull PlayerEntity player, @NotNull Hand hand) {
        if (this.isAlive() && this.hasNoCustomer()) {
            if (this.getOffers().isEmpty()) {
                return super.processInteract(player, hand);
            } else {
                if (!this.world.isRemote) {
                    this.setCustomer(player);
                    this.openMerchantContainer(player, this.getDisplayName(), -1);
                }

                return true;
            }
        } else {
            return super.processInteract(player, hand);
        }
    }

    protected void populateTradeData() {
        SurvivorTrades.ITrade[] asurvivortrades$itrade = SurvivorTrades.SURVIVOR_TRADES.get(1);
        SurvivorTrades.ITrade[] asurvivortrades$itrade1 = SurvivorTrades.SURVIVOR_TRADES.get(2);
        if (asurvivortrades$itrade != null && asurvivortrades$itrade1 != null) {
            MerchantOffers merchantoffers = this.getOffers();
            this.addTrades(merchantoffers, asurvivortrades$itrade);
            this.addTrades(merchantoffers, asurvivortrades$itrade1);
        }
    }

    @Override
    public @NotNull MerchantOffers getOffers() {
        if (this.offers == null) {
            this.offers = new MerchantOffers();
            this.populateTradeData();
        }
        return this.offers;
    }

    @Override
    public void onTrade(@NotNull MerchantOffer offer) {
        offer.resetUses();
        this.livingSoundTime = -this.getTalkInterval();
        this.onSurvivorTrade(offer);
    }

    protected void onSurvivorTrade(@NotNull MerchantOffer offer) {
        if (offer.getDoesRewardExp()) {
            int i = 3 + this.rand.nextInt(4);
            this.world.addEntity(new ExperienceOrbEntity(this.world, this.getPosX(), this.getPosY() + 0.5D, this.getPosZ(), i));
        }
    }

    protected void addTrades(MerchantOffers givenMerchantOffers, SurvivorTrades.ITrade @NotNull [] newTrades) {
        Set<Integer> set = Sets.newHashSet();
        if (newTrades.length > 64) {
            while(set.size() < 64) {
                set.add(this.rand.nextInt(newTrades.length));
            }
        } else {
            for(int i = 0; i < newTrades.length; ++i) {
                set.add(i);
            }
        }
        for(Integer integer : set) {
            SurvivorTrades.ITrade survivortrades$itrade = newTrades[integer];
            MerchantOffer merchantoffer = survivortrades$itrade.getOffer(this, this.rand);
            if (merchantoffer != null) {
                givenMerchantOffers.add(merchantoffer);
            }
        }
    }

    @Override
    public void setClientSideOffers(@Nullable MerchantOffers offers) {
    }

    @Override
    public void verifySellingItem(@NotNull ItemStack stack) {
    }

    @Override
    public @NotNull World getWorld() {
        return this.world;
    }

    @Override
    public int getXp() {
        return 0;
    }

    @Override
    public void setXP(int xpIn) {
    }

    @Override
    public boolean func_213705_dZ() {
        return false;
    }

    @Override
    @NotNull
    @SuppressWarnings("ConstantConditions")
    public SoundEvent getYesSound() {
        return null;
    }
}