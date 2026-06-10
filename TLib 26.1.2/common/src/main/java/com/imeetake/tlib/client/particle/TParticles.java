package com.imeetake.tlib.client.particle;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

import java.util.function.Function;

public final class TParticles {

    private TParticles() {
    }

    @FunctionalInterface
    public interface OrientedParticleFactory<T extends ParticleOptions> {
        TOrientedParticle<T> create(ClientLevel level, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet);
    }

    @FunctionalInterface
    public interface SimpleParticleFactory<T extends ParticleOptions> {
        Particle create(ClientLevel level, double x, double y, double z, double vx, double vy, double vz);
    }

    public static <T extends ParticleOptions> ParticleProvider<T> provider(
            RegistrySupplier<? extends ParticleType<T>> type,
            SpriteSet sprites,
            Function<SpriteSet, ParticleProvider<T>> factory
    ) {
        return TParticleProviders.provider(type, sprites, factory);
    }

    public static <T extends ParticleOptions> ParticleProvider<T> orientedProvider(
            RegistrySupplier<? extends ParticleType<T>> type,
            SpriteSet sprites,
            OrientedParticleFactory<T> factory
    ) {
        return TParticleProviders.orientedProvider(type, sprites, factory::create);
    }

    public static <T extends ParticleOptions> ParticleProvider<T> orientedProvider(
            ParticleType<T> type,
            SpriteSet sprites,
            OrientedParticleFactory<T> factory
    ) {
        return TParticleProviders.orientedProvider(type, sprites, factory::create);
    }
}
