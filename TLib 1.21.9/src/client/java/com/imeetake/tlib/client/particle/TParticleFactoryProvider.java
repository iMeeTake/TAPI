package com.imeetake.tlib.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;

public class TParticleFactoryProvider<T extends ParticleEffect> implements ParticleFactory<T> {

    private final SpriteProvider spriteProvider;
    private final ParticleCreator<T> creator;

    public TParticleFactoryProvider(SpriteProvider spriteProvider, ParticleCreator<T> creator) {
        this.spriteProvider = spriteProvider;
        this.creator = creator;
    }

    @Override
    public Particle createParticle(T effect, ClientWorld world, double x, double y, double z, double dx, double dy, double dz, net.minecraft.util.math.random.Random random) {
        return creator.create(world, x, y, z, dx, dy, dz, spriteProvider, random);
    }

    @FunctionalInterface
    public interface ParticleCreator<T extends ParticleEffect> {
        Particle create(ClientWorld world, double x, double y, double z, double dx, double dy, double dz, SpriteProvider spriteProvider, net.minecraft.util.math.random.Random random);
    }
}