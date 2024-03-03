package me.youm.morota.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import me.youm.morota.Morota;
import me.youm.morota.utils.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author YouM
 * Created on 2024/2/11
 */
public class RenderUtil {
    public static void drawImage(PoseStack poseStack, float x, float y, float width, float height, String image) {
        drawImage(poseStack, x, y, width, height, new ResourceLocation(Morota.MOD_ID, image));
    }

    public static void drawImage(PoseStack poseStack, float x, float y, float width, float height, ResourceLocation image) {
        RenderSystem.setShaderTexture(0, image);
        renderImage(poseStack.last().pose(), x, y, width, height, width, height, 0, 0);
    }

    public static void renderImage(Matrix4f matrix4f, float x, float y, float width, float height, float textureWidth, float textureHeight, float u, float v) {
        float widthProportion = 1.0F / textureWidth;
        float heightProportion = 1.0F / textureHeight;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f, x, y + height, 0.0f).uv(u * widthProportion, (v + height) * heightProportion).endVertex();
        bufferbuilder.vertex(matrix4f, (x + width), (y + height), 0.0f).uv((u + width) * widthProportion, (v + height) * heightProportion).endVertex();
        bufferbuilder.vertex(matrix4f, (x + width), y, 0.0f).uv((u + width) * widthProportion, v * heightProportion).endVertex();
        bufferbuilder.vertex(matrix4f, x, y, 0.0f).uv(u * widthProportion, v * heightProportion).endVertex();
        bufferbuilder.end();
        BufferUploader.end(bufferbuilder);
    }
    public static void drawFadeText(PoseStack poseStack, Font font, String text, int x, int y, int colorIndex, int speed, Color color){
        char[] charArray = text.toCharArray();
        AtomicInteger newX = new AtomicInteger(x);
        Util.repeat(charArray.length, index -> {
            String character = String.valueOf(charArray[index]);
            int width = font.width(character);
            Color fade = ColorUtil.fade(speed, index * colorIndex, color, color.getAlpha());
            drawText(poseStack,font,character,newX.get(),y,fade);
            newX.set(newX.get() + width);
        });

    }
    public static void drawText(PoseStack poseStack, Font font, String text, int x, int y, Color color) {
        GuiComponent.drawString(poseStack,font,text,x,y,color.getRGB());
    }
    public static void drawText(PoseStack poseStack, Font font, Component text, int x, int y, Color color) {
        GuiComponent.drawString(poseStack,font,text.getVisualOrderText(),x,y,color.getRGB());
    }
    public static void drawCenteredText(PoseStack poseStack, Font font, Component text, int x, int y, Color color) {
        drawText(poseStack,font,text,x - font.width(text.getVisualOrderText()) / 2,y,color);
    }
    public static void drawCenteredText(PoseStack poseStack, Font font, String text, int x, int y, Color color) {
        drawText(poseStack,font,text,x - font.width(text) / 2,y,color);
    }
}
