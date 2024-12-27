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

public class EndermanPlayerModel extends PlayerModel {
    public EndermanPlayerModel() {
        super(Identifier.of(ModelShifterClient.MOD_ID, "enderman_player"), Set.of(Creators.BUG),
                new ModelDimensions(0.7f, 3.2f, 1.3f));
    }

    @Override
    protected @NotNull FeatureRendererStates createFeatureRendererStates() {
        return new FeatureRendererStates()
                .add(FeatureRendererType.HELD_ITEM_LEFT)
                .add(FeatureRendererType.HELD_ITEM_RIGHT)
                .add(FeatureRendererType.ELYTRA);
    }

    @Override
    protected @NotNull GuiRenderInfo createGuiRenderInfo() {
        return new GuiRenderInfo()
                .setButtonRenderTweakFunction(EndermanPlayerModel::modifyGuiButtonRendering)
                .setShowcaseRenderTweakFunction(EndermanPlayerModel::modifyGuiShowcaseRendering)
                .setInventoryRenderTweakFunction(EndermanPlayerModel::modifyGuiInventoryRendering);
    }

    private static void modifyGuiButtonRendering(MatrixStack matrixStack) {
        matrixStack.scale(0.55f, 0.55f, 0.55f);
    }

    private static void modifyGuiShowcaseRendering(MatrixStack matrixStack) {
        matrixStack.scale(0.6f, 0.6f, 0.6f);
    }

    private static void modifyGuiInventoryRendering(MatrixStack matrixStack) {
        matrixStack.scale(0.7f, 0.7f, 0.7f);
        matrixStack.translate(0f,-0.2f,0f);
    }
}
