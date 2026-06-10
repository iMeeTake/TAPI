package com.imeetake.tlib.fabric;

import net.fabricmc.api.ModInitializer;

import com.imeetake.tlib.TLib;

public final class TLibFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        TLib.init();
    }
}
