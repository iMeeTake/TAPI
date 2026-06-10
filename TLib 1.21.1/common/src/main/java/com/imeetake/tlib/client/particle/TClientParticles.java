package com.imeetake.tlib.client.particle;

import com.imeetake.tlib.client.render.TClientRenderUtils;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;

public class TClientParticles {
    public static final double DEFAULT_CULL_DISTANCE_SQR = 1024.0D;

    private static final String PARTICLES_DECREASED = "DECREASED";
    private static final String PARTICLES_MINIMAL = "MINIMAL";

    public static void spawn(ParticleOptions effect, double x, double y, double z) {
        spawn(effect, x, y, z, 0, 0, 0);
    }

    public static void spawn(ParticleOptions effect, double x, double y, double z, double dx, double dy, double dz) {
        Minecraft client = Minecraft.getInstance();
        if (client.level != null) {
            client.level.addParticle(effect, x, y, z, dx, dy, dz);
        }
    }

    public static void spawn(RegistrySupplier<? extends SimpleParticleType> type, double x, double y, double z) {
        spawn(type, x, y, z, 0, 0, 0);
    }

    public static void spawn(RegistrySupplier<? extends SimpleParticleType> type, double x, double y, double z, double dx, double dy, double dz) {
        Minecraft client = Minecraft.getInstance();
        ClientLevel level = client.level;
        if (level == null) return;

        Particle particle = TParticleProviders.createParticle(type, level, x, y, z, dx, dy, dz);
        if (particle != null) {
            client.particleEngine.add(particle);
            return;
        }

        level.addParticle(type.get(), x, y, z, dx, dy, dz);
    }

    public static void spawnCulled(ParticleOptions effect, double x, double y, double z) {
        spawnCulled(effect, x, y, z, 0, 0, 0);
    }

    public static void spawnCulled(ParticleOptions effect, double x, double y, double z, double dx, double dy, double dz) {
        Minecraft client = Minecraft.getInstance();
        ClientLevel level = client.level;
        if (level == null || shouldCull(client, level, x, y, z, DEFAULT_CULL_DISTANCE_SQR)) return;

        spawn(effect, x, y, z, dx, dy, dz);
    }

    public static void spawnCulled(RegistrySupplier<? extends SimpleParticleType> type, double x, double y, double z) {
        spawnCulled(type, x, y, z, 0, 0, 0);
    }

    public static void spawnCulled(RegistrySupplier<? extends SimpleParticleType> type, double x, double y, double z, double dx, double dy, double dz) {
        Minecraft client = Minecraft.getInstance();
        ClientLevel level = client.level;
        if (level == null || shouldCull(client, level, x, y, z, DEFAULT_CULL_DISTANCE_SQR)) return;

        spawn(type, x, y, z, dx, dy, dz);
    }

    public static boolean shouldCull(double x, double y, double z) {
        Minecraft client = Minecraft.getInstance();
        ClientLevel level = client.level;
        return level == null || shouldCull(client, level, x, y, z, DEFAULT_CULL_DISTANCE_SQR);
    }

    public static boolean shouldCull(double x, double y, double z, double maxDistanceSqr) {
        Minecraft client = Minecraft.getInstance();
        ClientLevel level = client.level;
        return level == null || shouldCull(client, level, x, y, z, maxDistanceSqr);
    }

    private static boolean shouldCull(Minecraft client, ClientLevel level, double x, double y, double z, double maxDistanceSqr) {
        Vec3 cameraPos = TClientRenderUtils.getCameraPosition();
        if (cameraPos.distanceToSqr(x, y, z) > maxDistanceSqr) return true;

        return switch (particleStatusName(client)) {
            case PARTICLES_DECREASED -> level.random.nextInt(3) == 0;
            case PARTICLES_MINIMAL -> true;
            default -> false;
        };
    }

    private static String particleStatusName(Minecraft client) {
        Object status = client.options.particles().get();
        return status instanceof Enum<?> enumStatus ? enumStatus.name() : "";
    }
}
