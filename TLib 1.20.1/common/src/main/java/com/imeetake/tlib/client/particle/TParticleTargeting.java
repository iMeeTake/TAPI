package com.imeetake.tlib.client.particle;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.phys.Vec3;

public final class TParticleTargeting {

    private TParticleTargeting() {}

    public static Vec3 direction(double sx, double sy, double sz, double tx, double ty, double tz) {
        Vec3 diff = new Vec3(tx - sx, ty - sy, tz - sz);
        double len = diff.length();
        return len == 0 ? Vec3.ZERO : diff.scale(1.0 / len);
    }

    public static void spawnTowards(ParticleOptions effect,
                                    double sx, double sy, double sz,
                                    double tx, double ty, double tz,
                                    double speed) {
        Vec3 dir = direction(sx, sy, sz, tx, ty, tz);
        TClientParticles.spawn(effect,
                sx, sy, sz,
                dir.x * speed, dir.y * speed, dir.z * speed
        );
    }

    public static void spawnAway(ParticleOptions effect,
                                 double sx, double sy, double sz,
                                 double tx, double ty, double tz,
                                 double speed) {
        Vec3 dir = direction(sx, sy, sz, tx, ty, tz).scale(-1.0);
        TClientParticles.spawn(effect,
                sx, sy, sz,
                dir.x * speed, dir.y * speed, dir.z * speed
        );
    }

    public static void spawnUp(ParticleOptions effect,
                               double x, double y, double z,
                               double speed) {
        TClientParticles.spawn(effect,
                x, y, z,
                0, speed, 0
        );
    }

    public static void spawnDown(ParticleOptions effect,
                                 double x, double y, double z,
                                 double speed) {
        TClientParticles.spawn(effect,
                x, y, z,
                0, -speed, 0
        );
    }
}