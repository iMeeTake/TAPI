package com.imeetake.tlib.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.state.ParticleGroupRenderState;
import net.minecraft.client.renderer.texture.TextureAtlas;

import java.util.List;

public class TParticleRenderState implements ParticleGroupRenderState {

    private static final RenderType PARTICLE_RENDER_TYPE = createParticleRenderType();

    private static RenderType createParticleRenderType() {
        RenderSetup setup = RenderSetup.builder(RenderPipelines.TRANSLUCENT_PARTICLE)
                .withTexture("Sampler0", TextureAtlas.LOCATION_PARTICLES)
                .useLightmap()
                .sortOnUpload()
                .createRenderSetup();
        return RenderType.create("tlib_particle", setup);
    }

    private final List<TOrientedParticle<?>> particles;
    private final Camera camera;
    private final float tickDelta;

    public TParticleRenderState(List<TOrientedParticle<?>> particles, Camera camera, float tickDelta) {
        this.particles = particles;
        this.camera = camera;
        this.tickDelta = tickDelta;
    }

    @Override
    public void submit(SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        if (particles.isEmpty()) return;

        PoseStack poseStack = new PoseStack();

        submitNodeCollector.submitCustomGeometry(
                poseStack,
                PARTICLE_RENDER_TYPE,
                (pose, vertexConsumer) -> {
                    for (TOrientedParticle<?> particle : particles) {
                        if (particle.isAlive()) {
                            particle.render(pose, vertexConsumer, camera, tickDelta);
                        }
                    }
                }
        );
    }

    @Override
    public void clear() {
    }
}