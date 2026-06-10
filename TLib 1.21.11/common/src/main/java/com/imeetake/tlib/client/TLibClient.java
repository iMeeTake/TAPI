package com.imeetake.tlib.client;

import com.imeetake.tlib.client.particle.TParticleRenderer;

public final class TLibClient {

    private TLibClient() {
    }

    public static void init() {
        TParticleRenderer.init();
    }
}
