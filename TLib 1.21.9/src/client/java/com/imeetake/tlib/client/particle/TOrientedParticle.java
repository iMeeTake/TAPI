package com.imeetake.tlib.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public abstract class TOrientedParticle<T extends ParticleEffect> extends Particle {

    protected final SpriteProvider spriteProvider;
    protected float scale;

    protected TOrientedParticle(ClientWorld world,
                                double x, double y, double z,
                                double velocityX, double velocityY, double velocityZ,
                                SpriteProvider spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.spriteProvider = spriteProvider;
        this.scale = 1.0f;
        this.collidesWithWorld = false;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public record Orientation(Vec3d right, Vec3d up) {}

    protected abstract Orientation orientation(Camera camera, float tickDelta);

    @Override
    public void render(VertexConsumer vc, Camera camera, float tickDelta) {
        Vec3d cam = camera.getPos();
        double cx = MathHelper.lerp(tickDelta, this.lastX, this.x) - cam.x;
        double cy = MathHelper.lerp(tickDelta, this.lastY, this.y) - cam.y;
        double cz = MathHelper.lerp(tickDelta, this.lastZ, this.z) - cam.z;
        Vec3d center = new Vec3d(cx, cy, cz);

        Orientation o = this.orientation(camera, tickDelta);
        Vec3d right = o.right();
        Vec3d up = o.up();

        if (right.lengthSquared() < 1.0e-8) right = new Vec3d(0.01, 0, 0);
        if (up.lengthSquared()   < 1.0e-8)   up    = new Vec3d(0, 0.01, 0);

        Vec3d p1 = center.add(right).add(up);
        Vec3d p2 = center.add(right).subtract(up);
        Vec3d p3 = center.subtract(right).subtract(up);
        Vec3d p4 = center.subtract(right).add(up);

        Sprite sprite = this.spriteProvider.getSprite(this.age, this.maxAge);
        float u1 = sprite.getMinU();
        float u2 = sprite.getMaxU();
        float v1 = sprite.getMinV();
        float v2 = sprite.getMaxV();
        int light = this.getBrightness(tickDelta);

        vertex(vc, p1, u2, v1, light);
        vertex(vc, p2, u2, v2, light);
        vertex(vc, p3, u1, v2, light);
        vertex(vc, p4, u1, v1, light);

        vertex(vc, p4, u1, v1, light);
        vertex(vc, p3, u1, v2, light);
        vertex(vc, p2, u2, v2, light);
        vertex(vc, p1, u2, v1, light);
    }

    protected void vertex(VertexConsumer vc, Vec3d pos, float u, float v, int light) {
        vc.vertex((float) pos.x, (float) pos.y, (float) pos.z)
                .texture(u, v)
                .color(this.red, this.green, this.blue, this.alpha)
                .light(light);
    }
}