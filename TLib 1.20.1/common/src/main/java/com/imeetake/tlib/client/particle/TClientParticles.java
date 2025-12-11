package com.imeetake.tlib.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;

public class TClientParticles {

    public static void spawn(ParticleOptions effect, double x, double y, double z) {
        spawn(effect, x, y, z, 0, 0, 0);
    }

    public static void spawn(ParticleOptions effect, double x, double y, double z, double dx, double dy, double dz) {
        Minecraft client = Minecraft.getInstance();
        if (client.level != null) {
            client.level.addParticle(effect, x, y, z, dx, dy, dz);
        }
    }
}