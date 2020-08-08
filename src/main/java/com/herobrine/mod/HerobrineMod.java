package com.herobrine.mod;

import com.herobrine.mod.blocks.*;
import com.herobrine.mod.client.renders.RenderRegistry;
import com.herobrine.mod.items.HerobrineStatueItem;
import com.herobrine.mod.items.HolyWaterItem;
import com.herobrine.mod.items.UnholyWaterItem;
import com.herobrine.mod.util.entities.EntityRegistry;
import com.herobrine.mod.util.items.*;
import com.herobrine.mod.util.loot_tables.LootTableInit;
import com.herobrine.mod.util.savedata.Variables;
import com.herobrine.mod.util.worldgen.BiomeInit;
import com.herobrine.mod.util.worldgen.StructureInit;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Mod(name = HerobrineMod.NAME, useMetadata = true, updateJSON = HerobrineMod.UPDATEJSON, acceptedMinecraftVersions = HerobrineMod.MCVERSION, version = HerobrineMod.VERSION, modid = HerobrineMod.MODID)
public class HerobrineMod {
    public static final String NAME = "The Legend of Herobrine";
    public static final String UPDATEJSON = "https://raw.githubusercontent.com/Alex-MacLean/TheLegendOfHerobrine/master/update.json";
    public static final String MCVERSION = "[1.12.2]";
    public static final String VERSION = "0.5.2";
    public static final String MODID = "herobrine";
    public static final SimpleNetworkWrapper PACKET_HANDLER = NetworkRegistry.INSTANCE.newSimpleChannel(MODID + "_" + "packet");
    private int messageID = 0;
    public <T extends IMessage, V extends IMessage> void addNetworkMessage(Class<? extends IMessageHandler<T, V>> handler, Class<T> messageClass, Side @NotNull ... sides) {
        for (Side side : sides) {
            HerobrineMod.PACKET_HANDLER.registerMessage(handler, messageClass, messageID, side);
        }
        messageID++;
    }

    @Contract("_ -> new")
    public static @NotNull ResourceLocation location(String location) {
        return new ResourceLocation(MODID, location);
    }

    @Mod.Instance
    public static HerobrineMod instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        EntityRegistry.registerEntities();
        EntityRegistry.registerSpawnPlacement();
        EntityRegistry.registerEntityWorldSpawns();
        GameRegistry.registerWorldGenerator(new StructureInit(), 0);
        this.addNetworkMessage(Variables.WorldSavedDataSyncMessageHandler.class, Variables.WorldSavedDataSyncMessage.class, Side.SERVER, Side.CLIENT);
    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public static void clientRegistries(FMLPreInitializationEvent event) {
        RenderRegistry.registerEntityRenders();
    }

    @Mod.EventBusSubscriber
    public static class registryEvents {
        @SubscribeEvent
        public static void registerItems(final RegistryEvent.@NotNull Register<Item> event) {
            assert false;
            event.getRegistry().registerAll(
                    ItemList.unholy_water = new UnholyWaterItem().setRegistryName(location("unholy_water")).setTranslationKey(MODID + "." + "unholy_water").setCreativeTab(CreativeTabs.MISC),
                    ItemList.herobrine_alter = new ItemBlock(HerobrineAlter.block).setRegistryName(location("herobrine_alter")).setTranslationKey(MODID + "." + "herobrine_alter").setCreativeTab(CreativeTabs.DECORATIONS),
                    ItemList.holy_water = new HolyWaterItem().setRegistryName(location("holy_water")).setTranslationKey(MODID + "." + "holy_water").setCreativeTab(CreativeTabs.MISC),
                    ItemList.cursed_diamond_leggings = new ItemArmor(ArmorMaterialList.cursed_diamond_armor_material, 2, EntityEquipmentSlot.LEGS).setRegistryName(location("cursed_diamond_leggings")).setTranslationKey(MODID + "." + "cursed_diamond_leggings").setCreativeTab(CreativeTabs.COMBAT),
                    ItemList.cursed_diamond_helmet = new ItemArmor(ArmorMaterialList.cursed_diamond_armor_material, 1, EntityEquipmentSlot.HEAD).setRegistryName(location("cursed_diamond_helmet")).setTranslationKey(MODID + "." + "cursed_diamond_helmet").setCreativeTab(CreativeTabs.COMBAT),
                    ItemList.cursed_diamond_chestplate = new ItemArmor(ArmorMaterialList.cursed_diamond_armor_material, 1, EntityEquipmentSlot.CHEST).setRegistryName(location("cursed_diamond_chestplate")).setTranslationKey(MODID + "." + "cursed_diamond_chestplate").setCreativeTab(CreativeTabs.COMBAT),
                    ItemList.cursed_diamond_boots = new ItemArmor(ArmorMaterialList.cursed_diamond_armor_material, 1, EntityEquipmentSlot.FEET).setRegistryName(location("cursed_diamond_boots")).setTranslationKey(MODID + "." + "cursed_diamond_boots").setCreativeTab(CreativeTabs.COMBAT),
                    ItemList.cursed_diamond_sword = new ItemSword(ItemTierList.cursed_diamond_item_tier).setRegistryName(location("cursed_diamond_sword")).setTranslationKey(MODID + "." + "cursed_diamond_sword").setCreativeTab(CreativeTabs.COMBAT),
                    ItemList.cursed_diamond_hoe = new ItemHoe(ItemTierList.cursed_diamond_item_tier).setRegistryName(location("cursed_diamond_hoe")).setTranslationKey(MODID + "." + "cursed_diamond_hoe").setCreativeTab(CreativeTabs.TOOLS),
                    ItemList.cursed_diamond_shovel = new ItemSpade(ItemTierList.cursed_diamond_item_tier).setRegistryName(location("cursed_diamond_shovel")).setTranslationKey(MODID + "." + "cursed_diamond_shovel").setCreativeTab(CreativeTabs.TOOLS),
                    ItemList.cursed_diamond_pickaxe = new ModdedPickaxeItem(ItemTierList.cursed_diamond_item_tier).setRegistryName(location("cursed_diamond_pickaxe")).setTranslationKey(MODID + "." + "cursed_diamond_pickaxe").setCreativeTab(CreativeTabs.TOOLS),
                    ItemList.cursed_diamond_axe = new ModdedAxeItem(ItemTierList.cursed_diamond_item_tier, 13.0f, -3.0f).setRegistryName(location("cursed_diamond_axe")).setTranslationKey(MODID + "." + "cursed_diamond_axe").setCreativeTab(CreativeTabs.TOOLS),
                    ItemList.bedrock_sword = new ItemSword(ItemTierList.bedrock_item_tier).setRegistryName(location("bedrock_sword")).setTranslationKey(MODID + "." + "bedrock_sword").setCreativeTab(CreativeTabs.COMBAT),
                    ItemList.cursed_diamond = new Item().setRegistryName(location("cursed_diamond")).setTranslationKey(MODID + "." + "cursed_diamond").setCreativeTab(CreativeTabs.MISC),
                    ItemList.cursed_dust = new Item().setRegistryName(location("cursed_dust")).setTranslationKey(MODID + "." + "cursed_dust").setCreativeTab(CreativeTabs.MISC),
                    ItemList.purified_diamond = new Item().setRegistryName(location("purified_diamond")).setTranslationKey(MODID + "." + "purified_diamond").setCreativeTab(CreativeTabs.MISC),
                    ItemList.cursed_diamond_block = new ItemBlock(CursedDiamondBlock.block).setRegistryName(location("cursed_diamond_block")).setTranslationKey(MODID + "." + "cursed_diamond_block").setCreativeTab(CreativeTabs.DECORATIONS),
                    ItemList.purified_diamond_block = new ItemBlock(PurifiedDiamondBlock.block).setRegistryName(location("purified_diamond_block")).setTranslationKey(MODID + "." + "purified_diamond_block").setCreativeTab(CreativeTabs.DECORATIONS),
                    ItemList.herobrine_statue = new HerobrineStatueItem(HerobrineStatue.block).setRegistryName(location("herobrine_statue")).setTranslationKey(MODID + "." + "herobrine_statue").setCreativeTab(CreativeTabs.DECORATIONS)

            );
        }

        @SubscribeEvent
        public void registerLootTables(@NotNull LootTableLoadEvent event) {
            event.getLootTableManager().getLootTableFromLocation(LootTableInit.TRAPPED_HOUSE);
            event.getLootTableManager().getLootTableFromLocation(LootTableInit.HEROBRINE);
            event.getLootTableManager().getLootTableFromLocation(LootTableInit.SURVIVOR_BASE_BEDSIDE);
            event.getLootTableManager().getLootTableFromLocation(LootTableInit.SURVIVOR_BASE_BREWING);
            event.getLootTableManager().getLootTableFromLocation(LootTableInit.SURVIVOR_BASE_DISCS);
        }

        @SubscribeEvent
        public static void registerBiomes(final RegistryEvent.Register<Biome> event) {
            BiomeInit.registerBiomes();
        }

        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.@NotNull Register<Block> event) {
            event.getRegistry().registerAll(
                    new HerobrineAlter(),
                    new CursedDiamondBlock(),
                    new PurifiedDiamondBlock(),
                    new HerobrineStatueTop(),
                    new HerobrineStatue()
            );
        }

        @SubscribeEvent
        public static void registerRenders(ModelRegistryEvent event) {
            registerRender(ItemList.cursed_diamond_block);
            registerRender(ItemList.purified_diamond);
            registerRender(ItemList.unholy_water);
            registerRender(ItemList.herobrine_alter);
            registerRender(ItemList.holy_water);
            registerRender(ItemList.cursed_diamond_leggings);
            registerRender(ItemList.cursed_diamond_helmet);
            registerRender(ItemList.cursed_diamond_chestplate);
            registerRender(ItemList.cursed_diamond_boots);
            registerRender(ItemList.cursed_diamond_sword);
            registerRender(ItemList.cursed_diamond_hoe);
            registerRender(ItemList.cursed_diamond_shovel);
            registerRender(ItemList.cursed_diamond_pickaxe);
            registerRender(ItemList.cursed_diamond_axe);
            registerRender(ItemList.bedrock_sword);
            registerRender(ItemList.cursed_diamond);
            registerRender(ItemList.cursed_dust);
            registerRender(ItemList.purified_diamond_block);
            registerRender(ItemList.herobrine_statue);
        }

        private static void registerRender(Item item) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"));
        }

        @SubscribeEvent
        public void onPlayerLoggedIn(net.minecraftforge.fml.common.gameevent.PlayerEvent.@NotNull PlayerLoggedInEvent event) {
            if (!event.player.world.isRemote) {
                WorldSavedData saveData = Variables.SaveData.get(event.player.world);
                HerobrineMod.PACKET_HANDLER.sendTo(new Variables.WorldSavedDataSyncMessage(saveData), (EntityPlayerMP) event.player);
            }
        }

        @SubscribeEvent
        public void onPlayerChangedDimension(PlayerEvent.@NotNull PlayerChangedDimensionEvent event) {
            if (!event.player.world.isRemote) {
                WorldSavedData saveData = Variables.SaveData.get(event.player.world);
                HerobrineMod.PACKET_HANDLER.sendTo(new Variables.WorldSavedDataSyncMessage(saveData), (EntityPlayerMP) event.player);
            }
        }
    }
}