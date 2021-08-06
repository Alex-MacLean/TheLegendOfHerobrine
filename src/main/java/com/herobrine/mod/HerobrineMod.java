package com.herobrine.mod;

import com.herobrine.mod.blocks.*;
import com.herobrine.mod.client.renders.RenderRegistry;
import com.herobrine.mod.config.Config;
import com.herobrine.mod.items.HerobrineStatueItem;
import com.herobrine.mod.items.HolyWaterItem;
import com.herobrine.mod.items.UnholyWaterItem;
import com.herobrine.mod.savedata.HerobrineSaveData;
import com.herobrine.mod.util.blocks.BlockList;
import com.herobrine.mod.util.entities.DefaultSurvivorSkins;
import com.herobrine.mod.util.entities.EntityRegistry;
import com.herobrine.mod.util.items.ArmorMaterialList;
import com.herobrine.mod.util.items.ItemList;
import com.herobrine.mod.util.items.ItemTierList;
import com.herobrine.mod.util.worldgen.BiomeInit;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Mod(HerobrineMod.MODID)
@Mod.EventBusSubscriber(modid = HerobrineMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HerobrineMod {
    public static final String MODID = "herobrine";

    public HerobrineMod() {
        MinecraftForge.EVENT_BUS.addListener(this::onWorldLoaded);
        MinecraftForge.EVENT_BUS.addListener(this::onWorldSaved);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC, MODID + "-common.toml");
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

    private void init(FMLCommonSetupEvent event) {
        //Calls the registerDefaultSkins function in DefaultSurvivorSkins.
        DefaultSurvivorSkins.registerDefaultSkins();
    }

    @SubscribeEvent
    public static void registerItems(@NotNull final RegistryEvent.Register<Item> event) {
        assert false;
        event.getRegistry().registerAll(
                ItemList.herobrine_altar = new BlockItem(BlockList.herobrine_altar, new Item.Properties().tab(ItemGroup.TAB_DECORATIONS).rarity(Rarity.UNCOMMON)).setRegistryName(location("herobrine_altar")),
                ItemList.cursed_diamond_block = new BlockItem(BlockList.cursed_diamond_block, new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)).setRegistryName(location("cursed_diamond_block")),
                ItemList.herobrine_statue = new HerobrineStatueItem(BlockList.herobrine_statue, new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)).setRegistryName(location("herobrine_statue")),
                ItemList.purified_diamond_block = new BlockItem(BlockList.purified_diamond_block, new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)).setRegistryName(location("purified_diamond_block")),
                ItemList.bedrock_sword = new SwordItem(ItemTierList.bedrock_item_tier, 0, -2.4f, new Item.Properties().tab(ItemGroup.TAB_COMBAT).rarity(Rarity.EPIC)).setRegistryName(location("bedrock_sword")),
                ItemList.cursed_diamond = new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)).setRegistryName(location("cursed_diamond")),
                ItemList.cursed_diamond_sword = new SwordItem(ItemTierList.cursed_diamond_item_tier, 3, -2.4f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)).setRegistryName(location("cursed_diamond_sword")),
                ItemList.cursed_diamond_axe = new AxeItem(ItemTierList.cursed_diamond_item_tier, 5, -3.0f, new Item.Properties().tab(ItemGroup.TAB_TOOLS)).setRegistryName(location("cursed_diamond_axe")),
                ItemList.cursed_diamond_pickaxe = new PickaxeItem(ItemTierList.cursed_diamond_item_tier, 1, -2.8f, new Item.Properties().tab(ItemGroup.TAB_TOOLS)).setRegistryName(location("cursed_diamond_pickaxe")),
                ItemList.cursed_diamond_shovel = new ShovelItem(ItemTierList.cursed_diamond_item_tier, 1.5f, -3.0f, new Item.Properties().tab(ItemGroup.TAB_TOOLS)).setRegistryName(location("cursed_diamond_shovel")),
                ItemList.cursed_diamond_hoe = new HoeItem(ItemTierList.cursed_diamond_item_tier, -4, 0.0f, new Item.Properties().tab(ItemGroup.TAB_TOOLS)).setRegistryName(location("cursed_diamond_hoe")),
                ItemList.cursed_diamond_helmet = new ArmorItem(ArmorMaterialList.cursed_diamond_armor_material, EquipmentSlotType.HEAD, new Item.Properties().tab(ItemGroup.TAB_COMBAT)).setRegistryName(location("cursed_diamond_helmet")),
                ItemList.cursed_diamond_chestplate = new ArmorItem(ArmorMaterialList.cursed_diamond_armor_material, EquipmentSlotType.CHEST, new Item.Properties().tab(ItemGroup.TAB_COMBAT)).setRegistryName(location("cursed_diamond_chestplate")),
                ItemList.cursed_diamond_leggings = new ArmorItem(ArmorMaterialList.cursed_diamond_armor_material, EquipmentSlotType.LEGS, new Item.Properties().tab(ItemGroup.TAB_COMBAT)).setRegistryName(location("cursed_diamond_leggings")),
                ItemList.cursed_diamond_boots = new ArmorItem(ArmorMaterialList.cursed_diamond_armor_material, EquipmentSlotType.FEET, new Item.Properties().tab(ItemGroup.TAB_COMBAT)).setRegistryName(location("cursed_diamond_boots")),
                ItemList.cursed_dust = new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)).setRegistryName(location("cursed_dust")),
                ItemList.holy_water = new HolyWaterItem(new Item.Properties().tab(ItemGroup.TAB_MISC)).setRegistryName(location("holy_water")),
                ItemList.unholy_water = new UnholyWaterItem(new Item.Properties().tab(ItemGroup.TAB_MISC)).setRegistryName(location("unholy_water")),
                ItemList.purified_diamond = new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)).setRegistryName(location("purified_diamond")),
                ItemList.music_disc_dog = new MusicDiscItem(14, () -> new SoundEvent(HerobrineMod.location("music_disc_dog")), new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_MISC).rarity(Rarity.RARE)).setRegistryName(location("music_disc_dog"))
        );
        EntityRegistry.registerEntitySpawnEggs(event);
    }

    @SubscribeEvent
    public static void registerBlocks(@NotNull final RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                BlockList.herobrine_altar = new HerobrineAltar().setRegistryName(location("herobrine_altar")),
                BlockList.cursed_diamond_block = new CursedDiamondBlock().setRegistryName(location("cursed_diamond_block")),
                BlockList.herobrine_statue = new HerobrineStatue().setRegistryName(location("herobrine_statue")),
                BlockList.herobrine_statue_top = new HerobrineStatueTop().setRegistryName(location("herobrine_statue_top")),
                BlockList.purified_diamond_block = new PurifiedDiamondBlock().setRegistryName(location("purified_diamond_block"))
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
                EntityRegistry.INFECTED_BAT_ENTITY,
                EntityRegistry.HEROBRINE_STALKER_ENTITY
        );
        EntityRegistry.registerSpawnPlacement();
        EntityRegistry.registerEntityAttributes();
    }

    @SubscribeEvent
    public static void registerBiomes(@NotNull final RegistryEvent.Register<Biome> event) {
        BiomeInit.registerBiomes();
    }

    public void onWorldLoaded(WorldEvent.Load event) {
        if (!event.getWorld().isClientSide() && event.getWorld() instanceof ServerWorld) {
            HerobrineSaveData saver = HerobrineSaveData.forWorld((ServerWorld) event.getWorld());
            if (!saver.data.contains("Spawn")) {
                saver.data.putBoolean("Spawn", false);
            }
            saver.setDirty();
        }
    }

    public void onWorldSaved(WorldEvent.Save event) {
        if (!event.getWorld().isClientSide() && event.getWorld() instanceof ServerWorld) {
            HerobrineSaveData saver = HerobrineSaveData.forWorld((ServerWorld) event.getWorld());
            if (!saver.data.contains("Spawn")) {
                saver.data.putBoolean("Spawn", false);
            }
            saver.setDirty();
        }
    }
}