package com.herobrine.mod.config;

import com.herobrine.mod.HerobrineMod;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = HerobrineMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static class CommonConfig {
        public final ForgeConfigSpec.BooleanValue WarriorBreaksBlocks;
        public final ForgeConfigSpec.BooleanValue HerobrineAlwaysSpawns;
        public final ForgeConfigSpec.IntValue CursedForestWeight;
        public final ForgeConfigSpec.IntValue TrappedHouseWeight;
        public final ForgeConfigSpec.BooleanValue BuilderBuilds;
        public final ForgeConfigSpec.BooleanValue BedrockSwordDrops;
        public final ForgeConfigSpec.BooleanValue AltarRequiresShrine;
        public final ForgeConfigSpec.IntValue ShrineRemnantWeight;
        public final ForgeConfigSpec.IntValue HerobrineStatueWeight;
        public final ForgeConfigSpec.IntValue SurvivorBaseWeight;
        public final ForgeConfigSpec.IntValue HerobrineWarriorWeight;
        public final ForgeConfigSpec.IntValue HerobrineSpyWeight;
        public final ForgeConfigSpec.IntValue HerobrineBuilderWeight;
        public final ForgeConfigSpec.IntValue HerobrineMageWeight;
        public final ForgeConfigSpec.IntValue InfectedMobWeight;
        public final ForgeConfigSpec.IntValue HerobrineSpyObservationDistance;
        public final ForgeConfigSpec.IntValue HerobrineStalkerWeight;
        public final ForgeConfigSpec.IntValue GlowstonePillarWeight;
        public final ForgeConfigSpec.IntValue SandPyramidWeight;
        public final ForgeConfigSpec.IntValue HerobrineEndSpawnType;
        public final ForgeConfigSpec.BooleanValue GlobalHerobrineMessages;
        public CommonConfig(ForgeConfigSpec.@NotNull Builder builder) {
            builder.comment("Common config file for The Legend of Herobrine Minecraft mod.").push("entity_config");
            WarriorBreaksBlocks = builder.comment("Determines if the Herobrine warrior can break blocks.").worldRestart().define("WarriorBreaksBlocks", true);
            HerobrineAlwaysSpawns = builder.comment("If set to true Herobrine and infected mobs can exist even if Altar of Herobrine is not activated in a world.").worldRestart().define("HerobrineAlwaysSpawns", false);
            BuilderBuilds = builder.comment("Determines if the Herobrine Builder builds structures.").worldRestart().define("BuilderBuilds", true);
            HerobrineWarriorWeight = builder.comment("The spawn weight of the Herobrine warrior. Higher values makes it more common, while 0 disables it.").worldRestart().defineInRange("HerobrineWarriorWeight", 15, 0, 1024);
            HerobrineSpyWeight = builder.comment("The spawn weight of the Herobrine spy. Higher values makes it more common, while 0 disables it.").worldRestart().defineInRange("HerobrineSpyWeight", 25, 0, 1024);
            HerobrineBuilderWeight = builder.comment("The spawn weight of the Herobrine builder. Higher values makes it more common, while 0 disables it.").worldRestart().defineInRange("HerobrineBuilderWeight", 15, 0, 1024);
            HerobrineMageWeight = builder.comment("The spawn weight of the Herobrine mage. Higher values makes it more common, while 0 disables it.").worldRestart().defineInRange("HerobrineMageWeight", 15, 0, 1024);
            HerobrineStalkerWeight = builder.comment("The spawn weight of the Herobrine stalker. Higher values makes it more common, while 0 disables it.").worldRestart().defineInRange("HerobrineStalkerWeight", 20, 0, 1024);
            InfectedMobWeight = builder.comment("The spawn weight of Infected mobs. Higher values makes them more common, while 0 disables them.").worldRestart().defineInRange("InfectedMobWeight", 15, 0, 1024);
            HerobrineSpyObservationDistance = builder.comment("The distance in blocks the Herobrine spy observes the player from.").worldRestart().defineInRange("HerobrineSpyObservationDistance", 32, 0, 64);
            HerobrineEndSpawnType = builder.comment("Controls if Herobrine can spawn in the end. 0 makes Herobrine unable to spawn in the end. 1 makes only non hostile Herobrine types spawn (Stalker and Spy), 2 makes the builder spawn, and 3 makes all Herobrine types spawn. Herobrine will never spawn on the center island no matter what this is set to.").worldRestart().defineInRange("HerobrineEndSpawnType", 0, 0, 3);
            builder.pop();
            builder.push("biome_config");
            CursedForestWeight = builder.comment("The spawn weight of the Cursed Forest biome. Greater values make the biome more common. Setting the value to 0 disables the biome, while 1024 makes the entire world a Cursed Forest.").worldRestart().defineInRange("CursedForestWeight", 3, 0, 1024);
            builder.pop();
            builder.push("structure_config");
            TrappedHouseWeight = builder.comment("The spawn weight of the Trapped House structure. Greater values make the structure more common. Setting the value to 0 disables the structure, while 1000000 covers the entire world in Suspicious Houses.").worldRestart().defineInRange("TrappedHouseWeight", 450, 0, 1000000);
            ShrineRemnantWeight = builder.comment("The spawn weight of the Shrine Remnant Structure. Greater values make the structure more common. Setting the value to 0 disables the structure, while 1000000 covers the entire world in Shrine Remnants.").worldRestart().defineInRange("ShrineRemnantWeight", 5000, 0, 1000000);
            HerobrineStatueWeight = builder.comment("The spawn weight of the Herobrine Statue Structure. Greater values make the structure more common. Setting the value to 0 disables the structure, while 1000000 covers the entire world in Herobrine Statues.").worldRestart().defineInRange("HerobrineStatueWeight", 10000, 0, 1000000);
            SurvivorBaseWeight = builder.comment("The spawn weight of the Survivor Base Structure. Greater values make the structure more common. Setting the value to 0 disables the structure, while 1000000 covers the entire world in Survivor Bases.").worldRestart().defineInRange("SurvivorBaseWeight", 1000, 0, 1000000);
            GlowstonePillarWeight = builder.comment("The spawn weight of the Glowstone Pillar Structure. Greater values make the structure more common. Setting the value to 0 disables the structure, while 1000000 covers the entire world in Glowstone Pillars.").worldRestart().defineInRange("GlowstonePillarWeight", 500, 0, 1000000);
            SandPyramidWeight = builder.comment("The spawn weight of the Sand Pyramid Structure. Greater values make the structure more common. Setting the value to 0 disables the structure, while 1000000 covers the entire world in Sand Pyramids.").worldRestart().defineInRange("SandPyramidWeight", 6000, 0, 1000000);
            builder.pop();
            builder.push("item_config");
            BedrockSwordDrops = builder.comment("Determines if the Bedrock Sword can be obtained in Survival as an extremely rare drop from the Herobrine Warrior.").worldRestart().define("BedrockSwordDrops", false);
            builder.pop();
            builder.push("block_config");
            AltarRequiresShrine = builder.comment("Determines if the Altar of Herobrine needs to be built on a shrine to be activated.").define("AltarRequiresShrine", true);
            builder.pop();
            builder.push("misc");
            GlobalHerobrineMessages = builder.comment("If set to true makes chat messages from summoning and removing Herobrine appear to all players instead of just the player that is responsible for it.").worldRestart().define("GlobalHerobrineMessages", true);
            builder.pop();
        }
    }
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final CommonConfig COMMON;
    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}