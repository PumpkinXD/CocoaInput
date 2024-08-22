package jp.axer.cocoainput.fabric;

import net.fabricmc.api.ModInitializer;

import jp.axer.cocoainput.fabriclike.CocoaInputFabricLike;

public final class CocoaInputFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run the Fabric-like setup.
        CocoaInputFabricLike.init();
    }
}
