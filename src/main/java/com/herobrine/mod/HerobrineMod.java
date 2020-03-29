package com.herobrine.mod;

import com.herobrine.mod.blocks.HerobrineAlter;
import com.herobrine.mod.client.renders.RenderRegistry;
import com.herobrine.mod.entities.EntityRegistry;
import com.herobrine.mod.items.ArmorMaterialList;
import com.herobrine.mod.items.ItemList;
import com.herobrine.mod.items.ItemTierList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Mod(HerobrineMod.MODID)
public class HerobrineMod {
    public static HerobrineMod instance;
    public static final String MODID = "herobrine";
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public ElementsHerobrine elements;

    public HerobrineMod() {
        instance = this;
        elements = new ElementsHerobrine();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @NotNull
    @Contract("_ -> new")
    public static ResourceLocation location(String name) {
        return new ResourceLocation(MODID, name);
    }

    private void clientRegistries(final FMLClientSetupEvent event) {
        RenderRegistry.registerEntityRenders();
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
         public static final Material HEROBRINE_ALTER_MATERIAL = new Material(MaterialColor.RED, false, false, false, false, false, false, false, PushReaction.NORMAL);

        @SubscribeEvent
        public static void registerItems(@NotNull final RegistryEvent.Register<Item> event) {
            assert false;
            event.getRegistry().registerAll(
                    new BlockItem(HerobrineAlter.block, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(location("herobrine_alter")),
                    ItemList.bedrock_sword = new SwordItem(ItemTierList.bedrock_item_tier, 0, -2.4f, new Item.Properties().group(ItemGroup.COMBAT)).setRegistryName(location("bedrock_sword")),
                    ItemList.cursed_diamond = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(location("cursed_diamond")),
                    ItemList.cursed_diamond_sword = new SwordItem(ItemTierList.cursed_diamond_item_tier, 3, -2.4f, new Item.Properties().group(ItemGroup.COMBAT)).setRegistryName(location("cursed_diamond_sword")),
                    ItemList.cursed_diamond_axe = new AxeItem(ItemTierList.cursed_diamond_item_tier, 5, -3.0f, new Item.Properties().group(ItemGroup.TOOLS)).setRegistryName(location("cursed_diamond_axe")),
                    ItemList.cursed_diamond_pickaxe = new PickaxeItem(ItemTierList.cursed_diamond_item_tier, 1, -2.8f, new Item.Properties().group(ItemGroup.TOOLS)).setRegistryName(location("cursed_diamond_pickaxe")),
                    ItemList.cursed_diamond_shovel = new ShovelItem(ItemTierList.cursed_diamond_item_tier, 1.5f, -3.0f, new Item.Properties().group(ItemGroup.TOOLS)).setRegistryName(location("cursed_diamond_shovel")),
                    ItemList.cursed_diamond_hoe = new HoeItem(ItemTierList.cursed_diamond_item_tier, 0.0F, new Item.Properties().group(ItemGroup.TOOLS)).setRegistryName(location("cursed_diamond_hoe")),
                    ItemList.cursed_diamond_helmet = new ArmorItem(ArmorMaterialList.cursed_diamond_armor_material, EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.COMBAT)).setRegistryName(location("cursed_diamond_helmet")),
                    ItemList.cursed_diamond_chestplate = new ArmorItem(ArmorMaterialList.cursed_diamond_armor_material, EquipmentSlotType.CHEST, new Item.Properties().group(ItemGroup.COMBAT)).setRegistryName(location("cursed_diamond_chestplate")),
                    ItemList.cursed_diamond_leggings = new ArmorItem(ArmorMaterialList.cursed_diamond_armor_material, EquipmentSlotType.LEGS, new Item.Properties().group(ItemGroup.COMBAT)).setRegistryName(location("cursed_diamond_leggings")),
                    ItemList.cursed_diamond_boots = new ArmorItem(ArmorMaterialList.cursed_diamond_armor_material, EquipmentSlotType.FEET, new Item.Properties().group(ItemGroup.COMBAT)).setRegistryName(location("cursed_diamond_boots"))
            );
            EntityRegistry.registerEntitySpawnEggs(event);
        }

        @SubscribeEvent
        public static void registerBlocks(@NotNull final RegistryEvent.Register<Block> event) {
            event.getRegistry().registerAll(
                    new HerobrineAlter()
            );
        }

        @SubscribeEvent
        public static void registerEntities(@NotNull final RegistryEvent.Register<EntityType<?>> event) {
            event.getRegistry().registerAll(
                    EntityRegistry.HEROBRINE_ENTITY
            );
                    EntityRegistry.registerEntityWorldSpawns();
        }
    }
}