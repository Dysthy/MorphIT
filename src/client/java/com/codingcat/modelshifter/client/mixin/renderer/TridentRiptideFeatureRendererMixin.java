package com.codingcat.modelshifter.client.mixin.renderer;

import com.codingcat.modelshifter.client.api.entity.EntityRenderStateWrapper;
import com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererType;
import com.codingcat.modelshifter.client.util.MixinUtil;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.TridentRiptideFeatureRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
//? <1.21.3 {
import net.minecraft.entity.LivingEntity;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TridentRiptideFeatureRenderer.class)
public class TridentRiptideFeatureRendererMixin {
    @Unique
    private static final FeatureRendererType TYPE = FeatureRendererType.TRIDENT_RIPTIDE;

    //? >=1.21.3 {
    /*@Inject(at = @At(value = "HEAD"),
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/PlayerEntityRenderState;FF)V",
            cancellable = true)
    public void insertModifyRendering(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, PlayerEntityRenderState e, float f, float g, CallbackInfo ci) {
    *///?} else {
    @Inject(at = @At(value = "HEAD"),
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
            cancellable = true)
    public void insertModifyRendering(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, LivingEntity e, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
    //?}
        MixinUtil.insertModifyRendering(TYPE, EntityRenderStateWrapper.of(e), matrixStack, ci);
    }
}
