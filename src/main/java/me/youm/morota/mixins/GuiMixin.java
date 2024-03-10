package me.youm.morota.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.youm.morota.utils.render.ColorUtil;
import me.youm.morota.utils.render.RenderUtil;
import me.youm.morota.world.register.ItemRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author YouM
 * Created on 2024/3/2
 */
@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow
    protected ItemStack lastToolHighlight;
    @Shadow
    @Final
    protected Minecraft minecraft;
    @Shadow
    protected int toolHighlightTimer;

    @Shadow public abstract Font getFont();

    @Shadow protected int screenWidth;

    @Shadow protected int screenHeight;

    /**
     * @author YouM
     * @reason sword render
     */
    @Overwrite
    public void renderSelectedItemName(PoseStack pPoseStack) {
        this.minecraft.getProfiler().push("selectedItemName");
        if(!this.lastToolHighlight.isEmpty()){
            MutableComponent mutablecomponent = (new TextComponent("")).append(this.lastToolHighlight.getHoverName()).withStyle(this.lastToolHighlight.getRarity().getStyleModifier());
            if (this.lastToolHighlight.hasCustomHoverName()) {
                mutablecomponent.withStyle(ChatFormatting.ITALIC);
            }

            Component highlightTip = this.lastToolHighlight.getHighlightTip(mutablecomponent);

            int i = this.getFont().width(highlightTip);
            int j = (this.screenWidth - i) / 2;
            int k = this.screenHeight - 59;
            if (this.minecraft.gameMode != null && !this.minecraft.gameMode.canHurtPlayer()) {
                k += 14;
            }
            if (this.toolHighlightTimer > 0) {


                int l = (int)((float)this.toolHighlightTimer * 256.0F / 10.0F);
                if (l > 255) {
                    l = 255;
                }
                if (l > 0 && !lastToolHighlight.is(ItemRegister.MOROTA_SWORD.get())){
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    Gui.fill(pPoseStack, j - 2, k - 2, j + i + 2, k + 9 + 2, this.minecraft.options.getBackgroundColor(0));
                    Font font = net.minecraftforge.client.RenderProperties.get(lastToolHighlight).getFont(lastToolHighlight);
                    if (font == null) {
                        this.getFont().drawShadow(pPoseStack, highlightTip, (float)j, (float)k, 16777215 + (l << 24));
                    } else {
                        j = (this.screenWidth - font.width(highlightTip)) / 2;
                        font.drawShadow(pPoseStack, highlightTip, (float)j, (float)k, 16777215 + (l << 24));
                    }
                    RenderSystem.disableBlend();
                }

            }
            if(lastToolHighlight.is(ItemRegister.MOROTA_SWORD.get())){
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderUtil.drawFadeText(pPoseStack,getFont(), I18n.get("item.morota.morota_sword"),j,k,15,10, ColorUtil.THEME);
                RenderSystem.disableBlend();
            }
        }

        this.minecraft.getProfiler().pop();
    }
}