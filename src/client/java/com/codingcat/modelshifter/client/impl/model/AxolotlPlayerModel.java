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

public class AxolotlPlayerModel extends PlayerModel {
    public AxolotlPlayerModel() {
        super(Identifier.of(ModelShifterClient.MOD_ID, "axolotl_player"), Set.of(Creators.DOMPLANTO),
                new ModelDimensions(1.2f, 0.8f, -1f, false));
    }

    @Override
    protected @NotNull FeatureRendererStates createFeatureRendererStates() {
        return new FeatureRendererStates()
                .add(FeatureRendererType.HELD_ITEM_RIGHT, AxolotlPlayerModel::modifyHeldRendering)
                .add(FeatureRendererType.HELD_ITEM_LEFT, AxolotlPlayerModel::modifyHeldRendering)
                .add(FeatureRendererType.ELYTRA, AxolotlPlayerModel::modifyElytraRendering)
                .add(FeatureRendererType.CAPE, AxolotlPlayerModel::modifyElytraRendering)
                .setSillyHatRenderModifier(AxolotlPlayerModel::modifySillyHatRendering);
    }

    @Override
    protected @NotNull GuiRenderInfo createGuiRenderInfo() {
        return new GuiRenderInfo()
                .setButtonRenderTweakFunction(AxolotlPlayerModel::modifyGuiInventoryRendering)
                .setShowcaseRenderTweakFunction(AxolotlPlayerModel::modifyGuiInventoryRendering)
                .setInventoryRenderTweakFunction(AxolotlPlayerModel::modifyGuiInventoryRendering);
    }

    private static void modifyElytraRendering(EntityRenderStateWrapper state, MatrixStack poseStack) {
        poseStack.scale(0.5f, 0.5f, 0.5f);
    }

    private static void modifyHeldRendering(EntityRenderStateWrapper state, MatrixStack poseStack) {
        poseStack.scale(0.6f, 0.6f, 0.6f);
    }

    private static void modifySillyHatRendering(MatrixStack poseStack) {
        poseStack.scale(0.95f, 0.95f, 0.95f);
    }

    private static void modifyGuiInventoryRendering(MatrixStack matrixStack) {
        matrixStack.scale(1.2f, 1.2f, 1.2f);
    }
}
