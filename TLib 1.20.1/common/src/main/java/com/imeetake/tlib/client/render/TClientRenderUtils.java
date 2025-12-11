package com.imeetake.tlib.client.render;

import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3; // Vec3d -> Vec3

/**
 * Utility class for accessing client-side rendering state.
 * Architectury 1.20.1 (Mojang Mappings).
 */
public class TClientRenderUtils {

    public static Camera getCamera() {
        return Minecraft.getInstance().gameRenderer.getMainCamera();
    }

    public static Vec3 getCameraPosition() {
        Camera camera = getCamera();
        return camera.getPosition();
    }

    public static Vec3 getCameraLookVector() {
        Camera camera = getCamera();
        // Vec3.fromPolar logic equivalent
        float pitch = camera.getXRot();
        float yaw = camera.getYRot();
        float f = (float)Math.cos(-yaw * 0.017453292F - (float)Math.PI);
        float g = (float)Math.sin(-yaw * 0.017453292F - (float)Math.PI);
        float h = (float)-Math.cos(-pitch * 0.017453292F);
        float i = (float)Math.sin(-pitch * 0.017453292F);
        return new Vec3((double)(g * h), (double)i, (double)(f * h));
    }

    public static boolean isFirstPerson() {
        return Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON;
    }

    public static boolean isThirdPersonBack() {
        return Minecraft.getInstance().options.getCameraType() == CameraType.THIRD_PERSON_BACK;
    }

    public static boolean isThirdPersonFront() {
        return Minecraft.getInstance().options.getCameraType() == CameraType.THIRD_PERSON_FRONT;
    }

    public static boolean isHudVisible() {
        return !Minecraft.getInstance().options.hideGui;
    }

    public static boolean isInGUI() {
        return Minecraft.getInstance().screen != null;
    }

    public static double getFov() {
        // Mojang 1.20.1: options.fov() returns OptionInstance<Integer>
        return Minecraft.getInstance().options.fov().get();
    }
}