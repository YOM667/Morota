package me.youm.morota.client.renderer.hud;

import com.mojang.blaze3d.vertex.PoseStack;

/**
 * @author YouM
 * Created on 2024/2/11
 */
@FunctionalInterface
public interface IHUDRenderer {
    void render(PoseStack poseStack, float partialTick, int width, int height);
    Long
}

