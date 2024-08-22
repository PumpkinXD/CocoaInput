package jp.axer.cocoainput.neoforge.util.neoforge;

import net.neoforged.fml.ModList;
import jp.axer.cocoainput.util.ModDetector;

public class ModDetectorNeoforgeImpl implements ModDetector {

    @Override
    public boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }
}
