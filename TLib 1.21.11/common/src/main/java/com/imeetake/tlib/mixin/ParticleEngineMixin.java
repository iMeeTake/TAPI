package com.imeetake.tlib.mixin;

import com.imeetake.tlib.client.particle.TParticleRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.ParticlesRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public class ParticleEngineMixin {

    @Shadow
    protected ClientLevel level;

    @Inject(method = "extract", at = @At("TAIL"))
    private void tlib$extractCustomParticles(ParticlesRenderState particlesRenderState, Frustum frustum, Camera camera, float tickDelta, CallbackInfo ci) {
        TParticleRenderer.extractToRenderState(particlesRenderState, frustum, camera, tickDelta);
    }

    @Inject(method = "setLevel", at = @At("HEAD"))
    private void tlib$onSetLevel(ClientLevel level, CallbackInfo ci) {
        TParticleRenderer.onLevelChange(level);
    }
}