package com.herobrine.mod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ObjectHolder;
import org.jetbrains.annotations.NotNull;

import static com.herobrine.mod.HerobrineMod.RegistryEvents.HEROBRINE_ALTER_MATERIAL;
import static com.herobrine.mod.HerobrineMod.location;

public class HerobrineAlter extends Block {
    @ObjectHolder("herobrine:herobrine_alter")
    public static final Block block = null;

    public HerobrineAlter() {
        super(Properties.create(HEROBRINE_ALTER_MATERIAL).hardnessAndResistance(1.5f).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(0).func_226896_b_());
        setRegistryName(location("herobrine_alter"));
    }

    @Override
    public boolean canEntitySpawn(BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos, EntityType<?> type) {
        return false;
    }

    @NotNull
    @Override
    public ActionResultType func_225533_a_(BlockState state, World world, @NotNull BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult hit) {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            {
                java.util.HashMap<String, Object> $_dependencies = new java.util.HashMap<>();
                $_dependencies.put("entity", entity);
                $_dependencies.put("x", x);
                $_dependencies.put("y", y);
                $_dependencies.put("z", z);
                $_dependencies.put("world", world);
                ActivateAlter.executeProcedure($_dependencies);
            }
            return ActionResultType.SUCCESS;
    }
}