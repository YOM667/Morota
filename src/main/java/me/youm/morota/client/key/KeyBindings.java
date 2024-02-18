package me.youm.morota.client.key;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

/**
 * @author YouM
 * Created on 2024/2/13
 */
// TODO add keybindings
public class KeyBindings {
    public static final KeyMapping SPECIAL_ATTACK = new KeyMapping(
            "key.morota.special_attack",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            "key_category.morota.special_attack"
    );
}
