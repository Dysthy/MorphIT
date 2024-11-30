package com.codingcat.modelshifter.client.mixin.renderer;

import com.codingcat.modelshifter.client.ModelShifterClient;
import com.codingcat.modelshifter.client.api.entity.EntityRenderStateWrapper;
import com.codingcat.modelshifter.client.api.renderer.DynamicAdditionalRendererHolder;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
//? <1.21.3 {
import com.codingcat.modelshifter.client.render.ReplacedPlayerEntityRenderer;
import com.codingcat.modelshifter.client.api.model.PlayerModel;
import com.codingcat.modelshifter.client.util.MixinUtil;
//?} else {
/*import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
*///?}
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
//? >=1.21.3 {
/*public abstract class PlayerEntityRendererMixin
        extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityRenderState, PlayerEntityModel> {
*///?} else {
public abstract class PlayerEntityRendererMixin
        extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
//?}

    //? >=1.21.3 {
    /*public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel model, float shadowRadius) {
        *///?} else {
        public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
         //?}
        super(ctx, model, shadowRadius);
    }

    @Inject(at = @At("RETURN"), method = "<init>")
    public void onInit(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        ModelShifterClient.holder = new DynamicAdditionalRendererHolder(ctx, ModelShifterClient.state);
        ModelShifterClient.holder.applyState();
    }

    //? >=1.21.3 {
    /*@Inject(at = @At("HEAD"),
            method = "getPositionOffset(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;)Lnet/minecraft/util/math/Vec3d;",
            cancellable = true)
    public void injectSetModelPose(PlayerEntityRenderState e, CallbackInfoReturnable<Vec3d> cir) {
        *///?} else {
    @Inject(at = @At("HEAD"),
            method = "getPositionOffset(Lnet/minecraft/client/network/AbstractClientPlayerEntity;F)Lnet/minecraft/util/math/Vec3d;",
            cancellable = true)
    public void injectSetModelPose(AbstractClientPlayerEntity e, float f, CallbackInfoReturnable<Vec3d> cir) {
    //?}
        EntityRenderStateWrapper state = EntityRenderStateWrapper.of(e);
        if (!state.isPlayer()) return;
        assert state.getPlayer() != null;
        if (!ModelShifterClient.state.isRendererEnabled(state.getPlayer())) return;

        //? >=1.21.3 {
        /*cir.setReturnValue(super.getPositionOffset(e));
        *///?} else {
        cir.setReturnValue(super.getPositionOffset(e, f));
         //?}
        cir.cancel();
    }

    //? <1.21.3 {
    @Inject(at = @At("HEAD"),
            method = "setModelPose",
            cancellable = true)
    public void injectSetModelPose(AbstractClientPlayerEntity player, CallbackInfo ci) {
        MixinUtil.setModelVisibility(player, model);
        if (ModelShifterClient.state.isRendererEnabled(player))
            ci.cancel();
    }
    //?}

    //? >=1.21.3 {
    /*@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V"),
            method = "renderArm")
    public void injectRenderArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Identifier skinTexture, ModelPart arm, boolean sleeveVisible, CallbackInfo ci) {
        *///?} else {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/PlayerEntityModel;setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V"),
            method = "renderArm")
    public void injectRenderArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, ModelPart arm, ModelPart sleeve, CallbackInfo ci) {
    //?}
        if (arm == model.leftArm) {
            model.leftArm.visible = true;
            model.leftSleeve.visible = true;
        }
        if (arm == model.rightArm) {
            model.rightArm.visible = true;
            model.rightSleeve.visible = true;
        }
    }

    @Inject(at = @At("HEAD"),
            //? >=1.21.3 {
            /*method = "setupTransforms(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;FF)V",
            cancellable = true)
    public void injectSetupTransforms(PlayerEntityRenderState e, MatrixStack matrixStack, float f, float g, CallbackInfo ci) {
        *///?} else if >1.20.4 {
            method = "setupTransforms(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/client/util/math/MatrixStack;FFFF)V",
            cancellable = true)
    public void injectSetupTransforms(AbstractClientPlayerEntity e, MatrixStack matrixStack, float f, float g, float h, float i, CallbackInfo ci) {
            //?} else {
            /*method = "setupTransforms(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/client/util/math/MatrixStack;FFF)V",
            cancellable = true)
    public void injectSetupTransforms(AbstractClientPlayerEntity e, MatrixStack matrixStack, float f, float g, float h, CallbackInfo ci) {
            *///?}

        EntityRenderStateWrapper state = EntityRenderStateWrapper.of(e);
        if (!state.isPlayer()) return;
        assert state.getPlayer() != null;
        if (!ModelShifterClient.state.isRendererEnabled(state.getPlayer())) return;

        //? >=1.21.3 {
        /*super.setupTransforms(e, matrixStack, f, g);
        *///?} else if >1.20.4 {
        super.setupTransforms(e, matrixStack, f, g, h, i);
         //?} else {
        /*super.setupTransforms(e, matrixStack, f, g, h);
         *///?}
        ci.cancel();
    }

    //? <1.21.3 {
    @Inject(at = @At("HEAD"),
            method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    public void render(AbstractClientPlayerEntity clientPlayer, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if (!ModelShifterClient.state.isRendererEnabled(clientPlayer)
                || clientPlayer.isSpectator()) return;

        PlayerModel playerModel = ModelShifterClient.state.getState(clientPlayer.getUuid()).getPlayerModel();
        ReplacedPlayerEntityRenderer renderer = ModelShifterClient.holder.getRenderer(playerModel);
        if (renderer != null)
            renderer.render(clientPlayer, getTexture(clientPlayer), g, matrixStack, vertexConsumerProvider, i);
    }
    //?}
}
