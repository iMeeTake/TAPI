package com.imeetake.tlib.client.particle;

import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Function;

public class TParticles {

    /**
     * Исправленная версия.
     * 1. Принимает registry (чтобы регистрировать в ID мода, а не библиотеки).
     * 2. Использует new SimpleParticleType(false) {} (с фигурными скобками!),
     * чтобы обойти protected доступ.
     */
    public static RegistrySupplier<SimpleParticleType> simple(DeferredRegister<ParticleType<?>> registry, String name) {
        return registry.register(name, () -> new SimpleParticleType(false) {});
    }

    // --- Остальные методы (можно оставить без изменений, они были рабочие) ---

    @FunctionalInterface
    public interface OrientedParticleFactory<T extends ParticleOptions> {
        TOrientedParticle<T> create(ClientLevel level,
                                    double x, double y, double z,
                                    double velocityX, double velocityY, double velocityZ,
                                    SpriteSet spriteSet);
    }

    public static <T extends ParticleOptions> void registerOriented(
            RegistrySupplier<? extends ParticleType<T>> type,
            OrientedParticleFactory<T> factory
    ) {
        ParticleProviderRegistry.register(type, spriteSet ->
                (parameters, level, x, y, z, dx, dy, dz) ->
                        factory.create(level, x, y, z, dx, dy, dz, spriteSet)
        );
    }

    public static <T extends ParticleOptions> void register(
            RegistrySupplier<? extends ParticleType<T>> type,
            Function<SpriteSet, ParticleProvider<T>> factoryFunction) {
        ParticleProviderRegistry.register(type, factoryFunction::apply);
    }

    public static <T extends ParticleOptions> void registerSimple(
            RegistrySupplier<? extends ParticleType<T>> type,
            TParticleFactoryProvider.ParticleCreator<T> creator
    ) {
        ParticleProviderRegistry.register(type, sprite -> new TParticleFactoryProvider<>(sprite, creator));
    }
}