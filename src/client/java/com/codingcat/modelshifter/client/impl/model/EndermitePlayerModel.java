package com.codingcat.modelshifter.client.impl.model;

import com.codingcat.modelshifter.client.ModelShifterClient;
import com.codingcat.modelshifter.client.api.entity.EntityRenderStateWrapper;
import com.codingcat.modelshifter.client.api.model.ModelDimensions;
import com.codingcat.modelshifter.client.api.model.PlayerModel;
import com.codingcat.modelshifter.client.api.renderer.GuiRenderInfo;
import com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererStates;
import com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererType;
import com.codingcat.modelshifter.client.impl.Creators;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class EndermitePlayerModel extends PlayerModel {
    public EndermitePlayerModel() {
        super(Identifier.of(ModelShifterClient.MOD_ID, "endermite_player"), Set.of(Creators.DOMPLANTO),
                new ModelDimensions(1.2f, 0.8f, -1f, false));
    }

    @Override
    protected @NotNull FeatureRendererStates createFeatureRendererStates() {
        return new FeatureRendererStates()
                .add(FeatureRendererType.HELD_ITEM_RIGHT, EndermitePlayerModel::modifyHeldRendering)
                .add(FeatureRendererType.HELD_ITEM_LEFT, EndermitePlayerModel::modifyHeldRendering)
                .add(FeatureRendererType.ELYTRA, EndermitePlayerModel::modifyElytraRendering)
                .add(FeatureRendererType.CAPE, EndermitePlayerModel::modifyElytraRendering)
                .setSillyHatRenderModifier(EndermitePlayerModel::modifySillyHatRendering);
    }

    @Override
    protected @NotNull GuiRenderInfo createGuiRenderInfo() {
        return new GuiRenderInfo()
                .setButtonRenderTweakFunction(EndermitePlayerModel::modifyGuiInventoryRendering)
                .setShowcaseRenderTweakFunction(EndermitePlayerModel::modifyGuiInventoryRendering)
                .setInventoryRenderTweakFunction(EndermitePlayerModel::modifyGuiInventoryRendering);
    }

    private static void modifyElytraRendering(EntityRenderStateWrapper state, MatrixStack poseStack) {
        poseStack.scale(0.4f, 0.4f, 0.4f);
    }

    private static void modifyHeldRendering(EntityRenderStateWrapper state, MatrixStack poseStack) {
        poseStack.scale(0.5f, 0.5f, 0.5f);
    }

    private static void modifySillyHatRendering(MatrixStack poseStack) {
        poseStack.scale(0.45f, 0.45f, 0.45f);
        poseStack.translate(0.5f, 0.51f, 0.5f);
    }

    private static void modifyGuiInventoryRendering(MatrixStack matrixStack) {
        matrixStack.scale(1.5f, 1.5f, 1.5f);
    }
}
