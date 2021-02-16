package com.herobrine.mod.entities;

import com.herobrine.mod.util.entities.EntityRegistry;
import com.herobrine.mod.util.entities.SurvivorSkinRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class AlexSurvivorEntity extends AbstractSurvivorEntity {
    protected AlexSurvivorEntity(EntityType<? extends AlexSurvivorEntity> type, World world) {
        super(type, world);
    }

    public AlexSurvivorEntity(World worldIn) {
        this(EntityRegistry.ALEX_SURVIVOR_ENTITY, worldIn);
    }

    @Override
    public ResourceLocation getSkin() {
        if(textureLocation == null) {
            textureLocation = SurvivorSkinRegistry.chooseSmallArmsSkinFromList().toString();
        }
        return new ResourceLocation(textureLocation);
    }

    @Override
    public ILivingEntityData onInitialSpawn(@NotNull IServerWorld worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        textureLocation = SurvivorSkinRegistry.chooseSmallArmsSkinFromList().toString();
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }
}