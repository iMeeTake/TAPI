package com.imeetake.tlib.client.particle;

import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;

public final class TParticleProviders {
    private static final Map<ParticleType<?>, ParticleProvider<?>> PROVIDERS = new IdentityHashMap<>();

    private TParticleProviders() {
    }

    public static <T extends ParticleOptions> void register(
            RegistrySupplier<? extends ParticleType<T>> type,
            Function<SpriteSet, ParticleProvider<T>> factory
    ) {
        ParticleProviderRegistry.register(type, sprites -> provider(type, sprites, factory));
    }

    public static <T extends ParticleOptions> void registerSimple(
            RegistrySupplier<? extends ParticleType<T>> type,
            SimpleParticleFactory factory
    ) {
        register(type, sprites -> simpleProvider(type, sprites, factory));
    }

    public static <T extends ParticleOptions> void registerOriented(
            RegistrySupplier<? extends ParticleType<T>> type,
            OrientedParticleFactory<T> factory
    ) {
        register(type, sprites -> orientedProvider(type, sprites, factory));
    }

    public static <T extends ParticleOptions> ParticleProvider<T> provider(
            RegistrySupplier<? extends ParticleType<T>> type,
            SpriteSet sprites,
            Function<SpriteSet, ParticleProvider<T>> factory
    ) {
        ParticleProvider<T> provider = factory.apply(sprites);
        PROVIDERS.put(type.get(), provider);
        return provider;
    }

    public static <T extends ParticleOptions> ParticleProvider<T> simpleProvider(
            RegistrySupplier<? extends ParticleType<T>> type,
            SpriteSet sprites,
            SimpleParticleFactory factory
    ) {
        return provider(type, sprites, ignored -> (parameters, level, x, y, z, dx, dy, dz) -> {
            TextureSheetParticle particle = factory.create(level, x, y, z, dx, dy, dz);
            particle.pickSprite(sprites);
            return particle;
        });
    }

    public static <T extends ParticleOptions> ParticleProvider<T> orientedProvider(
            RegistrySupplier<? extends ParticleType<T>> type,
            SpriteSet sprites,
            OrientedParticleFactory<T> factory
    ) {
        return provider(type, sprites, ignored -> (parameters, level, x, y, z, dx, dy, dz) ->
                factory.create(level, x, y, z, dx, dy, dz, sprites));
    }

    static Particle createParticle(RegistrySupplier<? extends SimpleParticleType> type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
        return createParticle(type.get(), level, x, y, z, dx, dy, dz);
    }

    @SuppressWarnings("unchecked")
    static Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
        ParticleProvider<SimpleParticleType> provider = (ParticleProvider<SimpleParticleType>) PROVIDERS.get(type);
        return provider == null ? null : provider.createParticle(type, level, x, y, z, dx, dy, dz);
    }

    @FunctionalInterface
    public interface SimpleParticleFactory {
        TextureSheetParticle create(ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ);
    }

    @FunctionalInterface
    public interface OrientedParticleFactory<T extends ParticleOptions> {
        TOrientedParticle<T> create(ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteSet);
    }
}
