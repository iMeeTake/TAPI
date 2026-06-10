package com.imeetake.tlib.client.particle;

import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.ParticlesRenderState;

public class TParticleRenderer {

    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;
        initialized = true;

        ClientTickEvent.CLIENT_POST.register(client -> {
            if (client.level != null && !client.isPaused()) {
                TParticleManager.getInstance().tick();
            }
        });
    }

    public static void extractToRenderState(ParticlesRenderState particlesRenderState, Frustum frustum, Camera camera, float tickDelta) {
        if (TParticleManager.getInstance().isEmpty()) return;

        particlesRenderState.add(TParticleManager.getInstance().extractRenderState(camera, tickDelta));
    }

    public static void onLevelChange(net.minecraft.client.multiplayer.ClientLevel level) {
        TParticleManager.getInstance().setLevel(level);
    }
}