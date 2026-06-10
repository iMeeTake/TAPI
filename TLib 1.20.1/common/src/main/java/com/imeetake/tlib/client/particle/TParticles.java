package com.imeetake.tlib.client.particle;

import com.imeetake.tlib.particle.TParticleTypes;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Function;

public class TParticles {

    @Deprecated
    public static RegistrySupplier<SimpleParticleType> simple(DeferredRegister<ParticleType<?>> registry, String name) {
        return TParticleTypes.simple(registry, name);
    }

    @FunctionalInterface
    public interface OrientedParticleFactory<T extends ParticleOptions> {
        TOrientedParticle<T> create(ClientLevel level,
                                    double x, double y, double z,
                                    double velocityX, double velocityY, double velocityZ,
                                    SpriteSet spriteSet);
    }

    @FunctionalInterface
    public interface SimpleParticleFactory<T extends ParticleOptions> {
        Particle create(ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ);
    }

    public static <T extends ParticleOptions> void registerOriented(
            RegistrySupplier<? extends ParticleType<T>> type,
            OrientedParticleFactory<T> factory
    ) {
        TParticleProviders.registerOriented(type, factory::create);
    }

    public static <T extends ParticleOptions> void register(
            RegistrySupplier<? extends ParticleType<T>> type,
            Function<SpriteSet, ParticleProvider<T>> factoryFunction) {
        TParticleProviders.register(type, factoryFunction);
    }

    public static <T extends ParticleOptions> void registerSimple(
            RegistrySupplier<? extends ParticleType<T>> type,
            TParticleFactoryProvider.ParticleCreator<T> creator
    ) {
        TParticleProviders.register(type, sprite -> new TParticleFactoryProvider<>(sprite, creator));
    }
}
