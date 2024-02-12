package me.youm.morota.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import me.youm.morota.Morota;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

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
}
