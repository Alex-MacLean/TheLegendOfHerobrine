# TheLegendOfHerobrine
**Homepage**: https://herobrinemod.com

**CurseForge**: https://www.curseforge.com/minecraft/mc-mods/the-legend-of-herobrine

**Modrinth**: https://modrinth.com/mod/the-legend-of-herobrine

The Legend of Herobrine is a mod designed for modern versions of Minecraft that aims to add Herobrine to the game with many new gameplay features while also fitting in with vanilla gameplay and keeping the scary theme surrounding Herobrine while remaining relevant and fun in larger modpacks. Currently in beta. Major bugs may arise!

For a full description and access to preview builds visit: https://www.herobrinemod.com/about

Screenshots: https://www.herobrinemod.com/screenshots

FAQ: https://www.herobrinemod.com/faq

Programmer Art Style Resource Pack: https://github.com/Alex-MacLean/TheLegendOfHerobrine/releases/download/0.6.0/Herobrine-Programmer-Art.zip

**How to build this mod from the source code:**

(Warning: advanced users only!)

**Prerequisites:**

JDK 1.8.0 (Requires Oracle accout to download): https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html

(Windows Only) Add Java to path environment variable: https://www.javatpoint.com/how-to-set-path-in-java (Use second, permanent method)

IDE with Java support. I use Intellij Idea: https://www.jetbrains.com/idea/download/

**Step 1:** Download the source code of the desired branch as a zip file.

**Step 2:** Extract the zip file.

**Step 3:** Open build.gradle in your IDE. This file is located in the base directory of the source code. In Intellij Idea press open and browse to where you extracted the code locate build.gradle and open it

**Setp 4:** In the gradle processes window sync/reload the project if your IDE doesn't do it automatically. In Intellij Idea it is the arrow circle button in the top left corner.

**Setp 5:** The gradle processes window should now be populated with directories and processes. Go to Tasks -> fg_runs and run gen*name of your ide*Runs (For intellij idea run genIntellijRuns). This should download the rest of the required files to begin building the mod.

**Setp 6:** In the gradle window go to Tasks -> build and run build. If everything is set up properly it should build the mod file.

**Step 7:** The jar file is located in build/libs/LegendOfHerobrine-*VERSION*.jar. You can install this file like any other forge mod.

**Permissions:**

You are allowed to use this mod in your modpack. This is the only way you can redistribute the bianaries of this mod on your own.
You are allowed to modify this code and use it for personal use, but not redistribute this code in any way.
You are allowed to use this code as a reference for implementing similar, but not identical features in another mod.
You are not allowed to redistribute recompiled bianaries from this source code, no matter how much modifications are made.
If you want to contribute to this project feel free to contact me with whatever modifications you have made, if your code makes it into the mod I will add you to the credits section of the mcmod.info and mods.toml files.
If you have any questions about this mod or permissions feel free to DM me on curseforge GitHub.
