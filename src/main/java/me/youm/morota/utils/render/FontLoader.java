package me.youm.morota.utils.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.resources.ResourceLocation;

/**
 * @author YouM
 * Created on 2024/2/11
 */
public class FontLoader {
    public final ResourceLocation siyuanResource = new ResourceLocation("font");
    public final Font siyuan;
    public FontLoader(){
        siyuan = getFontRenderer(siyuanResource);
    }
    public Font getFontRenderer(ResourceLocation fontId) {
        FontManager fontManager = Minecraft.getInstance().fontManager;
        return new Font((resourceLocation) ->
                fontManager.fontSets.getOrDefault(fontId, fontManager.missingFontSet)
        );
    }
}
