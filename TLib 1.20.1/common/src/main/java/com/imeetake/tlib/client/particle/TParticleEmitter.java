package com.imeetake.tlib.client.particle;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.phys.AABB;

import java.util.Random;

public class TParticleEmitter {

    private static final Random RANDOM = new Random();

    public static void sphere(ParticleOptions effect, double cx, double cy, double cz, double radius, int count) {
        for (int i = 0; i < count; i++) {
            double costheta = 2 * RANDOM.nextDouble() - 1;
            double phi = 2 * Math.PI * RANDOM.nextDouble();
            double r = radius * Math.cbrt(RANDOM.nextDouble());
            double sinTheta = Math.sqrt(1 - costheta * costheta);

            double x = cx + r * sinTheta * Math.cos(phi);
            double y = cy + r * sinTheta * Math.sin(phi);
            double z = cz + r * costheta;

            TClientParticles.spawn(effect, x, y, z);
        }
    }

    public static void cube(ParticleOptions effect,
                            double minX, double minY, double minZ,
                            double maxX, double maxY, double maxZ,
                            int count) {
        for (int i = 0; i < count; i++) {
            double x = minX + RANDOM.nextDouble() * (maxX - minX);
            double y = minY + RANDOM.nextDouble() * (maxY - minY);
            double z = minZ + RANDOM.nextDouble() * (maxZ - minZ);

            TClientParticles.spawn(effect, x, y, z);
        }
    }

    public static void aabb(ParticleOptions effect, AABB box, int count) {
        cube(effect,
                box.minX, box.minY, box.minZ,
                box.maxX, box.maxY, box.maxZ,
                count);
    }
}