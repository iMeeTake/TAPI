package com.imeetake.tlib.client.particle;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.RandomSource;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;

public final class TParticleProviders {
    private static final Map<ParticleType<?>, ParticleProvider<?>> PROVIDERS = new IdentityHashMap<>();

    private TParticleProviders() {
    }

    public static <T extends ParticleOptions> ParticleProvider<T> provider(
            RegistrySupplier<? extends ParticleType<T>> type,
            SpriteSet sprites,
            Function<SpriteSet, ParticleProvider<T>> factory
    ) {
        return provider(type.get(), sprites, factory);
    }

    public static <T extends ParticleOptions> ParticleProvider<T> provider(
            ParticleType<T> type,
            SpriteSet sprites,
            Function<SpriteSet, ParticleProvider<T>> factory
    ) {
        ParticleProvider<T> provider = factory.apply(sprites);
        PROVIDERS.put(type, provider);
        return provider;
    }

    public static <T extends ParticleOptions> ParticleProvider<T> simpleProvider(
            ParticleType<T> type,
            SpriteSet sprites,
            SimpleParticleFactory<T> factory
    ) {
        return provider(type, sprites, ignored -> (parameters, level, x, y, z, dx, dy, dz, random) ->
                factory.create(parameters, level, x, y, z, dx, dy, dz, sprites, random));
    }

    public static <T extends ParticleOptions> ParticleProvider<T> orientedProvider(
            RegistrySupplier<? extends ParticleType<T>> type,
            SpriteSet sprites,
            OrientedParticleFactory<T> factory
    ) {
        return orientedProvider(type.get(), sprites, factory);
    }

    public static <T extends ParticleOptions> ParticleProvider<T> orientedProvider(
            ParticleType<T> type,
            SpriteSet sprites,
            OrientedParticleFactory<T> factory
    ) {
        TClientParticles.registerOriented(type, factory::create, sprites);
        return provider(type, sprites, ignored -> (parameters, level, x, y, z, dx, dy, dz, random) -> {
            TOrientedParticle<T> particle = factory.create(level, x, y, z, dx, dy, dz, sprites);
            if (particle != null) {
                TParticleManager.getInstance().add(particle);
            }
            return null;
        });
    }

    @FunctionalInterface
    public interface SimpleParticleFactory<T extends ParticleOptions> {
        Particle create(T parameters, ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteSet, RandomSource random);
    }

    @FunctionalInterface
    public interface OrientedParticleFactory<T extends ParticleOptions> {
        TOrientedParticle<T> create(ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteSet);
    }
}
