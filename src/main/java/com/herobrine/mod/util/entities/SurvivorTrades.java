package com.herobrine.mod.util.entities;

import com.google.common.collect.ImmutableMap;
import com.herobrine.mod.util.items.ItemList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

public class SurvivorTrades {
    public static final Int2ObjectMap<SurvivorTrades.ITrade[]> SURVIVOR_TRADES = getAsIntMap(ImmutableMap.of(1, new SurvivorTrades.ITrade[]{new GoldForItemsTrade(ItemList.cursed_dust, 8, 1), new GoldForItemsTrade(Items.REDSTONE_TORCH, 2, 1), new GoldForItemsTrade(ItemList.unholy_water, 7,2), new GoldForItemsTrade(Items.BLAZE_ROD, 8, 1), new GoldForItemsTrade(Items.DIAMOND, 48, 1), new IronForItemsTrade(ItemList.holy_water, 16, 2), new IronForItemsTrade(Items.LAVA_BUCKET, 3, 1), new IronForItemsTrade(Items.GOLD_BLOCK, 16, 1), new IronForItemsTrade(Items.NETHERRACK, 1, 4), new IronForItemsTrade(Items.GHAST_TEAR, 8, 1), new IronForItemsTrade(Items.BLAZE_POWDER, 8, 1), new IronForItemsTrade(Items.WATER_BUCKET, 3, 1), new IronForItemsTrade(Items.GLASS_BOTTLE, 1, 3)}, 2, new SurvivorTrades.ITrade[]{new CursedDiamondPurificationTrade(ItemList.purified_diamond, 1, 1)}));

    @Contract("_ -> new")
    private static @NotNull Int2ObjectMap<SurvivorTrades.ITrade[]> getAsIntMap(ImmutableMap<Integer, SurvivorTrades.ITrade[]> trade) {
        return new Int2ObjectOpenHashMap<>(trade);
    }

    public interface ITrade {
        @Nullable
        MerchantOffer getOffer(Entity trader, Random rand);
    }

    static class GoldForItemsTrade implements SurvivorTrades.ITrade {
        private final ItemStack item;
        private final int itemCost;
        private final int field_221210_c;
        private final int field_221211_d;
        private final int field_221212_e;
        private final float field_221213_f;

        public GoldForItemsTrade(Item item, int cost, int amount) {
            this(new ItemStack(item), cost, amount, 2147483647, 1);
        }

        public GoldForItemsTrade(ItemStack p_i50531_1_, int p_i50531_2_, int p_i50531_3_, int p_i50531_4_, int p_i50531_5_) {
            this(p_i50531_1_, p_i50531_2_, p_i50531_3_, p_i50531_4_, p_i50531_5_, 0.05F);
        }

        public GoldForItemsTrade(ItemStack item, int cost, int p_i50532_3_, int p_i50532_4_, int p_i50532_5_, float p_i50532_6_) {
            this.item = item;
            this.itemCost = cost;
            this.field_221210_c = p_i50532_3_;
            this.field_221211_d = p_i50532_4_;
            this.field_221212_e = p_i50532_5_;
            this.field_221213_f = p_i50532_6_;
        }

        @Override
        public MerchantOffer getOffer(@NotNull Entity trader, @NotNull Random rand) {
            return new MerchantOffer(new ItemStack(Items.GOLD_INGOT, this.itemCost), new ItemStack(this.item.getItem(), this.field_221210_c), this.field_221211_d, this.field_221212_e, this.field_221213_f);
        }
    }

    static class IronForItemsTrade implements SurvivorTrades.ITrade {
        private final ItemStack item;
        private final int itemCost;
        private final int field_221210_c;
        private final int field_221211_d;
        private final int field_221212_e;
        private final float field_221213_f;

        public IronForItemsTrade(Item item, int cost, int amount) {
            this(new ItemStack(item), cost, amount, 2147483647, 1);
        }

        public IronForItemsTrade(ItemStack p_i50531_1_, int p_i50531_2_, int p_i50531_3_, int p_i50531_4_, int p_i50531_5_) {
            this(p_i50531_1_, p_i50531_2_, p_i50531_3_, p_i50531_4_, p_i50531_5_, 0.05F);
        }

        public IronForItemsTrade(ItemStack item, int cost, int p_i50532_3_, int p_i50532_4_, int p_i50532_5_, float p_i50532_6_) {
            this.item = item;
            this.itemCost = cost;
            this.field_221210_c = p_i50532_3_;
            this.field_221211_d = p_i50532_4_;
            this.field_221212_e = p_i50532_5_;
            this.field_221213_f = p_i50532_6_;
        }

        @Override
        public MerchantOffer getOffer(@NotNull Entity trader, @NotNull Random rand) {
            return new MerchantOffer(new ItemStack(Items.IRON_INGOT, this.itemCost), new ItemStack(this.item.getItem(), this.field_221210_c), this.field_221211_d, this.field_221212_e, this.field_221213_f);
        }
    }

    static class CursedDiamondPurificationTrade implements SurvivorTrades.ITrade {
        private final ItemStack item;
        private final int itemCost;
        private final int field_221210_c;
        private final int field_221211_d;
        private final int field_221212_e;
        private final float field_221213_f;

        public CursedDiamondPurificationTrade(Item item, int cost, int amount) {
            this(new ItemStack(item), cost, amount, 2147483647, 1);
        }

        public CursedDiamondPurificationTrade(ItemStack p_i50531_1_, int p_i50531_2_, int p_i50531_3_, int p_i50531_4_, int p_i50531_5_) {
            this(p_i50531_1_, p_i50531_2_, p_i50531_3_, p_i50531_4_, p_i50531_5_, 0.05F);
        }

        public CursedDiamondPurificationTrade(ItemStack item, int cost, int p_i50532_3_, int p_i50532_4_, int p_i50532_5_, float p_i50532_6_) {
            this.item = item;
            this.itemCost = cost;
            this.field_221210_c = p_i50532_3_;
            this.field_221211_d = p_i50532_4_;
            this.field_221212_e = p_i50532_5_;
            this.field_221213_f = p_i50532_6_;
        }

        @Override
        public MerchantOffer getOffer(@NotNull Entity trader, @NotNull Random rand) {
            return new MerchantOffer(new ItemStack(ItemList.cursed_diamond, this.itemCost), new ItemStack(this.item.getItem(), this.field_221210_c), this.field_221211_d, this.field_221212_e, this.field_221213_f);
        }
    }
}
