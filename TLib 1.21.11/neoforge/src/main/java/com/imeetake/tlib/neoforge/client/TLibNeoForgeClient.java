package com.imeetake.tlib.neoforge.client;

import com.imeetake.tlib.TLib;
import com.imeetake.tlib.client.TLibClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = TLib.MOD_ID, value = Dist.CLIENT)
public final class TLibNeoForgeClient {

    private TLibNeoForgeClient() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        TLibClient.init();
    }
}
