package com.herobrinemod.herobrine.entities;

import com.herobrinemod.herobrine.items.ItemList;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SuspiciousStewIngredient;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class InfectedMooshroomEntity extends InfectedCowEntity implements Shearable {
    private static final TrackedData<String> TYPE = DataTracker.registerData(InfectedMooshroomEntity.class, TrackedDataHandlerRegistry.STRING);
    @Nullable
    private StatusEffect stewEffect;
    private int stewEffectDuration;
    @Nullable
    private UUID lightningId;
    public InfectedMooshroomEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setConversionEntity(EntityType.MOOSHROOM);
    }

    public static boolean canMooshroomSpawn(EntityType<? extends InfectedEntity> type, @NotNull ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, net.minecraft.util.math.random.@NotNull Random random) {
        return random.nextInt(20) == 1 && world.getDifficulty() != Difficulty.PEACEFUL && world.getBlockState(pos.down()).isIn(BlockTags.MOOSHROOMS_SPAWNABLE_ON) && isSpawnDark(world, pos, random) && canMobSpawn(type, world, spawnReason, pos, random) && HerobrineSpawnHelper.canHerobrineSpawn() && HerobrineSpawnHelper.getStage() > 1;
    }

    @Override
    public void convert() {
        this.getWorld().sendEntityStatus(this, (byte) 16);
        this.dropItem(ItemList.CURSED_DUST);
        MobEntity entity = this.convertTo(this.getConversionEntity(), false);
        assert entity != null;
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 300, 1));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 300, 1));
        ((MooshroomEntity) entity).setVariant(MooshroomEntity.Type.valueOf(this.getVariant().toString()));
        entity.initialize((ServerWorldAccess) this.getWorld(), this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.CONVERSION, null, null);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TYPE, InfectedMooshroomEntity.Type.RED.name);
    }

    @Override
    public boolean isShearable() {
        return this.isAlive();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("Type", this.getVariant().asString());
        if (this.stewEffect != null) {
            nbt.putInt("EffectId", StatusEffect.getRawId(this.stewEffect));
            nbt.putInt("EffectDuration", this.stewEffectDuration);
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setVariant(InfectedMooshroomEntity.Type.fromName(nbt.getString("Type")));
        if (nbt.contains("EffectId", NbtElement.BYTE_TYPE)) {
            this.stewEffect = StatusEffect.byRawId(nbt.getInt("EffectId"));
        }
        if (nbt.contains("EffectDuration", NbtElement.INT_TYPE)) {
            this.stewEffectDuration = nbt.getInt("EffectDuration");
        }
    }

    public void setVariant(InfectedMooshroomEntity.@NotNull Type type) {
        this.dataTracker.set(TYPE, type.name);
    }

    public InfectedMooshroomEntity.Type getVariant() {
        return InfectedMooshroomEntity.Type.fromName(this.dataTracker.get(TYPE));
    }

    private Optional<Pair<StatusEffect, Integer>> getStewEffectFrom(@NotNull ItemStack flower) {
        SuspiciousStewIngredient suspiciousStewIngredient = SuspiciousStewIngredient.of(flower.getItem());
        if (suspiciousStewIngredient != null) {
            return Optional.of(Pair.of(suspiciousStewIngredient.getEffectInStew(), suspiciousStewIngredient.getEffectInStewDuration()));
        }
        return Optional.empty();
    }

    public enum Type implements StringIdentifiable
    {
        RED("red", Blocks.RED_MUSHROOM.getDefaultState()),
        BROWN("brown", Blocks.BROWN_MUSHROOM.getDefaultState());

        public static final StringIdentifiable.Codec<InfectedMooshroomEntity.Type> CODEC;
        final String name;
        final BlockState mushroom;

        Type(String name, BlockState mushroom) {
            this.name = name;
            this.mushroom = mushroom;
        }

        public BlockState getMushroomState() {
            return this.mushroom;
        }

        @Override
        public String asString() {
            return this.name;
        }

        static InfectedMooshroomEntity.Type fromName(String name) {
            return CODEC.byId(name, RED);
        }

        static {
            CODEC = StringIdentifiable.createCodec(InfectedMooshroomEntity.Type::values);
        }
    }

    @Override
    public void onStruckByLightning(ServerWorld world, @NotNull LightningEntity lightning) {
        UUID uUID = lightning.getUuid();
        if (!uUID.equals(this.lightningId)) {
            this.setVariant(this.getVariant() == InfectedMooshroomEntity.Type.RED ? InfectedMooshroomEntity.Type.BROWN : InfectedMooshroomEntity.Type.RED);
            this.lightningId = uUID;
            this.playSound(SoundEvents.ENTITY_MOOSHROOM_CONVERT, 2.0f, 1.0f);
        }
    }

    @Override
    public ActionResult interactMob(@NotNull PlayerEntity player2, Hand hand) {
        ItemStack itemStack = player2.getStackInHand(hand);
        if (itemStack.isOf(Items.BOWL) && !this.isBaby()) {
            ItemStack itemStack2;
            boolean bl = false;

            if (this.stewEffect != null) {
                bl = true;
                itemStack2 = new ItemStack(Items.SUSPICIOUS_STEW);
                SuspiciousStewItem.addEffectToStew(itemStack2, this.stewEffect, this.stewEffectDuration);
                this.stewEffect = null;
                this.stewEffectDuration = 0;
            } else {
                itemStack2 = new ItemStack(Items.MUSHROOM_STEW);
            }

            ItemStack itemStack3 = ItemUsage.exchangeStack(itemStack, player2, itemStack2, false);
            player2.setStackInHand(hand, itemStack3);
            SoundEvent soundEvent = bl ? SoundEvents.ENTITY_MOOSHROOM_SUSPICIOUS_MILK : SoundEvents.ENTITY_MOOSHROOM_MILK;
            this.playSound(soundEvent, 1.0f, 1.0f);
            return ActionResult.success(this.getWorld().isClient);
        }

        if (itemStack.isOf(Items.SHEARS) && this.isShearable()) {
            this.sheared(SoundCategory.PLAYERS);
            this.emitGameEvent(GameEvent.SHEAR, player2);
            if (!this.getWorld().isClient) {
                itemStack.damage(1, player2, player -> player.sendToolBreakStatus(hand));
            }
            return ActionResult.success(this.getWorld().isClient);
        }

        if (this.getVariant() == InfectedMooshroomEntity.Type.BROWN && itemStack.isIn(ItemTags.SMALL_FLOWERS)) {
            if (this.stewEffect != null) {
                for (int i = 0; i < 2; ++i) {
                    this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX() + this.random.nextDouble() / 2.0, this.getBodyY(0.5), this.getZ() + this.random.nextDouble() / 2.0, 0.0, this.random.nextDouble() / 5.0, 0.0);
                }
            } else {
                Optional<Pair<StatusEffect, Integer>> optional = this.getStewEffectFrom(itemStack);
                if (optional.isEmpty()) {
                    return ActionResult.PASS;
                }
                Pair<StatusEffect, Integer> pair = optional.get();
                if (!player2.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                for (int j = 0; j < 4; ++j) {
                    this.getWorld().addParticle(ParticleTypes.EFFECT, this.getX() + this.random.nextDouble() / 2.0, this.getBodyY(0.5), this.getZ() + this.random.nextDouble() / 2.0, 0.0, this.random.nextDouble() / 5.0, 0.0);
                }
                this.stewEffect = pair.getLeft();
                this.stewEffectDuration = pair.getRight();
                this.playSound(SoundEvents.ENTITY_MOOSHROOM_EAT, 2.0f, 1.0f);
            }
            return ActionResult.success(this.getWorld().isClient);
        }
        return super.interactMob(player2, hand);
    }

    @Override
    public float getPathfindingFavor(@NotNull BlockPos pos, @NotNull WorldView world) {
        if (world.getBlockState(pos.down()).isOf(Blocks.MYCELIUM)) {
            return 10.0f;
        }
        return world.getPhototaxisFavor(pos);
    }

    @Override
    public void sheared(SoundCategory shearedSoundCategory) {
        InfectedCowEntity entity;
        this.getWorld().playSoundFromEntity(null, this, SoundEvents.ENTITY_MOOSHROOM_SHEAR, shearedSoundCategory, 1.0f, 1.0f);
        if (!this.getWorld().isClient() && (entity = EntityTypeList.INFECTED_COW.create(this.getWorld())) != null) {
            ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.EXPLOSION, this.getX(), this.getBodyY(0.5), this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
            this.discard();
            entity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
            entity.setHealth(this.getHealth());
            entity.bodyYaw = this.bodyYaw;

            if (this.hasCustomName()) {
                entity.setCustomName(this.getCustomName());
                entity.setCustomNameVisible(this.isCustomNameVisible());
            }

            if (this.isPersistent()) {
                entity.setPersistent();
            }

            entity.setInvulnerable(this.isInvulnerable());
            this.getWorld().spawnEntity(entity);
            for (int i = 0; i < 5; ++i) {
                this.getWorld().spawnEntity(new ItemEntity(this.getWorld(), this.getX(), this.getBodyY(1.0), this.getZ(), new ItemStack(this.getVariant().mushroom.getBlock())));
            }
        }
    }
}