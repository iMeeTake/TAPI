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
    protected float scale;

    protected TOrientedParticle(ClientLevel level,
                                double x, double y, double z,
                                double velocityX, double velocityY, double velocityZ,
                                SpriteSet spriteSet) {
        super(level, x, y, z, velocityX, velocityY, velocityZ);
        this.spriteSet = spriteSet;
        this.scale = 1.0f;
        this.hasPhysics = false;
        this.pickSprite(spriteSet);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }

        this.move(this.xd, this.yd, this.zd);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        this.buildGeometry(vertexConsumer, camera, tickDelta);
    }

    public abstract void buildGeometry(VertexConsumer vertexConsumer,
                                       Camera camera,
                                       float tickDelta);
}
