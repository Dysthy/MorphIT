package com.codingcat.modelshifter.client.mixin.renderer;

import com.codingcat.modelshifter.client.api.entity.EntityRenderStateWrapper;
import com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererType;
import com.codingcat.modelshifter.client.util.MixinUtil;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.ShoulderParrotFeatureRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
//? <1.21.3 {
import net.minecraft.entity.player.PlayerEntity;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShoulderParrotFeatureRenderer.class)
//? >=1.21.3 {
/*public abstract class ShoulderParrotFeatureRendererMixin
        extends FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> {
    *///?} else {
    public class ShoulderParrotFeatureRendererMixin<T extends PlayerEntity> {
     //?}
    @Unique
    private static final FeatureRendererType TYPE = FeatureRendererType.SHOULDER_PARROT;
    //? >=1.21.3 {
    /*public ShoulderParrotFeatureRendererMixin(FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel> context) {
        super(context);
    }
    *///?}

    //? >=1.21.3 {
    /*@Inject(at = @At(value = "HEAD"),
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/PlayerEntityRenderState;FF)V",
            cancellable = true)
    public void insertModifyRendering(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, PlayerEntityRenderState e, float f, float g, CallbackInfo ci) {
    *///?} else {
    @Inject(at = @At(value = "HEAD"),
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/player/PlayerEntity;FFFFFF)V",
            cancellable = true)
    public void insertModifyRendering(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T e, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
    //?}
        MixinUtil.insertModifyRendering(TYPE, EntityRenderStateWrapper.of(e), matrixStack, ci);
    }
}
