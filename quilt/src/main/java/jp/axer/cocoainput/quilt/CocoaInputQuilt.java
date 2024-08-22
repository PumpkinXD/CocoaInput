package jp.axer.cocoainput.quilt;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import jp.axer.cocoainput.fabriclike.CocoaInputFabricLike;

public final class CocoaInputQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        // Run the Fabric-like setup.
        CocoaInputFabricLike.init();
    }
}
