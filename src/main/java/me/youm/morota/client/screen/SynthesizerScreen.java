package me.youm.morota.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import me.youm.morota.Morota;
import me.youm.morota.utils.render.RenderUtil;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * @author YouM
 * Created on 2024/2/17
 */
public class SynthesizerScreen extends AbstractContainerScreen<SynthesizerMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Morota.MOD_ID,"textures/gui/synthesizer_gui.png");
    public SynthesizerScreen(SynthesizerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderUtil.drawImage(poseStack,(width - imageWidth) / 2.0f,(height - imageHeight) / 2.0f,imageWidth,imageHeight,TEXTURE);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        renderTooltip(poseStack, mouseX, mouseY);
    }
}
