package com.codingcat.modelshifter.client.util;

import com.codingcat.modelshifter.client.ModelShifterClient;
import com.codingcat.modelshifter.client.api.entity.EntityRenderStateWrapper;
import com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererStates;
import com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererType;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

public class MixinUtil {
    public static void ifRendererEnabled(FeatureRendererType type, EntityRenderStateWrapper renderState, boolean invertFeatureRenderer, Consumer<FeatureRendererStates> run) {
        if (!isRendererEnabled(type, renderState, invertFeatureRenderer)) return;
        assert renderState.getPlayer() != null;
        FeatureRendererStates states = ModelShifterClient.state.accessFeatureRendererStates(renderState.getPlayer());

        run.accept(states);
    }

    public static boolean isRendererEnabled(FeatureRendererType type, EntityRenderStateWrapper renderState, boolean invertFeatureRenderer) {
        if (!renderState.isPlayer()) return false;
        assert renderState.getPlayer() != null;
        FeatureRendererStates states = ModelShifterClient.state.accessFeatureRendererStates(renderState.getPlayer());
        return ModelShifterClient.state.isRendererEnabled(renderState.getPlayer())
                && invertFeatureRenderer != states.isRendererEnabled(type);
    }

    public static void insertModifyRendering(FeatureRendererType type, EntityRenderStateWrapper renderState, MatrixStack matrixStack, CallbackInfo ci) {
        if (!renderState.isPlayer()) return;
        assert renderState.getPlayer() != null;
        if (!ModelShifterClient.state.isRendererEnabled(renderState.getPlayer())) return;
        FeatureRendererStates states = ModelShifterClient.state.accessFeatureRendererStates(renderState.getPlayer());
        if (states.isRendererEnabled(type))
            states.modifyRendering(type, renderState, matrixStack);
        else
            ci.cancel();
    }

    public static void setModelVisibility(PlayerEntity player, PlayerEntityModel model) {
        boolean rendererEnabled = ModelShifterClient.state.isRendererEnabled(player);
        model.setVisible(!rendererEnabled);
        model.head.visible = !rendererEnabled || player.isSpectator();
    }
}
