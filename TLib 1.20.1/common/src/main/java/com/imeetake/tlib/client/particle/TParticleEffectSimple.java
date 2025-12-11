package com.imeetake.tlib.client.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;

public class TParticleEffectSimple implements ParticleOptions {

    private final ParticleType<TParticleEffectSimple> type;

    public TParticleEffectSimple(ParticleType<TParticleEffectSimple> type) {
        this.type = type;
    }

    @Override
    public ParticleType<?> getType() {
        return type;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
    }

    @Override
    public String writeToString() {
        return BuiltInRegistries.PARTICLE_TYPE.getKey(this.type).toString();
    }

    // Обязательное поле для ParticleOptions в 1.20.1
    public static final ParticleOptions.Deserializer<TParticleEffectSimple> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        @Override
        public TParticleEffectSimple fromCommand(ParticleType<TParticleEffectSimple> type, StringReader reader) throws CommandSyntaxException {
            return new TParticleEffectSimple(type);
        }

        @Override
        public TParticleEffectSimple fromNetwork(ParticleType<TParticleEffectSimple> type, FriendlyByteBuf buf) {
            return new TParticleEffectSimple(type);
        }
    };
}