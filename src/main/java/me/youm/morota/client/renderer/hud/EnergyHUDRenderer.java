package me.youm.morota.client.renderer.hud;

import me.youm.morota.Morota;
import me.youm.morota.utils.player.PlayerUtil;
import me.youm.morota.utils.render.RenderUtil;
import me.youm.morota.world.player.capability.MorotaEntityEnergyCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;


/**
 * @author YouM
 * Created on 2024/2/10
 */
public class EnergyHUDRenderer {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(Morota.MOD_ID, "textures/gui/energy_hud_background.png");
    private static final ResourceLocation PROGRESS = new ResourceLocation(Morota.MOD_ID, "textures/gui/energy_progress.png");
    public static final IHUDRenderer render = (((poseStack, partialTick, width, height) -> {
        assert Minecraft.getInstance().player != null;
        MorotaEntityEnergyCapability capability = PlayerUtil.getMorotaCapability(Minecraft.getInstance().player);
        RenderUtil.drawImage(poseStack, width - 203, 11,  ((float) capability.getMorotaEnergy() / capability.getMaxEnergy() * 196.0f), 8, PROGRESS);
        RenderUtil.drawImage(poseStack, width - 205, 5, 200, 20, BACKGROUND);
        RenderUtil.drawCenteredText(poseStack, Morota.fontLoader.siyuan, capability.getMorotaEnergy() + " %", width - 103, 11, new Color(255, 255, 255));
    }));
}
