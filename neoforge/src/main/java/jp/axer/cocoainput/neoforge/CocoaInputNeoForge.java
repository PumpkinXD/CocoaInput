package jp.axer.cocoainput.neoforge;

import jp.axer.cocoainput.neoforge.util.neoforge.ModDetectorNeoforgeImpl;
import net.neoforged.fml.common.Mod;

import jp.axer.cocoainput.CocoaInput;

@Mod(CocoaInput.MOD_ID)
public final class CocoaInputNeoForge {
    public CocoaInputNeoForge() {
        // Run our common setup.
        CocoaInput.init(new ModDetectorNeoforgeImpl());
    }
}
