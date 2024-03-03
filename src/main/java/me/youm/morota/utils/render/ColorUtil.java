package me.youm.morota.utils.render;

import java.awt.*;

/**
 * @author YouM
 * Created on 2024/3/1
 */
public class ColorUtil {
    public static final Color theme = new Color(94,94,230);
    public static Color fade(int speed, int index, Color color, int alpha) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        int angle = (int) ((System.currentTimeMillis() / speed + index) % 360);
        angle = (angle > 180 ? 360 - angle : angle) + 180;

        Color colorHSB = new Color(Color.HSBtoRGB(hsb[0], hsb[1], angle / 360f));

        return new Color(colorHSB.getRed(), colorHSB.getGreen(), colorHSB.getBlue(), alpha);
    }
}
