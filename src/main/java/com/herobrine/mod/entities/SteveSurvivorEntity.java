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

public class SteveSurvivorEntity extends AbstractSurvivorEntity {
    protected SteveSurvivorEntity(EntityType<? extends SteveSurvivorEntity> type, World world) {
        super(type, world);
    }

    public SteveSurvivorEntity(World worldIn) {
        this(EntityRegistry.STEVE_SURVIVOR_ENTITY, worldIn);
    }

    @Override
    public ResourceLocation getSkin() {
        if(textureLocation == null) {
            textureLocation = SurvivorSkinRegistry.chooseClassicArmsSkinFromList().toString();
        }
        return new ResourceLocation(textureLocation);
    }

    @Override
    public ILivingEntityData onInitialSpawn(@NotNull IServerWorld worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        textureLocation = SurvivorSkinRegistry.chooseClassicArmsSkinFromList().toString();
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }
}
