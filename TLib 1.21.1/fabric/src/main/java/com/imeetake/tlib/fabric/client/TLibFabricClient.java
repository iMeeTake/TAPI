package com.imeetake.tlib.fabric.client;

import com.imeetake.tlib.client.TLibClient;
import net.fabricmc.api.ClientModInitializer;

public final class TLibFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TLibClient.init();
    }
}
