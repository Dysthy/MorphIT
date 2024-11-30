package com.codingcat.modelshifter.client.mixin.renderer;


import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.render.entity.LivingEntityRenderer;

//? >=1.21.3 {
/*import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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
*///?}

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin
        //? >=1.21.3 {
        /*<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>>
*///?}
{
    //? >=1.21.3 {
    /*@Shadow protected M model;

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
            renderer.render(clientPlayer, playerEntityRenderer.getTexture(playerState), matrixStack, vertexConsumerProvider, i);
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
    *///?}
}
