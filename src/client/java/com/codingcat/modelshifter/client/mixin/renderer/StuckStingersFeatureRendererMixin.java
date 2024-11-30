package com.codingcat.modelshifter.client.mixin.renderer;

import com.codingcat.modelshifter.client.api.entity.EntityRenderStateWrapper;
import com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererType;
import com.codingcat.modelshifter.client.util.MixinUtil;

import net.minecraft.client.render.entity.feature.StuckStingersFeatureRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
//? <1.21.3 {
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
//?}
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StuckStingersFeatureRenderer.class)
public class StuckStingersFeatureRendererMixin {
    @Unique
    private static final FeatureRendererType TYPE = FeatureRendererType.STUCK_STINGERS;

    //? >=1.21.3 {
    /*@Inject(at = @At("RETURN"),
            method = "getObjectCount",
            cancellable = true)
    public void injectGetObjectCount(PlayerEntityRenderState e, CallbackInfoReturnable<Integer> cir) {
        MixinUtil.ifRendererEnabled(TYPE, EntityRenderStateWrapper.of(e), true, states ->
                cir.setReturnValue(0));
    }
    *///?} else {
    @Inject(at = @At("HEAD"),
            method = "renderObject",
            cancellable = true)
    public void injectModifyRendering(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Entity entity, float directionX, float directionY, float directionZ, float tickDelta, CallbackInfo ci) {
        MixinUtil.insertModifyRendering(TYPE, EntityRenderStateWrapper.of(entity), matrices, ci);
    }
    //?}
}
