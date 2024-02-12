package me.youm.morota.client.renderer.hud;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YouM
 * Created on 2024/2/11
 */
public class HUDRendererManager {
    public List<IHUDRenderer> hudList = new ArrayList<>();
    public void load(){
        hudList.add(EnergyHUDRenderer.render);
    }
}
