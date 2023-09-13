package com.herobrinemod.herobrine.mixin;

import com.herobrinemod.herobrine.items.ItemList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Inject(method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;", at = @At("RETURN"), cancellable = true)
    private static void cursedDiamondToolLootTableFunctions(BlockState state, ServerWorld world, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity entity, @NotNull ItemStack stack, @NotNull CallbackInfoReturnable<List<ItemStack>> cir) {
        if (stack.getItem() == ItemList.CURSED_DIAMOND_PICKAXE && stack.getItem().isSuitableFor(state)) {
            List<ItemStack> returnValue = cir.getReturnValue();
            for (ItemStack itemStack : returnValue) {
                Optional<RecipeEntry<SmeltingRecipe>> recipe = world.getRecipeManager().getFirstMatch(RecipeType.SMELTING, new SimpleInventory(itemStack), world);
                if (recipe.isPresent()) {
                    List<ItemStack> drops = new ArrayList<>();
                    ItemStack smeltedStack = recipe.get().value().getResult(world.getRegistryManager());
                    smeltedStack.setCount(itemStack.getCount());
                    drops.add(smeltedStack);
                    cir.setReturnValue(drops);
                }
            }
        }
        if (stack.getItem() == ItemList.CURSED_DIAMOND_SHOVEL && stack.getItem().isSuitableFor(state)) {
            List<ItemStack> drops = new ArrayList<>();
            ItemStack newStack = state.getBlock().asItem().getDefaultStack();
            drops.add(newStack);
            cir.setReturnValue(drops);
        }
    }
}