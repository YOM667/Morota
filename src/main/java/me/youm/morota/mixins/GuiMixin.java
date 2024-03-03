package me.youm.morota.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import me.youm.morota.utils.render.ColorUtil;
import me.youm.morota.utils.render.RenderUtil;
import me.youm.morota.world.register.ItemRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;

/**
 * @author YouM
 * Created on 2024/3/2
 */
@Mixin(Gui.class)
public class GuiMixin {
    @Shadow
    protected ItemStack lastToolHighlight;
    @Redirect(
            method = "renderSelectedItemName",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Font;drawShadow(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/network/chat/Component;FFI)I"
            )
    )
    public int redirect_drawShadow(Font instance, PoseStack pPoseStack, Component pText, float pX, float pY, int pColor) {
        //get pColor alpha value
        int alpha = new Color(pColor).getAlpha();
        if(lastToolHighlight.is(ItemRegister.MOROTA_SWORD.get())){
            RenderUtil.drawFadeText(pPoseStack, Minecraft.getInstance().font, I18n.get("item.morota.morota_sword"), (int) pX, (int) pY, 20,10, new Color(ColorUtil.theme.getRed(), ColorUtil.theme.getGreen(), ColorUtil.theme.getBlue(), alpha));
        }else{
            return instance.drawShadow(pPoseStack, pText, pX, pY, pColor);
        }
        return 0;
    }
}