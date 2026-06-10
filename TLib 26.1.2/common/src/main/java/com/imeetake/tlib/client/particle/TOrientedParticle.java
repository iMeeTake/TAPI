package com.imeetake.tlib.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public abstract class TOrientedParticle<T extends ParticleOptions> {

    protected final ClientLevel level;
    protected final SpriteSet spriteSet;
    protected final RandomSource random = RandomSource.create();

    protected double xo, yo, zo;
    protected double x, y, z;
    protected double xd, yd, zd;

    protected float rCol = 1.0f;
    protected float gCol = 1.0f;
    protected float bCol = 1.0f;
    protected float alpha = 1.0f;

    protected float scale = 1.0f;
    protected float gravity = 0.0f;
    protected float friction = 0.98f;

    protected int age = 0;
    protected int lifetime = 20;

    protected boolean removed = false;
    protected boolean hasPhysics = false;

    protected TOrientedParticle(ClientLevel level,
                                double x, double y, double z,
                                double velocityX, double velocityY, double velocityZ,
                                SpriteSet spriteSet) {
        this.level = level;
        this.spriteSet = spriteSet;

        this.x = x;
        this.y = y;
        this.z = z;
        this.xo = x;
        this.yo = y;
        this.zo = z;

        this.xd = velocityX;
        this.yd = velocityY;
        this.zd = velocityZ;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }

        this.yd -= 0.04 * this.gravity;

        this.x += this.xd;
        this.y += this.yd;
        this.z += this.zd;

        this.xd *= this.friction;
        this.yd *= this.friction;
        this.zd *= this.friction;
    }

    public abstract void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta);

    public void render(PoseStack.Pose pose, VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        this.buildGeometry(vertexConsumer, camera, tickDelta);
    }

    public void remove() {
        this.removed = true;
    }

    public boolean isAlive() {
        return !this.removed;
    }

    public void setColor(float r, float g, float b) {
        this.rCol = r;
        this.gCol = g;
        this.bCol = b;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    protected int getLightColor(float tickDelta) {
        double px = Mth.lerp(tickDelta, this.xo, this.x);
        double py = Mth.lerp(tickDelta, this.yo, this.y);
        double pz = Mth.lerp(tickDelta, this.zo, this.z);
        BlockPos blockPos = BlockPos.containing(px, py, pz);
        return this.level.hasChunkAt(blockPos) ? LevelRenderer.getLightColor(this.level, blockPos) : 0;
    }

    protected void move(double dx, double dy, double dz) {
        this.x += dx;
        this.y += dy;
        this.z += dz;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public int getAge() {
        return age;
    }

    public int getLifetime() {
        return lifetime;
    }
}