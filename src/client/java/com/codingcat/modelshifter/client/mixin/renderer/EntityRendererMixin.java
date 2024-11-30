package com.codingcat.modelshifter.client.mixin.renderer;

import com.codingcat.modelshifter.client.ModelShifterClient;
import com.codingcat.modelshifter.client.api.entity.EntityRenderStateWrapper;
import com.codingcat.modelshifter.client.api.model.PlayerModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
//? >=1.21.3 {
/*import net.minecraft.client.render.entity.state.EntityRenderState;
*///?}
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(EntityRenderer.class)
//? >=1.21.3 {
/*public class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {
*///?} else {
public class EntityRendererMixin<T extends Entity> {
//?}
    @Inject(
            //? >1.20.4 <1.21.3 {
            method = "renderLabelIfPresent(Lnet/minecraft/entity/Entity;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(DDD)V")
            //?} else {
            /*method = "renderLabelIfPresent",
            *///?}
            //? <=1.20.4 >=1.21.3 {
            /*at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V")
            *///?}
    )
    protected void injectLabelPositionOffset(
            //? >=1.21.3 {
            /*S e,
            *///?} else {
            T e,
            //?}
            Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light,
                                             //? >1.20.4 <1.21.3 {
                                             float tickDelta,
                                             //?}
                                             CallbackInfo ci) {
        EntityRenderStateWrapper state = EntityRenderStateWrapper.of(e);
        if (!state.isPlayer()) return;
        assert state.getPlayer() != null;
        if (!ModelShifterClient.state.isRendererEnabled(state.getPlayer())) return;
        PlayerModel model = ModelShifterClient.state.getState(state.getPlayer().getUuid()).getPlayerModel();
        if (model == null) return;

        matrices.translate(0f, model.getDimensions().labelOffset(), 0f);
    }
}
