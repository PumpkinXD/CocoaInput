package jp.axer.cocoainput.fabriclike;

import jp.axer.cocoainput.CocoaInput;
import jp.axer.cocoainput.fabriclike.util.fabriclike.ModetectorFabricLikeImpl;

public final class CocoaInputFabricLike {
    public static void init() {
        // Run our common setup.
        CocoaInput.init(new ModetectorFabricLikeImpl());
    }
}
