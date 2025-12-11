package com.imeetake.tlib.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleOptions;

public abstract class TOrientedParticle<T extends ParticleOptions> extends TextureSheetParticle {

    protected final SpriteSet spriteSet;
    protected float scale; // В 1.20.1 это поле quadSize, но храним scale для API совместимости

    protected TOrientedParticle(ClientLevel level,
                                double x, double y, double z,
                                double velocityX, double velocityY, double velocityZ,
                                SpriteSet spriteSet) {
        super(level, x, y, z, velocityX, velocityY, velocityZ);
        this.spriteSet = spriteSet;
        this.scale = 1.0f;
        this.hasPhysics = false; // collidesWithWorld = false
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    /**
     * CRITICAL: We override render to BYPASS the default billboard logic.
     * We do NOT calculate quaternions here. We strictly delegate to the subclass.
     */
    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        // Do not call super.render() because that forces billboard rotation.
        this.buildGeometry(vertexConsumer, camera, tickDelta);
    }

    /**
     * Subclasses must implement this to define their own geometry (vertices),
     * completely ignoring standard camera orientation if they wish.
     */
    public abstract void buildGeometry(VertexConsumer vertexConsumer,
                                       Camera camera,
                                       float tickDelta);
}