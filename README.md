# The Legend of Herobrine
**Homepage**: https://herobrinemod.com

**Modrinth**: https://modrinth.com/mod/the-legend-of-herobrine

The Legend of Herobrine is a mod designed for modern versions of Minecraft that aims to add Herobrine to the game with many new gameplay features while also fitting in with vanilla gameplay and keeping the scary theme surrounding Herobrine while remaining relevant and fun in larger modpacks. Currently in beta. Major bugs may arise!

For a full description visit: https://www.herobrinemod.com/about

Preview Builds are hosted here: https://github.com/Alex-MacLean/TheLegendOfHerobrine-Preview/releases

Screenshots: https://www.herobrinemod.com/screenshots

FAQ: https://www.herobrinemod.com/faq

Programmer Art Style Resource Pack: https://github.com/Alex-MacLean/TheLegendOfHerobrine/releases/download/0.6.0/Herobrine-Programmer-Art.zip

This GitHub repository, the offical preview builds repository, the official Modrinth page, the official legacy (outdated) CurseForge page are the ONLY safe places to download this mod, any other sources should be considered packaged with viruses. Furthermore, If any newer version of this mod (Fabric 1.19 previews or newer) makes it anywhere other than my GitHub repositories, the official website, or modrinth, it should also be considered a virus, even if the newer version is on CurseForge. Viruses being spread through infected Minecraft mods have been on the rise, especially on shady websites, and CurseForge so please try to stay safe and download only from official sources.

**How to build this mod from source:**

**Prerequisites:**

JDK 17 (17.0.1 or newer): (Oracle JDK) https://www.oracle.com/java/technologies/downloads/#jdk17 (OpenJDK) https://jdk.java.net/archive/

JavaFX 17: https://gluonhq.com/products/javafx/

If you use GNU/Linux install the latest versions of JDK 17 and OpenJFX 17 from your distros package repositories.

(Windows Only) Add Java to path environment variable: https://www.javatpoint.com/how-to-set-path-in-java (Use second, permanent method)

IDE with Java support. I use Intellij Idea: https://www.jetbrains.com/idea/download/ (If you are using GNU/Linux your preferred IDE should be in your distros package repositories, if not see if they have an appimage or build and install it from source or choose an IDE available in your distros package repositories)

**Building**

**Step 1:** (Required) Download the source code of the desired branch as a zip file.

**Step 2:** (Required) Extract the zip file.

**Step 3:** (Required) Open build.gradle as a project in your IDE. This file is located in the base directory of the source code. In Intellij Idea press open and browse to where you extracted the code locate build.gradle and open it

**Setp 4:** (Required) In the gradle processes window sync/reload the project if your IDE doesn't do it automatically. In Intellij Idea it is the arrow circle button in the top left corner.

**Setp 5:** (Optional, Recommended) The gradle processes window should now be populated with directories and processes. Go to Tasks -> fabric and run genSources to generate the sources for Vanilla Minecraft. You may have to choose the sources file to use in your IDE. Choose the file with "-sources" appended to the end. Your IDE should be able to locate the directory automatically

**Step 6:** (Optional, Intellij Idea, Source Code Testing) If run configurations do not appear and you are using Intellij Idea go to Tasks -> fabric and run ideaSyncTask

**Setp 7:** (Required) In the gradle window go to Tasks -> build and run build. If everything is set up properly it should build the mod file.

**Step 8:** (Info) The jar file is located in .../build/libs/LegendOfHerobrine-*VERSION*.jar. You can install this file like any other mod.

**Permissions:**

You are allowed to use this mod in your modpack. This is the only way you can redistribute the bianaries of this mod on your own.

You are allowed to modify this code and use it for personal use, but not redistribute this code in any way.

You are allowed to use this code as a reference for implementing similar, but not identical features in another mod.

You are not allowed to redistribute recompiled bianaries from this source code, no matter how much modifications are made.

If you want to contribute to this project feel free to contact me with whatever modifications you have made, if your code makes it into the mod I will add you to the credits section of the fabric.mod.json file.

For questions about any other restrictions or permissions not stated here, please refer to the license (LGPLv3).

If you have any questions about this mod or your permissions feel free to send me an email or DM me on Modrinth or GitHub.
