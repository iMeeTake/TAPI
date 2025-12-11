package com.imeetake.tlib.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel; // ClientWorld
import net.minecraft.client.player.LocalPlayer; // ClientPlayerEntity
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder; // RegistryEntry
import net.minecraft.world.level.biome.Biome;

public class TClientEnvironment {

    public static ClientLevel getWorld() {
        return Minecraft.getInstance().level;
    }

    public static boolean isRaining() {
        ClientLevel level = getWorld();
        return level != null && level.isRaining();
    }

    public static boolean isThundering() {
        ClientLevel level = getWorld();
        return level != null && level.isThundering();
    }

    public static float getRainStrength(float tickDelta) {
        ClientLevel level = getWorld();
        return level != null ? level.getRainLevel(tickDelta) : 0.0f; // getRainGradient -> getRainLevel
    }

    public static Holder<Biome> getCurrentBiome() {
        ClientLevel level = getWorld();
        LocalPlayer player = Minecraft.getInstance().player;

        if (level != null && player != null) {
            return level.getBiome(player.blockPosition());
        }
        return null;
    }

    public static boolean isInOpenSky() {
        ClientLevel level = getWorld();
        LocalPlayer player = Minecraft.getInstance().player;

        if (level != null && player != null) {
            return level.canSeeSky(player.blockPosition());
        }
        return false;
    }

    public static boolean isNight() {
        ClientLevel level = getWorld();

        if (level != null) {
            long timeOfDay = level.getDayTime() % 24000L;
            return timeOfDay >= 13000L && timeOfDay <= 23000L;
        }
        return false;
    }

    public static boolean isColdBiome(Holder<Biome> entry, BlockPos pos) {
        if (entry == null) return false;
        Biome b = entry.value();
        try {
            // 1.20.1 Mojang: Check for SNOW explicitly or cold temp
            return b.getPrecipitationAt(pos) == Biome.Precipitation.SNOW
                    || b.getBaseTemperature() <= 0.15f;
        } catch (Throwable t) {
            return false;
        }
    }

    public static boolean isAridBiome(Holder<Biome> entry, BlockPos pos) {
        if (entry == null) return false;
        Biome b = entry.value();
        try {
            return b.getPrecipitationAt(pos) == Biome.Precipitation.NONE;
        } catch (Throwable t) {
            return false;
        }
    }
}