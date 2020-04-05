package com.herobrine.mod.entities;

import com.herobrine.mod.util.entities.EntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class InfectedMooshroomEntity extends InfectedCowEntity implements net.minecraftforge.common.IShearable {
    private static final DataParameter<String> MOOSHROOM_TYPE = EntityDataManager.createKey(InfectedMooshroomEntity.class, DataSerializers.STRING);
    private Effect hasStewEffect;
    private int effectDuration;
    private UUID lightningUUID;

    public InfectedMooshroomEntity(EntityType<? extends InfectedMooshroomEntity> type, World worldIn) {
        super(type, worldIn);
        experienceValue = 3;
    }

    public InfectedMooshroomEntity(World worldIn) {
        this((EntityType<? extends InfectedMooshroomEntity>) EntityRegistry.INFECTED_MOOSHROOM_ENTITY, worldIn);
    }

    @Override
    public void onStruckByLightning(@NotNull LightningBoltEntity lightningBolt) {
        UUID uuid = lightningBolt.getUniqueID();
        if (!uuid.equals(this.lightningUUID)) {
            this.setMooshroomType(this.getMooshroomType() == InfectedMooshroomEntity.Type.RED ? InfectedMooshroomEntity.Type.BROWN : InfectedMooshroomEntity.Type.RED);
            this.lightningUUID = uuid;
            this.playSound(SoundEvents.ENTITY_MOOSHROOM_CONVERT, 2.0F, 1.0F);
        }

    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(MOOSHROOM_TYPE, InfectedMooshroomEntity.Type.RED.name);
    }

    @Override
    public boolean processInteract(@NotNull PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
         if (false && itemstack.getItem() == Items.SHEARS) {
            this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosYHeight(0.5D), this.getPosZ(), 0.0D, 0.0D, 0.0D);
            if (!this.world.isRemote) {
                this.remove();
                InfectedCowEntity cowentity = (InfectedCowEntity) EntityRegistry.INFECTED_COW_ENTITY.create(this.world);
                cowentity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
                cowentity.setHealth(this.getHealth());
                cowentity.renderYawOffset = this.renderYawOffset;
                if (this.hasCustomName()) {
                    cowentity.setCustomName(this.getCustomName());
                    cowentity.setCustomNameVisible(this.isCustomNameVisible());
                }

                if (this.isNoDespawnRequired()) {
                    cowentity.enablePersistence();
                }

                cowentity.setInvulnerable(this.isInvulnerable());
                this.world.addEntity(cowentity);

                for(int k = 0; k < 5; ++k) {
                    this.world.addEntity(new ItemEntity(this.world, this.getPosX(), this.getPosYHeight(1.0D), this.getPosZ(), new ItemStack(this.getMooshroomType().renderState.getBlock())));
                }

                itemstack.damageItem(1, player, (p_213442_1_) -> p_213442_1_.sendBreakAnimation(hand));
                this.playSound(SoundEvents.ENTITY_MOOSHROOM_SHEAR, 1.0F, 1.0F);
            }

            return true;
        } else {
            return super.processInteract(player, hand);
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putString("Type", this.getMooshroomType().name);
        if (this.hasStewEffect != null) {
            compound.putByte("EffectId", (byte)Effect.getId(this.hasStewEffect));
            compound.putInt("EffectDuration", this.effectDuration);
        }

    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setMooshroomType(InfectedMooshroomEntity.Type.getTypeByName(compound.getString("Type")));
        if (compound.contains("EffectId", 1)) {
            this.hasStewEffect = Effect.get(compound.getByte("EffectId"));
        }

        if (compound.contains("EffectDuration", 3)) {
            this.effectDuration = compound.getInt("EffectDuration");
        }

    }

    private void setMooshroomType(@NotNull InfectedMooshroomEntity.Type typeIn) {
        this.dataManager.set(MOOSHROOM_TYPE, typeIn.name);
    }

    public InfectedMooshroomEntity.Type getMooshroomType() {
        return InfectedMooshroomEntity.Type.getTypeByName(this.dataManager.get(MOOSHROOM_TYPE));
    }

    @Override
    public boolean isShearable(@NotNull ItemStack item, net.minecraft.world.IWorldReader world, net.minecraft.util.math.BlockPos pos) {
        return !this.isChild();
    }

    @NotNull
    @Override
    public java.util.List<ItemStack> onSheared(@NotNull ItemStack item, net.minecraft.world.IWorld world, net.minecraft.util.math.BlockPos pos, int fortune) {
        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
        this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosYHeight(0.5D), this.getPosZ(), 0.0D, 0.0D, 0.0D);
        if (!this.world.isRemote) {
            this.remove();
            InfectedCowEntity cowentity = (InfectedCowEntity) EntityRegistry.INFECTED_COW_ENTITY.create(this.world);
            assert cowentity != null;
            cowentity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
            cowentity.setHealth(this.getHealth());
            cowentity.renderYawOffset = this.renderYawOffset;
            if (this.hasCustomName()) {
                cowentity.setCustomName(this.getCustomName());
                cowentity.setCustomNameVisible(this.isCustomNameVisible());
            }
            this.world.addEntity(cowentity);
            for(int i = 0; i < 5; ++i) {
                ret.add(new ItemStack(this.getMooshroomType().renderState.getBlock()));
            }
            this.playSound(SoundEvents.ENTITY_MOOSHROOM_SHEAR, 1.0F, 1.0F);
        }
        return ret;
    }

    public enum Type {
        RED("red", Blocks.RED_MUSHROOM.getDefaultState()),
        BROWN("brown", Blocks.BROWN_MUSHROOM.getDefaultState());

        private final String name;
        private final BlockState renderState;

        Type(String nameIn, BlockState renderStateIn) {
            this.name = nameIn;
            this.renderState = renderStateIn;
        }

        @OnlyIn(Dist.CLIENT)
        public BlockState getRenderState() {
            return this.renderState;
        }

        private static InfectedMooshroomEntity.Type getTypeByName(String nameIn) {
            for(InfectedMooshroomEntity.Type mooshroomentity$type : values()) {
                if (mooshroomentity$type.name.equals(nameIn)) {
                    return mooshroomentity$type;
                }
            }

            return RED;
        }
    }
}