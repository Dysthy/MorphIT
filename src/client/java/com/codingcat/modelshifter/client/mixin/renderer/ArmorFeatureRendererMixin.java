package com.codingcat.modelshifter.client.mixin.renderer;

import com.codingcat.modelshifter.client.api.entity.EntityRenderStateWrapper;
import com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererType;
import com.codingcat.modelshifter.client.util.MixinUtil;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
//? <1.21.3 {
/*import net.minecraft.entity.LivingEntity;
*///?} else {
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
 //?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
//? >=1.21.3 {
public abstract class ArmorFeatureRendererMixin<S extends BipedEntityRenderState, M extends BipedEntityModel<S>>
        extends FeatureRenderer<S, M> {
    public ArmorFeatureRendererMixin(FeatureRendererContext<S, M> context) {
        super(context);
    }
//?} else {
/*public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>>
        extends FeatureRenderer<T, M> {
    public ArmorFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }
    *///?}

    @Unique
    private static final FeatureRendererType TYPE = FeatureRendererType.ARMOR;

    //? >=1.21.3 {
    @Inject(at = @At(value = "HEAD"),
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/BipedEntityRenderState;FF)V",
            cancellable = true)
    public void insertModifyRendering(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, BipedEntityRenderState e, float f, float g, CallbackInfo ci) {
    //?} else {
    /*@Inject(at = @At(value = "HEAD"),
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
            cancellable = true)
    public void insertModifyRendering(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T e, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        *///?}
        MixinUtil.insertModifyRendering(TYPE, EntityRenderStateWrapper.of(e), matrixStack, ci);
    }
}
