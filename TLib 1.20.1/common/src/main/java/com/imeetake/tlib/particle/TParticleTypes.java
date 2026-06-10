package com.imeetake.tlib.particle;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

public final class TParticleTypes {

    private TParticleTypes() {
    }

    public static RegistrySupplier<SimpleParticleType> simple(DeferredRegister<ParticleType<?>> registry, String name) {
        return registry.register(name, () -> new SimpleParticleType(false) {
        });
    }
}
