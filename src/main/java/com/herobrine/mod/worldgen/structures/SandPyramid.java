package com.herobrine.mod.worldgen.structures;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.savedata.SaveDataUtil;
import net.minecraft.util.Mirror;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;
import java.util.Set;

@Mod.EventBusSubscriber(modid = HerobrineMod.MODID)
public class SandPyramid {
    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        Feature<NoFeatureConfig> feature = new Feature<NoFeatureConfig>(NoFeatureConfig.field_236558_a_) {
            @Override
            @SuppressWarnings("ConstantConditions") //suppresses passing null to argument annotated as NotNull for PlacementSettings.setChunk()
            public boolean generate(@NotNull ISeedReader world, @NotNull ChunkGenerator generator, @NotNull Random random, BlockPos pos, @NotNull NoFeatureConfig config) {
                int ci = (pos.getX() >> 4) << 4;
                int ck = (pos.getZ() >> 4) << 4;
                if ((random.nextInt(1000000) + 1) <= Config.COMMON.SandPyramidWeight.get()) {
                    int count = random.nextInt(1) + 1;
                    for (int a = 0; a < count; a++) {
                        int i = ci + random.nextInt(16);
                        int k = ck + random.nextInt(16);
                        int j = world.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, i, k);
                        j -= 1;
                        BlockPos spawnTo = new BlockPos(i, j , k);
                        Template template = world.getWorld().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "sand_pyramid"));
                        if(SaveDataUtil.canHerobrineSpawn(world.getWorld())) {
                            template.func_237144_a_(world, spawnTo, new PlacementSettings().setRotation(Rotation.NONE).setRandom(random).setMirror(Mirror.NONE).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK).setChunk(null).setIgnoreEntities(false), random);
                        }
                    }
                }
                return true;
            }
        };
        BiomeDictionary.Type[] Biome = {
                BiomeDictionary.Type.OCEAN
        };
        RegistryKey<net.minecraft.world.biome.Biome> key = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, Objects.requireNonNull(event.getName()));
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);
        for (BiomeDictionary.Type t : Biome) {
            if (types.contains(t)) {
                event.getGeneration().getFeatures(GenerationStage.Decoration.SURFACE_STRUCTURES).add(() -> feature.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
            }
        }
    }
}