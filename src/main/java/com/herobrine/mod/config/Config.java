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
        public final ForgeConfigSpec.IntValue CursedForestBiomeWeight;
        public final ForgeConfigSpec.IntValue TrappedHouseSpawnWeight;
        public final ForgeConfigSpec.BooleanValue BuilderBuilds;
        public final ForgeConfigSpec.BooleanValue BedrockSwordDrops;
        public final ForgeConfigSpec.BooleanValue AltarRequiresShrine;
        public final ForgeConfigSpec.IntValue ShrineRemnantSpawnWeight;
        public final ForgeConfigSpec.IntValue StatueSpawnWeight;
        public final ForgeConfigSpec.IntValue SurvivorBaseSpawnWeight;
        public CommonConfig(ForgeConfigSpec.@NotNull Builder builder) {
            builder.comment("Common config file for The Legend of Herobrine Minecraft mod.").push("entity_config");
            WarriorBreaksBlocks = builder.comment("Determines if the Herobrine Warrior can break blocks.").worldRestart().define("WarriorBreaksBlocks", true);
            HerobrineAlwaysSpawns = builder.comment("If set to true Herobrine and infected mobs can exist even if Altar of Herobrine is not activated in a world.").worldRestart().define("HerobrineAlwaysSpawns", false);
            BuilderBuilds = builder.comment("Determines if the Herobrine Builder builds structures.").worldRestart().define("BuilderBuilds", true);
            builder.pop();
            builder.push("biome_config");
            CursedForestBiomeWeight = builder.comment("The spawn weight of the Cursed Forest biome. Greater values make the biome more common. Setting the value to 0 disables the biome, while 1024 makes the entire world a Cursed Forest.").worldRestart().defineInRange("CursedForestBiomeWeight", 4, 0, 1024);
            builder.pop();
            builder.push("structure_config");
            TrappedHouseSpawnWeight = builder.comment("The spawn weight of the Suspicious House structure. Greater values make the structure more common. Setting the value to 0 disables the structure, while 1000000 covers the entire world in Suspicious Houses.").worldRestart().defineInRange("TrappedHouseSpawnWeight", 550, 0, 1000000);
            ShrineRemnantSpawnWeight = builder.comment("The spawn weight of the Shrine Remnant Structure. Greater values make the structure more common. Setting the value to 0 disables the structure, while 1000000 covers the entire world in Shrine Remnants.").worldRestart().defineInRange("ShrineRemnantSpawnWeight", 10000, 0, 1000000);
            StatueSpawnWeight = builder.comment("The spawn weight of the Herobrine Statue Structure. Greater values make the structure more common. Setting the value to 0 disables the structure, while 1000000 covers the entire world in Herobrine Statues.").worldRestart().defineInRange("StatueSpawnWeight", 50000, 0, 1000000);
            SurvivorBaseSpawnWeight = builder.comment("The spawn weight of the Survivor Base Structure. Greater values make the structure more common. Setting the value to 0 disables the structure, while 1000000 covers the entire world in Survivor Bases.").worldRestart().defineInRange("SurvivorBaseSpawnWeight", 1000, 0, 1000000);
            builder.pop();
            builder.push("item_config");
            BedrockSwordDrops = builder.comment("Determines if the Bedrock Sword can be obtained in Survival as an extremely rare drop from the Herobrine Warrior.").worldRestart().define("BedrockSwordDrops", false);
            builder.pop();
            builder.push("block_config");
            AltarRequiresShrine = builder.comment("Determines if the Altar of Herobrine needs to be built on a shrine to be activated.").define("AltarRequiresShrine", true);
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