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

public class DinoPlayerModel extends PlayerModel {
    public DinoPlayerModel() {
        super(Identifier.of(ModelShifterClient.MOD_ID, "dino_player"), Set.of(Creators.DOMPLANTO),
                new ModelDimensions(2f, 1.8f));
    }

    @Override
    protected @NotNull FeatureRendererStates createFeatureRendererStates() {
        return new FeatureRendererStates()
                .add(FeatureRendererType.HELD_ITEM_LEFT)
                .add(FeatureRendererType.HELD_ITEM_RIGHT)
                .add(FeatureRendererType.ELYTRA)
                .add(FeatureRendererType.CAPE)
                .add(FeatureRendererType.TRIDENT_RIPTIDE);
    }

    @Override
    protected @NotNull GuiRenderInfo createGuiRenderInfo() {
        return new GuiRenderInfo()
                .setShowcaseRenderTweakFunction(DinoPlayerModel::modifyGuiRendering)
                .setButtonRenderTweakFunction(DinoPlayerModel::modifyGuiRendering)
                .setInventoryRenderTweakFunction(DinoPlayerModel::modifyGuiInventoryRendering);
    }

    private static void modifyGuiRendering(MatrixStack matrixStack) {
        matrixStack.scale(0.5f, 0.5f, 0.5f);
    }

    private static void modifyGuiInventoryRendering(MatrixStack matrixStack) {
        matrixStack.scale(0.7f, 0.7f, 0.7f);
    }
}
