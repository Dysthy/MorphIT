package com.codingcat.modelshifter.client.impl.model;

import com.codingcat.modelshifter.client.api.model.ModelDimensions;
import com.codingcat.modelshifter.client.api.model.PlayerModel;
import com.codingcat.modelshifter.client.api.renderer.GuiRenderInfo;
import com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererStates;

import org.jetbrains.annotations.NotNull;

import java.util.Set;


public class ExternalPlayerModel extends PlayerModel {

    public ExternalPlayerModel(Identifier modelId) {
        super(modelId, Set.of("external"), new ModelDimensions(1f, 1f, 0f, true));
    }

    @Override
    protected @NotNull FeatureRendererStates createFeatureRendererStates() {
        return new FeatureRendererStates()
                .add(com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererType.HELD_ITEM_LEFT)
                .add(com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererType.HELD_ITEM_RIGHT)
                .add(com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererType.ELYTRA)
                .add(com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererType.CAPE)
                .add(com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererType.TRIDENT_RIPTIDE);
    }

    @Override
    protected @NotNull GuiRenderInfo createGuiRenderInfo() {
        return new GuiRenderInfo();
    }
}


