package com.herobrine.mod.entities;

import com.google.common.collect.Maps;
import com.herobrine.mod.util.loot_tables.LootTableInit;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

public class InfectedSheepEntity extends EntityMob implements IShearable {
    private static final DataParameter<Byte> DYE_COLOR = EntityDataManager.createKey(InfectedSheepEntity.class, DataSerializers.BYTE);
    private static final Map<EnumDyeColor, float[]> DYE_TO_RGB = Maps.newEnumMap(EnumDyeColor.class);
    private int sheepTimer;
    private EntityAIEatGrass entityAIEatGrass;

    @Contract(value = "_ -> new", pure = true)
    private static float @NotNull [] createSheepColor(@NotNull EnumDyeColor p_192020_0_)
    {
        float[] afloat = p_192020_0_.getColorComponentValues();
        return new float[] {afloat[0] * 0.75F, afloat[1] * 0.75F, afloat[2] * 0.75F};
    }

    @SideOnly(Side.CLIENT)
    public static float[] getDyeRgb(EnumDyeColor dyeColor)
    {
        return DYE_TO_RGB.get(dyeColor);
    }
    public InfectedSheepEntity(World worldIn) {
        super(worldIn);
        this.experienceValue = 3;
        InventoryCrafting inventoryCrafting = new InventoryCrafting(new Container() {
            public boolean canInteractWith(@NotNull EntityPlayer playerIn) {
                return false;
            }
        }, 2, 1);
        inventoryCrafting.setInventorySlotContents(0, new ItemStack(Items.DYE));
        inventoryCrafting.setInventorySlotContents(1, new ItemStack(Items.DYE));
        this.setSize(0.9f, 1.3f);
    }

    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.entityAIEatGrass = new EntityAIEatGrass(this);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
        this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0d, true));
        this.tasks.addTask(4, this.entityAIEatGrass);
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0d));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23D);
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof UnholyWaterEntity)
            return false;
        return super.attackEntityFrom(source, amount);
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
    public IEntityLivingData onInitialSpawn(@NotNull DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        if ((!(Variables.WorldVariables.get(world).Spawn))) {
            this.world.removeEntity(this);
        }
        this.setFleeceColor(getRandomSheepColor(this.world.rand));
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Override
    protected void updateAITasks() {
        this.sheepTimer = this.entityAIEatGrass.getEatingGrassTimer();
        super.updateAITasks();
    }

    @Override
    public void onLivingUpdate() {
        if (this.world.isRemote) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }

        super.onLivingUpdate();
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(DYE_COLOR, (byte) 0);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        if (this.getSheared()) {
            return LootTableInit.INFECTED_SHEEP;
        }
        else {
            switch (this.getFleeceColor())
            {
                case WHITE:
                default:
                    return LootTableList.ENTITIES_SHEEP_WHITE;
                case ORANGE:
                    return LootTableList.ENTITIES_SHEEP_ORANGE;
                case MAGENTA:
                    return LootTableList.ENTITIES_SHEEP_MAGENTA;
                case LIGHT_BLUE:
                    return LootTableList.ENTITIES_SHEEP_LIGHT_BLUE;
                case YELLOW:
                    return LootTableList.ENTITIES_SHEEP_YELLOW;
                case LIME:
                    return LootTableList.ENTITIES_SHEEP_LIME;
                case PINK:
                    return LootTableList.ENTITIES_SHEEP_PINK;
                case GRAY:
                    return LootTableList.ENTITIES_SHEEP_GRAY;
                case SILVER:
                    return LootTableList.ENTITIES_SHEEP_SILVER;
                case CYAN:
                    return LootTableList.ENTITIES_SHEEP_CYAN;
                case PURPLE:
                    return LootTableList.ENTITIES_SHEEP_PURPLE;
                case BLUE:
                    return LootTableList.ENTITIES_SHEEP_BLUE;
                case BROWN:
                    return LootTableList.ENTITIES_SHEEP_BROWN;
                case GREEN:
                    return LootTableList.ENTITIES_SHEEP_GREEN;
                case RED:
                    return LootTableList.ENTITIES_SHEEP_RED;
                case BLACK:
                    return LootTableList.ENTITIES_SHEEP_BLACK;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 10) {
            this.sheepTimer = 40;
        }
        else {
            super.handleStatusUpdate(id);
        }
    }

    @Override
    public boolean processInteract(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (false && itemstack.getItem() == Items.SHEARS && !this.getSheared()) {
            if (!this.world.isRemote) {
                this.setSheared(true);
                int i = 1 + this.rand.nextInt(3);

                for (int j = 0; j < i; ++j) {
                    EntityItem entityitem = this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, this.getFleeceColor().getMetadata()), 1.0F);
                    entityitem.motionY += this.rand.nextFloat() * 0.05F;
                    entityitem.motionX += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
                    entityitem.motionZ += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
                }
            }

            itemstack.damageItem(1, player);
            this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        }

        return super.processInteract(player, hand);
    }
    @SideOnly(Side.CLIENT)
    public float getHeadRotationPointY(float p_70894_1_) {
        if (this.sheepTimer <= 0) {
            return 0.0F;
        }
        else if (this.sheepTimer >= 4 && this.sheepTimer <= 36) {
            return 1.0F;
        }
        else {
            return this.sheepTimer < 4 ? ((float)this.sheepTimer - p_70894_1_) / 4.0F : -((float)(this.sheepTimer - 40) - p_70894_1_) / 4.0F;
        }
    }

    @SideOnly(Side.CLIENT)
    public float getHeadRotationAngleX(float p_70890_1_) {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
            float f = ((float)(this.sheepTimer - 4) - p_70890_1_) / 32.0F;
            return ((float)Math.PI / 5F) + ((float)Math.PI * 7F / 100F) * MathHelper.sin(f * 28.7F);
        }
        else {
            return this.sheepTimer > 0 ? ((float)Math.PI / 5F) : this.rotationPitch * 0.017453292F;
        }
    }

    @Override
    public void writeEntityToNBT(@NotNull NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Sheared", this.getSheared());
        compound.setByte("Color", (byte)this.getFleeceColor().getMetadata());
    }

    @Override
    public void readEntityFromNBT(@NotNull NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setSheared(compound.getBoolean("Sheared"));
        this.setFleeceColor(EnumDyeColor.byMetadata(compound.getByte("Color")));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SHEEP_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SHEEP_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SHEEP_DEATH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull Block blockIn) {
        this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
    }

    public EnumDyeColor getFleeceColor() {
        return EnumDyeColor.byMetadata(this.dataManager.get(DYE_COLOR) & 15);
    }

    public void setFleeceColor(@NotNull EnumDyeColor color) {
        byte b0 = this.dataManager.get(DYE_COLOR);
        this.dataManager.set(DYE_COLOR, (byte) (b0 & 240 | color.getMetadata() & 15));
    }

    public boolean getSheared() {
        return (this.dataManager.get(DYE_COLOR) & 16) != 0;
    }

    public void setSheared(boolean sheared) {
        byte b0 = this.dataManager.get(DYE_COLOR);

        if (sheared) {
            this.dataManager.set(DYE_COLOR, (byte) (b0 | 16));
        }
        else {
            this.dataManager.set(DYE_COLOR, (byte) (b0 & -17));
        }
    }

    public static EnumDyeColor getRandomSheepColor(@NotNull Random random) {
        int i = random.nextInt(100);

        if (i < 5) {
            return EnumDyeColor.BLACK;
        }
        else if (i < 10) {
            return EnumDyeColor.GRAY;
        }
        else if (i < 15) {
            return EnumDyeColor.SILVER;
        }
        else if (i < 18) {
            return EnumDyeColor.BROWN;
        }
        else {
            return random.nextInt(500) == 0 ? EnumDyeColor.PINK : EnumDyeColor.WHITE;
        }
    }

    @Override
    public void eatGrassBonus() {
        this.setSheared(false);
    }

    @Override
    public boolean isShearable(@NotNull ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos){
        return !this.getSheared();
    }

    @Override
    public java.util.@NotNull List<ItemStack> onSheared(@NotNull ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune) {
        this.setSheared(true);
        int i = 1 + this.rand.nextInt(3);

        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
        for (int j = 0; j < i; ++j)
            ret.add(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, this.getFleeceColor().getMetadata()));

        this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        return ret;
    }

    @Override
    public float getEyeHeight()
    {
        return 0.95F * this.height;
    }

    static {
        for (EnumDyeColor enumdyecolor : EnumDyeColor.values()) {
            DYE_TO_RGB.put(enumdyecolor, createSheepColor(enumdyecolor));
        }

        DYE_TO_RGB.put(EnumDyeColor.WHITE, new float[] {0.9019608F, 0.9019608F, 0.9019608F});
    }
}