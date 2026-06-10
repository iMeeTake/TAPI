package com.imeetake.tlib.client.particle;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.state.ParticleGroupRenderState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TParticleManager {

    private static final TParticleManager INSTANCE = new TParticleManager();

    private final List<TOrientedParticle<?>> particles = new ArrayList<>();
    private ClientLevel level;

    private TParticleManager() {
    }

    public static TParticleManager getInstance() {
        return INSTANCE;
    }

    public void setLevel(ClientLevel level) {
        this.level = level;
        this.particles.clear();
    }

    public void add(TOrientedParticle<?> particle) {
        this.particles.add(particle);
    }

    public void tick() {
        Iterator<TOrientedParticle<?>> iterator = this.particles.iterator();
        while (iterator.hasNext()) {
            TOrientedParticle<?> particle = iterator.next();
            particle.tick();
            if (!particle.isAlive()) {
                iterator.remove();
            }
        }
    }

    public ParticleGroupRenderState extractRenderState(Camera camera, float tickDelta) {
        return new TParticleRenderState(new ArrayList<>(this.particles), camera, tickDelta);
    }

    public List<TOrientedParticle<?>> getParticles() {
        return this.particles;
    }

    public void clear() {
        this.particles.clear();
    }

    public int getParticleCount() {
        return this.particles.size();
    }

    public boolean isEmpty() {
        return this.particles.isEmpty();
    }
}