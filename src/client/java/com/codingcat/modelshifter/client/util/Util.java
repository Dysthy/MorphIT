package com.codingcat.modelshifter.client.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.SkinTextures;
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
        //? >=1.21.3 {
        context.drawGuiTexture(RenderLayer::getGuiTextured, texture, x, y, width, height);
        //?} else if >1.20.1 {
        /*context.drawGuiTexture(texture, x, y, width, height);
         *///?} else {
        /*context.drawTexture(texture.withPrefixedPath("textures/gui/sprites/").withSuffixedPath(".png"), x, y, 0, 0, width, height, width, height);
         *///?}
    }

    public static Pair<VertexConsumer, VertexConsumerProvider.Immediate> obtainVertexConsumer(Identifier texture) {
        VertexConsumerProvider.Immediate vertexConsumer = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer consumer = vertexConsumer.getBuffer(RenderLayer.getEntityTranslucent(texture));

        return Pair.of(consumer, vertexConsumer);
    }

    public static <T extends PlayerEntity, S extends EntityRenderState> EntityRenderer<T, S> getNormalRendererReflect(AbstractClientPlayerEntity playerEntity, EntityRenderDispatcher dispatcher) throws ReflectiveOperationException {
        Field modelRenderersField = dispatcher.getClass().getDeclaredField("modelRenderers");
        modelRenderersField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<SkinTextures.Model, EntityRenderer<T, S>> modelRenderers = (Map<SkinTextures.Model, EntityRenderer<T, S>>) modelRenderersField.get(dispatcher);

        SkinTextures.Model model = playerEntity.getSkinTextures().model();
        EntityRenderer<T, S> entityRenderer = modelRenderers.get(model);
        return entityRenderer != null ? entityRenderer : modelRenderers.get(SkinTextures.Model.WIDE);
    }
}
