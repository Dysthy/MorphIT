package com.codingcat.modelshifter.client.impl.model;

import com.codingcat.modelshifter.client.ModelShifterClient;
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

public class CreakingPlayerModel extends PlayerModel {
    public CreakingPlayerModel() {
        super(Identifier.of(ModelShifterClient.MOD_ID, "creaking_player"), Set.of(Creators.BUG),
                new ModelDimensions(0.8f, 2.8f, 0.9f, true));
    }

    @Override
    protected @NotNull FeatureRendererStates createFeatureRendererStates() {
        return new FeatureRendererStates()
                .setSillyHatRenderModifier(CreakingPlayerModel::modifySillyHatRendering)
                .add(FeatureRendererType.HELD_ITEM_LEFT)
                .add(FeatureRendererType.HELD_ITEM_RIGHT)
                .add(FeatureRendererType.ELYTRA)
                .add(FeatureRendererType.CAPE)
                .add(FeatureRendererType.TRIDENT_RIPTIDE);
    }

    @Override
    protected @NotNull GuiRenderInfo createGuiRenderInfo() {
        return new GuiRenderInfo()
                .setButtonRenderTweakFunction(CreakingPlayerModel::modifyGuiButtonRendering)
                .setShowcaseRenderTweakFunction(CreakingPlayerModel::modifyGuiShowcaseInventoryRendering)
                .setInventoryRenderTweakFunction(CreakingPlayerModel::modifyGuiShowcaseInventoryRendering);
    }

    private static void modifyGuiButtonRendering(MatrixStack matrixStack) {
        matrixStack.scale(0.6f, 0.6f, 0.6f);
    }

    private static void modifyGuiShowcaseInventoryRendering(MatrixStack matrixStack) {
        matrixStack.scale(0.7f, 0.7f, 0.7f);
    }

    private static void modifySillyHatRendering(MatrixStack matrixStack) {
        matrixStack.scale(0.8f, 0.8f, 0.8f);
    }
}
