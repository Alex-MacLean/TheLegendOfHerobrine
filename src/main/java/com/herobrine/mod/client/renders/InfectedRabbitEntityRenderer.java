package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedRabbitEntityModel;
import com.herobrine.mod.entities.InfectedRabbitEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class InfectedRabbitEntityRenderer extends RenderLiving<InfectedRabbitEntity> {
    private static final ResourceLocation BROWN = new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_rabbit/brown.png");
    private static final ResourceLocation WHITE = new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_rabbit/white.png");
    private static final ResourceLocation BLACK = new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_rabbit/black.png");
    private static final ResourceLocation GOLD = new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_rabbit/gold.png");
    private static final ResourceLocation SALT = new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_rabbit/salt.png");
    private static final ResourceLocation WHITE_SPLOTCHED = new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_rabbit/white_splotched.png");
    private static final ResourceLocation TOAST = new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_rabbit/toast.png");
    private static final ResourceLocation CAERBANNOG = new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_rabbit/caerbannog.png");

    public InfectedRabbitEntityRenderer(RenderManager manager) {
        super(manager, new InfectedRabbitEntityModel(), 0.3F);
    }

    @Override
    protected ResourceLocation getEntityTexture(@NotNull InfectedRabbitEntity entity) {
        String s = TextFormatting.getTextWithoutFormattingCodes(entity.getName());
        if ("Toast".equals(s)) {
            return TOAST;
        }else {
            switch (entity.getRabbitType()) {
                case 0:
                default:
                    return BROWN;
                case 1:
                    return WHITE;
                case 2:
                    return BLACK;
                case 3:
                    return WHITE_SPLOTCHED;
                case 4:
                    return GOLD;
                case 5:
                    return SALT;
                case 99:
                    return CAERBANNOG;
            }
        }
    }
}
