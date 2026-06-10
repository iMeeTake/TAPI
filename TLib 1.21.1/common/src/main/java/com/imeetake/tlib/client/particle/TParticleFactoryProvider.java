package com.imeetake.tlib.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleOptions;

@Deprecated
public class TParticleFactoryProvider<T extends ParticleOptions> implements ParticleProvider<T> {

    private final SpriteSet spriteSet;
    private final ParticleCreator<T> creator;

    public TParticleFactoryProvider(SpriteSet spriteSet, ParticleCreator<T> creator) {
        this.spriteSet = spriteSet;
        this.creator = creator;
    }

    @Override
    public Particle createParticle(T type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
        TextureSheetParticle particle = creator.create(level, x, y, z, dx, dy, dz);
        particle.pickSprite(spriteSet);
        return particle;
    }

    @FunctionalInterface
    public interface ParticleCreator<T extends ParticleOptions> {
        TextureSheetParticle create(ClientLevel level, double x, double y, double z, double dx, double dy, double dz);
    }
}
