package com.codingcat.modelshifter.client.mixin.renderer;


import com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererType;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.render.entity.LivingEntityRenderer;

//? >=1.21.3 {
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.codingcat.modelshifter.client.ModelShifterClient;
import com.codingcat.modelshifter.client.api.entity.EntityRenderStateWrapper;
import com.codingcat.modelshifter.client.api.model.PlayerModel;
import com.codingcat.modelshifter.client.render.ReplacedPlayerEntityRenderer;
import com.codingcat.modelshifter.client.util.MixinUtil;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
//?}

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin
        //? >=1.21.3 {
        <T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>>
//?}
{
    //? >=1.21.3 {
    @Shadow
    protected M model;

    @Inject(at = @At("HEAD"),
            method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    public void render(LivingEntityRenderState renderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        EntityRenderStateWrapper state = EntityRenderStateWrapper.of(renderState);
        if (!state.isPlayer() || !(state.getPlayer() instanceof AbstractClientPlayerEntity clientPlayer)) return;
        if (!(renderState instanceof PlayerEntityRenderState playerState)) return;

        @SuppressWarnings("unchecked")
        LivingEntityRenderer<T, S, M> rendererObject = ((LivingEntityRenderer<T, S, M>) (Object) this);
        if (!(rendererObject instanceof PlayerEntityRenderer playerEntityRenderer)) return;
        if (!ModelShifterClient.state.isRendererEnabled(state.getPlayer())
                || state.getPlayer().isSpectator()) return;

        PlayerModel playerModel = ModelShifterClient.state.getState(state.getPlayer().getUuid()).getPlayerModel();
        ReplacedPlayerEntityRenderer renderer = ModelShifterClient.holder.getRenderer(playerModel);
        if (renderer != null)
            renderer.render(clientPlayer, playerEntityRenderer.getTexture(playerState), -1, matrixStack, vertexConsumerProvider, i);
    }

    @Inject(
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/model/EntityModel;setAngles(Lnet/minecraft/client/render/entity/state/EntityRenderState;)V",
                    shift = At.Shift.AFTER),
            method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    public void injectModelVisibility(LivingEntityRenderState livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        EntityRenderStateWrapper state = EntityRenderStateWrapper.of(livingEntityRenderState);
        if (!state.isPlayer() || !(state.getPlayer() instanceof AbstractClientPlayerEntity playerEntity)) return;
        if (!(model instanceof PlayerEntityModel playerModel)) return;

        MixinUtil.setModelVisibility(playerEntity, playerModel);
    }
    //?}

    @Redirect(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/render/entity/feature/FeatureRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/EntityRenderState;FF)V"),
            method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    public void featureRendererRender(FeatureRenderer<S, M> instance, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, EntityRenderState e, float v, float v1, @Local(argsOnly = true) S livingEntityState) {
        EntityRenderStateWrapper state = EntityRenderStateWrapper.of(e);
        FeatureRendererType type = FeatureRendererType.findByClass(instance.getClass());
        //noinspection DataFlowIssue
        if (!state.isPlayer() || !ModelShifterClient.state.isRendererEnabled(state.getPlayer())) {
            instance.render(matrixStack, vertexConsumerProvider, i, livingEntityState, v, v1);
            return;
        }

        instance.getContextModel().resetTransforms();
        MixinUtil.ifRendererEnabled(type, state, false, states -> {
            states.modifyRendering(type, state, matrixStack);
            instance.render(matrixStack, vertexConsumerProvider, i, livingEntityState, v, v1);
        });
    }
}
