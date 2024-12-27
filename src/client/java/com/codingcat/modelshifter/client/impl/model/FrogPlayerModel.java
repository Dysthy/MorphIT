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

public class FrogPlayerModel extends PlayerModel {
    public FrogPlayerModel() {
        super(Identifier.of(ModelShifterClient.MOD_ID, "frog_player"), Set.of(Creators.BUG),
                new ModelDimensions(0.5f, 0.5f, -1.2f));
    }

    @Override
    protected @NotNull FeatureRendererStates createFeatureRendererStates() {
        return new FeatureRendererStates()
                .add(FeatureRendererType.HELD_ITEM_RIGHT)
                .add(FeatureRendererType.ELYTRA)
                .add(FeatureRendererType.TRIDENT_RIPTIDE);
    }

    @Override
    protected @NotNull GuiRenderInfo createGuiRenderInfo() {
        return new GuiRenderInfo()
                .setButtonRenderTweakFunction(FrogPlayerModel::modifyGuiButtonRendering)
                .setShowcaseRenderTweakFunction(FrogPlayerModel::modifyGuiShowcaseInventoryRendering)
                .setInventoryRenderTweakFunction(FrogPlayerModel::modifyGuiShowcaseInventoryRendering);
    }

    private static void modifyGuiShowcaseInventoryRendering(MatrixStack matrixStack) {
        matrixStack.scale(1.3f, 1.3f, 1.3f);
    }

    private static void modifyGuiButtonRendering(MatrixStack matrixStack) {
        matrixStack.scale(1.6f, 1.6f, 1.6f);
    }
}
