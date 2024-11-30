package com.codingcat.modelshifter.client.mixin.renderer;

import com.codingcat.modelshifter.client.api.entity.EntityRenderStateWrapper;
import com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererType;
import com.codingcat.modelshifter.client.util.MixinUtil;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
//? >=1.21.3 {
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
//?} else {
/*import net.minecraft.entity.LivingEntity;
*///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ElytraFeatureRenderer.class)
//? >=1.21.3 {
public abstract class ElytraFeatureRendererMixin<S extends BipedEntityRenderState, M extends EntityModel<S>>
        extends FeatureRenderer<S, M> {
    //?} else {
/*public abstract class ElytraFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T>>
        extends FeatureRenderer<T, M> {
*///?}
    @Unique
    private static final FeatureRendererType TYPE = FeatureRendererType.ELYTRA;

    //? >=1.21.3 {
    public ElytraFeatureRendererMixin(FeatureRendererContext<S, M> context) {
        super(context);
    }
    //?} else {
    /*public ElytraFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }
    *///?}

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
        MixinUtil.ifRendererEnabled(TYPE, EntityRenderStateWrapper.of(e), false, states -> ci.cancel());
    }

    //? >=1.21.3 {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"),
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/BipedEntityRenderState;FF)V")
    public void insertModifyRendering2(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, BipedEntityRenderState e, float f, float g, CallbackInfo ci) {
    //?} else {
    /*@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"),
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V")
    public void insertModifyRendering2(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T e, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
    *///?}
        EntityRenderStateWrapper state = EntityRenderStateWrapper.of(e);
        MixinUtil.ifRendererEnabled(TYPE, state, true, states ->
                states.modifyRendering(TYPE, state, matrixStack));
    }
}
