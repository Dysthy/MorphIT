package com.codingcat.modelshifter.client.mixin.renderer;

import com.codingcat.modelshifter.client.ModelShifterClient;
import com.codingcat.modelshifter.client.api.entity.EntityRenderStateWrapper;
import com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererStates;
import com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererType;
import com.codingcat.modelshifter.client.util.MixinUtil;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
//? >=1.21.3 {
/*import net.minecraft.client.render.model.BakedModel;
import net.minecraft.item.ModelTransformationMode;
*///?} else {
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.LivingEntity;
 //?}
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemFeatureRenderer.class)
//? >=1.21.3 {
/*public abstract class HeldItemFeatureRendererMixin<S extends LivingEntityRenderState, M extends EntityModel<S>>
        extends FeatureRenderer<S, M> {
    *///?} else {
    public abstract class HeldItemFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
     //?}
    @Unique
    private static final FeatureRendererType TYPE = FeatureRendererType.HELD_ITEM;

    //? >=1.21.3 {
    /*public HeldItemFeatureRendererMixin(FeatureRendererContext<S, M> context) {
        super(context);
    }
    *///?} else {
    public HeldItemFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }
    //?}

    //? >=1.21.3 {
    /*@Inject(at = @At(value = "HEAD"),
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/LivingEntityRenderState;FF)V",
            cancellable = true)
    public void insertModifyRendering(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, LivingEntityRenderState e, float f, float g, CallbackInfo ci) {
        *///?} else {
        @Inject(at = @At(value = "HEAD"),
                method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
                cancellable = true)
        public void insertModifyRendering(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T e,float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        //?}
        MixinUtil.ifRendererEnabled(TYPE, EntityRenderStateWrapper.of(e), false, states -> ci.cancel());
    }


    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/ModelWithArms;setArmAngle(Lnet/minecraft/util/Arm;Lnet/minecraft/client/util/math/MatrixStack;)V"), method = "renderItem")
    public void insertModifyRendering(ModelWithArms instance, Arm arm, MatrixStack matrixStack,
                                      //? >=1.21.3 {
                                      /*@Local(argsOnly = true) S e
                                      *///?} else {
            @Local(argsOnly = true) LivingEntity e
             //?}
    ) {
        EntityRenderStateWrapper state = EntityRenderStateWrapper.of(e);
        if (!state.isPlayer()) return;
        assert state.getPlayer() != null;
        FeatureRendererStates states = ModelShifterClient.state.accessFeatureRendererStates(state.getPlayer());
        if (ModelShifterClient.state.isRendererEnabled(state.getPlayer())
                || (states != null && states.isRendererEnabled(TYPE))) return;

        ((ModelWithArms) this.getContextModel()).setArmAngle(arm, matrixStack);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"), method = "renderItem")
    //? >=1.21.3 {
    /*public void insertModifyRendering(LivingEntityRenderState state, BakedModel model, ItemStack stack, ModelTransformationMode modelTransformation, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        EntityRenderStateWrapper renderState = EntityRenderStateWrapper.of(state);
        *///?} else {
    public void insertModifyRendering(LivingEntity entity, ItemStack stack, ModelTransformationMode transformationMode, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
      EntityRenderStateWrapper renderState = EntityRenderStateWrapper.of(entity);
    //?}
        MixinUtil.ifRendererEnabled(TYPE, renderState, true, states ->
                states.modifyRendering(TYPE, renderState, matrices));
    }
}
