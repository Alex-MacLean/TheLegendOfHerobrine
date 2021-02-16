package com.herobrine.mod;

import com.herobrine.mod.blocks.*;
import com.herobrine.mod.client.renders.RenderRegistry;
import com.herobrine.mod.config.Config;
import com.herobrine.mod.items.HerobrineStatueItem;
import com.herobrine.mod.items.HolyWaterItem;
import com.herobrine.mod.items.UnholyWaterItem;
import com.herobrine.mod.util.entities.DefaultSurvivorSkins;
import com.herobrine.mod.util.entities.EntityRegistry;
import com.herobrine.mod.util.items.ArmorMaterialList;
import com.herobrine.mod.util.items.ItemList;
import com.herobrine.mod.util.items.ItemTierList;
import com.herobrine.mod.util.savedata.Variables;
import com.herobrine.mod.util.worldgen.BiomeInit;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod(HerobrineMod.MODID)
@Mod.EventBusSubscriber(modid = HerobrineMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HerobrineMod {
    public static final String MODID = "herobrine";
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public HerobrineMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        this.addNetworkMessage(Variables.WorldSavedDataSyncMessage.class, Variables.WorldSavedDataSyncMessage::buffer, Variables.WorldSavedDataSyncMessage::new, Variables.WorldSavedDataSyncMessage::handler);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC, MODID + "-common.toml");
        MinecraftForge.EVENT_BUS.register(this);
    }

    private int messageID = 0;

    public <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, PacketBuffer> encoder, Function<PacketBuffer, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }

    @NotNull
    @Contract("_ -> new")
    public static ResourceLocation location(String name) {
        return new ResourceLocation(MODID, name);
    }

    private void clientRegistries(final FMLClientSetupEvent event) {
        RenderRegistry.registerEntityRenders();
    }

    private void init(FMLCommonSetupEvent event) {
        DefaultSurvivorSkins.registerDefaultSkins();
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerItems(@NotNull final RegistryEvent.Register<Item> event) {
            assert false;
            event.getRegistry().registerAll(
                    new BlockItem(HerobrineAlter.block, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(location("herobrine_alter")),
                    new BlockItem(CursedDiamondBlock.block, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(location("cursed_diamond_block")),
                    new HerobrineStatueItem(HerobrineStatue.block, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(location("herobrine_statue")),
                    new BlockItem(PurifiedDiamondBlock.block, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(location("purified_diamond_block")),
                    ItemList.bedrock_sword = new SwordItem(ItemTierList.bedrock_item_tier, 0, -2.4f, new Item.Properties().group(ItemGroup.COMBAT)).setRegistryName(location("bedrock_sword")),
                    ItemList.cursed_diamond = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(location("cursed_diamond")),
                    ItemList.cursed_diamond_sword = new SwordItem(ItemTierList.cursed_diamond_item_tier, 3, -2.4f, new Item.Properties().group(ItemGroup.COMBAT)).setRegistryName(location("cursed_diamond_sword")),
                    ItemList.cursed_diamond_axe = new AxeItem(ItemTierList.cursed_diamond_item_tier, 5, -3.0f, new Item.Properties().group(ItemGroup.TOOLS)).setRegistryName(location("cursed_diamond_axe")),
                    ItemList.cursed_diamond_pickaxe = new PickaxeItem(ItemTierList.cursed_diamond_item_tier, 1, -2.8f, new Item.Properties().group(ItemGroup.TOOLS)).setRegistryName(location("cursed_diamond_pickaxe")),
                    ItemList.cursed_diamond_shovel = new ShovelItem(ItemTierList.cursed_diamond_item_tier, 1.5f, -3.0f, new Item.Properties().group(ItemGroup.TOOLS)).setRegistryName(location("cursed_diamond_shovel")),
                    ItemList.cursed_diamond_hoe = new HoeItem(ItemTierList.cursed_diamond_item_tier, -4,0.0f, new Item.Properties().group(ItemGroup.TOOLS)).setRegistryName(location("cursed_diamond_hoe")),
                    ItemList.cursed_diamond_helmet = new ArmorItem(ArmorMaterialList.cursed_diamond_armor_material, EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.COMBAT)).setRegistryName(location("cursed_diamond_helmet")),
                    ItemList.cursed_diamond_chestplate = new ArmorItem(ArmorMaterialList.cursed_diamond_armor_material, EquipmentSlotType.CHEST, new Item.Properties().group(ItemGroup.COMBAT)).setRegistryName(location("cursed_diamond_chestplate")),
                    ItemList.cursed_diamond_leggings = new ArmorItem(ArmorMaterialList.cursed_diamond_armor_material, EquipmentSlotType.LEGS, new Item.Properties().group(ItemGroup.COMBAT)).setRegistryName(location("cursed_diamond_leggings")),
                    ItemList.cursed_diamond_boots = new ArmorItem(ArmorMaterialList.cursed_diamond_armor_material, EquipmentSlotType.FEET, new Item.Properties().group(ItemGroup.COMBAT)).setRegistryName(location("cursed_diamond_boots")),
                    ItemList.cursed_dust = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(location("cursed_dust")),
                    ItemList.holy_water = new HolyWaterItem(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(location("holy_water")),
                    ItemList.unholy_water = new UnholyWaterItem(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(location("unholy_water")),
                    ItemList.purified_diamond = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(location("purified_diamond"))
            );
            EntityRegistry.registerEntitySpawnEggs(event);
        }

        @SubscribeEvent
        public static void registerBlocks(@NotNull final RegistryEvent.Register<Block> event) {
            event.getRegistry().registerAll(
                    new HerobrineAlter(),
                    new CursedDiamondBlock(),
                    new HerobrineStatue(),
                    new HerobrineStatueTop(),
                    new PurifiedDiamondBlock()
            );
        }

        @SubscribeEvent
        public static void registerEntities(@NotNull final RegistryEvent.Register<EntityType<?>> event) {
            event.getRegistry().registerAll(
                    EntityRegistry.HEROBRINE_WARRIOR_ENTITY,
                    EntityRegistry.INFECTED_PIG_ENTITY,
                    EntityRegistry.INFECTED_CHICKEN_ENTITY,
                    EntityRegistry.INFECTED_SHEEP_ENTITY,
                    EntityRegistry.INFECTED_COW_ENTITY,
                    EntityRegistry.INFECTED_MOOSHROOM_ENTITY,
                    EntityRegistry.INFECTED_VILLAGER_ENTITY,
                    EntityRegistry.HEROBRINE_SPY_ENTITY,
                    EntityRegistry.HEROBRINE_BUILDER_ENTITY,
                    EntityRegistry.HEROBRINE_MAGE_ENTITY,
                    EntityRegistry.FAKE_HEROBRINE_MAGE_ENTITY,
                    EntityRegistry.HOLY_WATER_ENTITY,
                    EntityRegistry.UNHOLY_WATER_ENTITY,
                    EntityRegistry.STEVE_SURVIVOR_ENTITY,
                    EntityRegistry.ALEX_SURVIVOR_ENTITY,
                    EntityRegistry.INFECTED_WOLF_ENTITY,
                    EntityRegistry.INFECTED_LLAMA_ENTITY,
                    EntityRegistry.INFECTED_HORSE_ENTITY,
                    EntityRegistry.INFECTED_DONKEY_ENTITY,
                    EntityRegistry.INFECTED_RABBIT_ENTITY,
                    EntityRegistry.INFECTED_BAT_ENTITY
            );
            EntityRegistry.registerSpawnPlacement();
            EntityRegistry.registerEntityAttributes();
        }

        @SubscribeEvent
        public static void registerBiomes(@NotNull final RegistryEvent.Register<Biome> event) {
            BiomeInit.registerBiomes();
        }

        @SubscribeEvent
        public void onPlayerLoggedIn(PlayerEvent.@NotNull PlayerLoggedInEvent event) {
            World world = event.getPlayer().world;
            WorldSavedData saveData = Variables.SaveData.get(world);
            HerobrineMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new Variables.WorldSavedDataSyncMessage(saveData));
            Variables.SaveData.get(world).syncData(world);
        }
    }
}