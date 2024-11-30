package com.codingcat.modelshifter.client.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.tuple.Pair;

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
        /*context.drawGuiTexture(RenderLayer::getGuiTextured, texture, x, y, width, height);
        *///?} else if >1.20.1 {
        context.drawGuiTexture(texture, x, y, width, height);
         //?} else {
        /*context.drawTexture(texture.withPrefixedPath("textures/gui/sprites/").withSuffixedPath(".png"), x, y, 0, 0, width, height, width, height);
         *///?}
    }

    public static Pair<VertexConsumer, VertexConsumerProvider.Immediate> obtainVertexConsumer(Identifier texture) {
        VertexConsumerProvider.Immediate vertexConsumer = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer consumer = vertexConsumer.getBuffer(RenderLayer.getEntityTranslucent(texture));

        return Pair.of(consumer, vertexConsumer);
    }
}
