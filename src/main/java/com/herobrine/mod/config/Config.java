package com.herobrine.mod.config;

import com.herobrine.mod.HerobrineMod;

@net.minecraftforge.common.config.Config(modid = HerobrineMod.MODID, name = "herobrine-common", type = net.minecraftforge.common.config.Config.Type.INSTANCE)
public class Config {
    @net.minecraftforge.common.config.Config.Name("AltarRequiresShrine")
    @net.minecraftforge.common.config.Config.Comment("Determines if the Altar of Herobrine needs to be built on a shrine to be activated.")
    @net.minecraftforge.common.config.Config.RequiresMcRestart
    public static boolean AltarRequiresShrine = true;

    @net.minecraftforge.common.config.Config.Name("HerobrineAlwaysSpawns")
    @net.minecraftforge.common.config.Config.Comment("If set to true Herobrine and infected mobs can exist even if Altar of Herobrine is not activated in a world.")
    @net.minecraftforge.common.config.Config.RequiresMcRestart
    public static boolean HerobrineAlwaysSpawns = false;

    @net.minecraftforge.common.config.Config.Name("BuilderBuilds")
    @net.minecraftforge.common.config.Config.Comment("Determines if the Herobrine Builder builds structures.")
    @net.minecraftforge.common.config.Config.RequiresMcRestart
    public static boolean BuilderBuilds = true;

    @net.minecraftforge.common.config.Config.Name("BedrockSwordDrops")
    @net.minecraftforge.common.config.Config.Comment("Determines if the Bedrock Sword can be obtained in Survival as an extremely rare drop from the Herobrine Warrior.")
    @net.minecraftforge.common.config.Config.RequiresMcRestart
    public static boolean BedrockSwordDrops = false;

    @net.minecraftforge.common.config.Config.Name("WarriorBreaksBlocks")
    @net.minecraftforge.common.config.Config.Comment("Determines if the Herobrine Warrior can break blocks.")
    @net.minecraftforge.common.config.Config.RequiresMcRestart
    public static boolean WarriorBreaksBlocks = false;

    @net.minecraftforge.common.config.Config.RangeInt(min = 0, max = 1024)
    @net.minecraftforge.common.config.Config.Name("CursedForestBiomeWeight")
    @net.minecraftforge.common.config.Config.Comment("The spawn weight of the Cursed Forest biome. Greater values make the biome more common. Setting the value to 0 disables the biome, while 1024 makes the entire world a Cursed Forest.")
    @net.minecraftforge.common.config.Config.RequiresMcRestart
    public static int CursedForestBiomeWeight = 4;

    @net.minecraftforge.common.config.Config.RangeInt(min = 0, max = 1000000)
    @net.minecraftforge.common.config.Config.Name("TrappedHouseSpawnWeight")
    @net.minecraftforge.common.config.Config.Comment("The spawn weight of the Suspicious House structure. Greater values make the structure more common. Setting the value to 0 disables the structure, while 1000000 covers the entire world in Suspicious Houses.")
    @net.minecraftforge.common.config.Config.RequiresMcRestart
    public static int TrappedHouseSpawnWeight = 550;

    @net.minecraftforge.common.config.Config.RangeInt(min = 0, max = 1000000)
    @net.minecraftforge.common.config.Config.Name("ShrineRemnantSpawnWeight")
    @net.minecraftforge.common.config.Config.Comment("The spawn weight of the Shrine Remnant Structure. Greater values make the structure more common. Setting the value to 0 disables the structure, while 1000000 covers the entire world in Shrine Remnants.")
    @net.minecraftforge.common.config.Config.RequiresMcRestart
    public static int ShrineRemnantSpawnWeight = 200000;

    @net.minecraftforge.common.config.Config.RangeInt(min = 0, max = 1000000)
    @net.minecraftforge.common.config.Config.Name("StatueSpawnWeight")
    @net.minecraftforge.common.config.Config.Comment("The spawn weight of the Herobrine Statue Structure. Greater values make the structure more common. Setting the value to 0 disables the structure, while 1000000 covers the entire world in Herobrine Statues.")
    @net.minecraftforge.common.config.Config.RequiresMcRestart
    public static int StatueSpawnWeight = 50000;

    @net.minecraftforge.common.config.Config.RangeInt(min = 0, max = 1000000)
    @net.minecraftforge.common.config.Config.Name("SurvivorBaseSpawnWeight")
    @net.minecraftforge.common.config.Config.Comment("The spawn weight of the Survivor Base Structure. Greater values make the structure more common. Setting the value to 0 disables the structure, while 1000000 covers the entire world in Survivor Bases.")
    @net.minecraftforge.common.config.Config.RequiresMcRestart
    public static int SurvivorBaseSpawnWeight = 1000;
}