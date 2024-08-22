package jp.axer.cocoainput.fabriclike.util.fabriclike;

import jp.axer.cocoainput.util.ModDetector;
import net.fabricmc.loader.api.FabricLoader;

public class ModetectorFabricLikeImpl implements ModDetector {

    @Override
    public boolean isModLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }
}
