package com.dysthy.morphit.client.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
//? >=1.21.3 {
import net.minecraft.client.render.entity.state.EntityRenderState;
//?} else {
/*import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.*;
*///?}
//? >1.20.1 {
import net.minecraft.client.util.SkinTextures;
//?}
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.util.Map;

public class Util {
    public static GameProfile getGameProfile() {
        //? >1.20.1 {
        return MinecraftClient.getInstance().getGameProfile();
        //?} else {
        /*return MinecraftClient.getInstance().getSession().getProfile();
         *///?}
    }

    public static void drawGuiTexture(DrawContext context, Identifier texture, int x, int y, int width, int height) {
        Util.drawGuiTexture(context, texture, x, y, width, height, -1);
    }

    public static void drawGuiTexture(DrawContext context, Identifier texture, int x, int y, int width, int height, int color) {
        //? <1.21.3 {
        /*float[] prevColor = RenderSystem.getShaderColor().clone();
        Color color1 = new Color(color);
        float r = (float) color1.getRed() / 255;
        float g = (float) color1.getGreen() / 255;
        float b = (float) color1.getBlue() / 255;
        float a = (float) color1.getAlpha() / 255;
        *///?}
        //? >=1.21.3 {
        context.drawGuiTexture(RenderLayer::getGuiTextured, texture, x, y, width, height, color);
        //?} else if >1.20.1 {
        /*context.setShaderColor(r, g, b, a);
        context.drawGuiTexture(texture, x, y, width, height);
        context.setShaderColor(prevColor[0], prevColor[1], prevColor[2], prevColor[3]);
        *///?} else {
        /*context.setShaderColor(r, g, b, a);
        context.drawTexture(texture.withPrefixedPath("textures/gui/sprites/").withSuffixedPath(".png"), x, y, 0, 0, width, height, width, height);
        context.setShaderColor(prevColor[0], prevColor[1], prevColor[2], prevColor[3]);
         *///?}
    }

    public static Pair<VertexConsumer, VertexConsumerProvider.Immediate> obtainVertexConsumer(Identifier texture) {
        VertexConsumerProvider.Immediate vertexConsumer = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer consumer = vertexConsumer.getBuffer(RenderLayer.getEntityTranslucent(texture));

        return Pair.of(consumer, vertexConsumer);
    }

    //? <1.21.3 {
    /*public static <T extends PlayerEntity> EntityRenderer<T>
     *///?} else {
    public static <T extends PlayerEntity, S extends EntityRenderState> EntityRenderer<T, S>
    //?}
    getNormalRendererReflect(AbstractClientPlayerEntity playerEntity, EntityRenderDispatcher dispatcher) throws ReflectiveOperationException {
        //noinspection ExtractMethodRecommender
        Field modelRenderersField = dispatcher.getClass().getDeclaredField("modelRenderers");
        modelRenderersField.setAccessible(true);
        @SuppressWarnings("unchecked")
        //? <=1.20.1 {
        /*Map<String, EntityRenderer<T>> modelRenderers = (Map<String, EntityRenderer<T>>) modelRenderersField.get(dispatcher);
         *///?} else if <1.21.3 {
                /*Map<SkinTextures.Model, EntityRenderer<T>> modelRenderers = (Map<SkinTextures.Model, EntityRenderer<T>>) modelRenderersField.get(dispatcher);
                 *///?} else {
                Map<SkinTextures.Model, EntityRenderer<T, S>> modelRenderers = (Map<SkinTextures.Model, EntityRenderer<T, S>>) modelRenderersField.get(dispatcher);
        //?}

        //? <=1.20.1 {
        /*String model = playerEntity.getModel();
         *///?} else {
        SkinTextures.Model model = playerEntity.getSkinTextures().model();
        //?}
        //? <1.21.3 {
        /*EntityRenderer<T> entityRenderer = modelRenderers.get(model);
         *///?} else {
        EntityRenderer<T, S> entityRenderer = modelRenderers.get(model);
        //?}
        return entityRenderer != null ? entityRenderer :
                //? >1.20.1 {
                modelRenderers.get(SkinTextures.Model.WIDE);
        //?} else {
        /*modelRenderers.get("default");
         *///?}
    }

    public static Class<?> getCallingClass(int offset) {
        try {
            return Class.forName(Thread.currentThread().getStackTrace()[offset + 3].getClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to identify calling class", e);
        }
    }
}
