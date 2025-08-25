package com.dysthy.morphit.client.impl.model;

import com.dysthy.morphit.client.api.model.ModelDimensions;
import com.dysthy.morphit.client.api.model.PlayerModel;
import com.dysthy.morphit.client.api.renderer.GuiRenderInfo;
import com.dysthy.morphit.client.api.renderer.feature.FeatureRendererStates;

import com.dysthy.morphit.client.api.renderer.feature.FeatureRendererType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;


public class ExternalPlayerModel extends PlayerModel {

    public ExternalPlayerModel(Identifier modelId) {
        super(modelId, Set.of("external"), new ModelDimensions(1f, 1f, 0f, true));
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
        return new GuiRenderInfo();
    }
}


