package com.herobrinemod.herobrine.entities;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradeOffers;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SurvivorEntity extends MerchantEntity {
    // For some reason using normal NBT data doesn't work and TrackedData does. Why? Just ask Mojang about their spaghetti code. This just works and any better method I tried won't work.
    private static final TrackedData<Boolean> SMALL_ARMS = DataTracker.registerData(SurvivorEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> REQUIRES_INITIALIZATION = DataTracker.registerData(SurvivorEntity.class, TrackedDataHandlerRegistry.BOOLEAN); // This is required because for some reason loading an entity from a structure NBT file doesn't run the initialize function, probably to save entity data? Trying to save data for entities loaded from a structure is really weird before you save the world
    private static final TrackedData<String> TEXTURE_PATH = DataTracker.registerData(SurvivorEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<String> TEXTURE_NAMESPACE = DataTracker.registerData(SurvivorEntity.class, TrackedDataHandlerRegistry.STRING);
    private int healTimer;
    private final WanderAroundFarGoal wanderAroundFarGoal = new WanderAroundFarGoal(this, 0.7);
    public SurvivorEntity(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
        ((MobNavigation)this.getNavigation()).setCanPathThroughDoors(true);
        this.getNavigation().setCanSwim(true);
        this.experiencePoints = 5;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, false));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, MobEntity.class, 5, true, true, (entity) -> entity instanceof Monster && !(entity instanceof CreeperEntity)));
        this.goalSelector.add(3, new LookAtCustomerGoal(this));
        this.goalSelector.add(5, new LookAtEntityGoal(this, LivingEntity.class, 32.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder registerAttributes() {
        return createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SMALL_ARMS, false);
        this.dataTracker.startTracking(REQUIRES_INITIALIZATION, false);
        this.dataTracker.startTracking(TEXTURE_PATH, "");
        this.dataTracker.startTracking(TEXTURE_NAMESPACE, "");
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("RegenTimer", this.healTimer);
        nbt.putBoolean("SmallArms", this.getSmallArms());
        nbt.putBoolean("RequiresInitialization", this.dataTracker.get(REQUIRES_INITIALIZATION));
        nbt.putString("TexturePath", this.dataTracker.get(TEXTURE_PATH));
        nbt.putString("TextureNamespace", this.dataTracker.get(TEXTURE_NAMESPACE));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.healTimer = nbt.getInt("RegenTimer");
        this.setSmallArms(nbt.getBoolean("SmallArms"));
        this.dataTracker.set(REQUIRES_INITIALIZATION, nbt.getBoolean("RequiresInitialization"));
        this.dataTracker.set(TEXTURE_PATH, nbt.getString("TexturePath"));
        this.dataTracker.set(TEXTURE_NAMESPACE, nbt.getString("TextureNamespace"));
    }

    // Returning null for getYesSound() and getTradingSound() causes a NullPointerException
    // which for some reason leads to a duplication exploit where a player can get items for free
    // by quickly closing the trading window after completing the trade,
    // so I supply it with a SoundEvent with an empty string as the identifier and the bug seems to be fixed
    // Don't ask me why this happens. Probably something to do with spamming the console causing the trading code to stop for a short time
    @Override
    public SoundEvent getYesSound() {
        return SoundEvent.of(new Identifier(""));
    }

    protected SoundEvent getTradingSound(boolean sold) {
        return SoundEvent.of(new Identifier(""));
    }

    @Override
    public void playCelebrateSound() {
    }

    @Override
    public void mobTick() {
        this.tickHandSwing();
        // Run initialize function if RequiresInitialization is true. RequiresInitialization is only true when loaded from the structure file because I set it to true and removed most NBT data from the entity after saving the structure file.
        // If you set RequiresInitialization to true in the game the entity will be reinitialized. IDK the consequences of this. I hate this implementation because it runs every tick.
        if(this.dataTracker.get(REQUIRES_INITIALIZATION)) {
            this.dataTracker.set(REQUIRES_INITIALIZATION, false);
            this.initialize((ServerWorldAccess) this.getWorld(), this.getWorld().getLocalDifficulty(getBlockPos()), SpawnReason.NATURAL, null, null);
        }

        if(!this.hasCustomer()) {
            this.goalSelector.add(4, this.wanderAroundFarGoal);
        } else {
            this.goalSelector.remove(this.wanderAroundFarGoal);
        }

        if(this.getTarget() != null && this.hasCustomer()) {
            this.resetCustomer();
        }

        --this.healTimer;
        if (this.isAlive() && this.getHealth() < this.getMaxHealth() && this.healTimer < 1) {
            this.healTimer = 80;
            this.heal(1.0f);
        } else if(this.healTimer < 1 && this.isAlive()) {
            this.healTimer = 80;
        }

        // Makes every hostile mob that can see the Survivor and doesn't already have a target and isn't a Herobrine Stalker target the Survivor. Runs every tick. Very bloated implementation, but I've seen worse in the Vanilla code
        Box effectBox = getBoundingBox().expand(32.0, 32.0, 32.0);
        List<LivingEntity> affectedEntities = this.getWorld().getEntitiesByClass(LivingEntity.class, effectBox, EntityPredicates.VALID_LIVING_ENTITY);
        if(!affectedEntities.isEmpty()) {
            for(LivingEntity entity : affectedEntities) {
                if((entity instanceof Monster) && !(entity instanceof CreeperEntity) && ((HostileEntity) entity).getTarget() != null && !(entity instanceof Angerable) && !(entity instanceof HerobrineStalkerEntity) && entity.canSee(this) && entity.isAlive()) {
                    ((HostileEntity) entity).setTarget(this);
                }
            }
        }

        super.mobTick();
    }

    @Override
    public void onDeath(@NotNull DamageSource source) {
        this.resetCustomer();
        super.onDeath(source);
    }

    @Override
    public void tickMovement() {
        this.tickHandSwing();
        super.tickMovement();
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (this.isAlive() && !this.hasCustomer()) {
            for (TradeOffer tradeOffer : this.getOffers()) {
                tradeOffer.resetUses();
            }
            if (this.getOffers().isEmpty()) {
                return ActionResult.success(this.getWorld().isClient);
            } else if (!this.getWorld().isClient) {
                this.beginTradeWith(player);
            }
            return ActionResult.success(this.getWorld().isClient);
        }
        return super.interactMob(player, hand);
    }

    private void beginTradeWith(PlayerEntity customer) {
        this.setCustomer(customer);
        this.sendOffers(customer, this.getDisplayName(), 1);
    }

    @Override
    public void setCustomer(@Nullable PlayerEntity customer) {
        boolean bl = this.getCustomer() != null && customer == null;
        super.setCustomer(customer);
        if (bl) {
            this.resetCustomer();
        }
    }

    @Override
    protected void resetCustomer() {
        super.resetCustomer();
    }

    @Override
    public boolean isLeveledMerchant() {
        return false;
    }

    @Override
    protected void afterUsing(@NotNull TradeOffer offer) {
        if (offer.shouldRewardPlayerExperience()) {
            int i = 3 + this.random.nextInt(4);
            this.getWorld().spawnEntity(new ExperienceOrbEntity(this.getWorld(), this.getX(), this.getY() + 0.5, this.getZ(), i));
        }
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean bl = super.tryAttack(target);
        if (bl) {
            float f = this.getWorld().getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
            if (this.isOnFire() && this.random.nextFloat() < f * 0.3f) {
                target.setOnFireFor(2 * (int)f);
            }
        }
        return bl;
    }

    @Override
    protected void fillRecipes() {
        TradeOffers.Factory[] factorys = SurvivorTrades.SURVIVOR_TRADES.get(1);
        if (factorys != null) {
            TradeOfferList tradeOfferList = this.getOffers();
            this.fillRecipesFromPool(tradeOfferList, factorys, factorys.length);
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public void setTexture(@NotNull Identifier texture) {
        this.dataTracker.set(TEXTURE_NAMESPACE, texture.getNamespace());
        this.dataTracker.set(TEXTURE_PATH, texture.getPath());
    }

    public Identifier getTexture() {
        return new Identifier(this.dataTracker.get(TEXTURE_NAMESPACE), this.dataTracker.get(TEXTURE_PATH));
    }

    public void setSmallArms(boolean smallArms) {
        this.dataTracker.set(SMALL_ARMS, smallArms);
    }

    public boolean getSmallArms() {
        return this.dataTracker.get(SMALL_ARMS);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setTexture(SurvivorSkinRegistry.getSkinList().get(random.nextInt(SurvivorSkinRegistry.getSkinList().size())));
        this.setSmallArms(random.nextBoolean());
        this.healTimer = 80;
        this.setPersistent();
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        this.equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        this.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
        this.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
        this.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
        this.equipStack(EquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }
}