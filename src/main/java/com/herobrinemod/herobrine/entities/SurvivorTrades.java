package com.herobrinemod.herobrine.entities;

import com.google.common.collect.ImmutableMap;
import com.herobrinemod.herobrine.items.ItemList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class SurvivorTrades {
    public static final Int2ObjectMap<TradeOffers.Factory[]> SURVIVOR_TRADES = copyToFastUtilMap(ImmutableMap.of(1, new TradeOffers.Factory[]{new SellItemForGoldFactory(ItemList.CURSED_DUST, 8, 1, 1), new SellItemForGoldFactory(Items.REDSTONE_TORCH, 2, 1, 1), new SellItemForGoldFactory(ItemList.UNHOLY_WATER, 7, 2, 1), new SellItemForGoldFactory(Items.BLAZE_ROD, 8, 1, 1), new SellItemForGoldFactory(Items.DIAMOND, 48, 1, 1), new SellItemForIronFactory(ItemList.HOLY_WATER, 16, 2, 1), new SellItemForIronFactory(Items.LAVA_BUCKET, 3, 1, 1), new SellItemForIronFactory(Items.GOLD_BLOCK, 16, 1, 1), new SellItemForIronFactory(Items.NETHERRACK, 1, 4, 1), new SellItemForIronFactory(Items.GHAST_TEAR, 16, 1, 1), new SellItemForIronFactory(Items.BLAZE_POWDER, 8, 1, 1), new SellItemForIronFactory(Items.WATER_BUCKET, 3, 1, 1), new SellItemForIronFactory(Items.GLASS_BOTTLE, 1, 3, 1), new SellItemForIronFactory(Items.REDSTONE_TORCH, 4, 1, 1), new CursedDiamondPurificationTrade()}));
    @Contract("_ -> new")
    private static @NotNull Int2ObjectMap<TradeOffers.Factory[]> copyToFastUtilMap(ImmutableMap<Integer, TradeOffers.Factory[]> map) {
        return new Int2ObjectOpenHashMap<>(map);
    }

    public static class SellItemForGoldFactory implements TradeOffers.Factory {
        private final ItemStack sell;
        private final int price;
        private final int count;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public SellItemForGoldFactory(Item item, int price, int count, int experience) {
            this(new ItemStack(item), price, count, experience);
        }

        public SellItemForGoldFactory(ItemStack stack, int price, int count, int experience) {
            this(stack, price, count, Integer.MAX_VALUE, experience, 0.05f);
        }

        public SellItemForGoldFactory(ItemStack stack, int price, int count, int maxUses, int experience, float multiplier) {
            this.sell = stack;
            this.price = price;
            this.count = count;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = multiplier;
        }

        @Contract("_, _ -> new")
        @Override
        public @NotNull TradeOffer create(Entity entity, Random random) {
            return new TradeOffer(new ItemStack(Items.GOLD_INGOT, this.price), new ItemStack(this.sell.getItem(), this.count), this.maxUses, this.experience, this.multiplier);
        }
    }

    private static class SellItemForIronFactory implements TradeOffers.Factory {
        private final ItemStack sell;
        private final int price;
        private final int count;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public SellItemForIronFactory(Item item, int price, int count, int experience) {
            this(new ItemStack(item), price, count, experience);
        }

        public SellItemForIronFactory(ItemStack stack, int price, int count, int experience) {
            this(stack, price, count, Integer.MAX_VALUE, experience, 0.05f);
        }

        public SellItemForIronFactory(ItemStack stack, int price, int count, int maxUses, int experience, float multiplier) {
            this.sell = stack;
            this.price = price;
            this.count = count;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = multiplier;
        }

        @Contract("_, _ -> new")
        @Override
        public @NotNull TradeOffer create(Entity entity, Random random) {
            return new TradeOffer(new ItemStack(Items.IRON_INGOT, this.price), new ItemStack(this.sell.getItem(), this.count), this.maxUses, this.experience, this.multiplier);
        }
    }

    private static class CursedDiamondPurificationTrade implements TradeOffers.Factory {
        private final ItemStack sell;
        private final int price;
        private final int count;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public CursedDiamondPurificationTrade() {
            this(new ItemStack(ItemList.PURIFIED_DIAMOND), 1, 1, 5);
        }

        public CursedDiamondPurificationTrade(ItemStack stack, int price, int count, int experience) {
            this(stack, price, count, Integer.MAX_VALUE, experience, 0.05f);
        }

        public CursedDiamondPurificationTrade(ItemStack stack, int price, int count, int maxUses, int experience, float multiplier) {
            this.sell = stack;
            this.price = price;
            this.count = count;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = multiplier;
        }

        @Override
        public TradeOffer create(Entity entity, Random random) {
            return new TradeOffer(new ItemStack(ItemList.CURSED_DIAMOND, this.price), new ItemStack(this.sell.getItem(), this.count), this.maxUses, this.experience, this.multiplier);
        }
    }
}
