package me.youm.morota.client.renderer.hud;

import me.youm.morota.Morota;
import me.youm.morota.utils.player.PlayerUtil;
import me.youm.morota.utils.render.RenderUtil;
import me.youm.morota.world.player.capability.MorotaEntityEnergyCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;


/**
 * @author YouM
 * Created on 2024/2/10
 */
public class EnergyHUDRenderer {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(Morota.MOD_ID, "textures/gui/energy_hud_background.png");
    private static final ResourceLocation PROGRESS = new ResourceLocation(Morota.MOD_ID, "textures/gui/energy_progress.png");
    public static final IHUDRenderer render = (((poseStack, partialTick, width, height) -> {
        MorotaEntityEnergyCapability capability = PlayerUtil.getMorotaEntityEnergyCapability(Minecraft.getInstance().player);

        RenderUtil.drawImage(
                poseStack,
                width - 203,
                11,
                (capability.getMorotaEnergy() / 100.0f) * 196.0f,
                8,
                PROGRESS
        );
        RenderUtil.drawImage(
                poseStack,
                width - 205,
                5,
                200,
                20,
                BACKGROUND
        );
        GuiComponent.drawCenteredString(
                poseStack,
                Morota.fontLoader.siyuan,
                capability.getMorotaEnergy() + " %",
                width - 103,
                11,
                0xFFFFFF
        );

    }));

}
