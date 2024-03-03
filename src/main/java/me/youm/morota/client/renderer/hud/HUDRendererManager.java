package me.youm.morota.client.renderer.hud;

import com.mojang.blaze3d.platform.Window;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YouM
 * Created on 2024/2/11
 */
public class HUDRendererManager {
    private final List<IHUDRenderer> hudList = new ArrayList<>();
    public HUDRendererManager(){
        hudList.add(EnergyHUDRenderer.render);
    }
    public void load(RenderGameOverlayEvent event){
        Window window = event.getWindow();
        this.hudList.forEach(renderer -> renderer.render(
                event.getMatrixStack(),
                event.getPartialTicks(),
                window.getGuiScaledWidth(),
                window.getGuiScaledHeight()
        ));
    }
}
