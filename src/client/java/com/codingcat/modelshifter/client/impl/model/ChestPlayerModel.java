package com.codingcat.modelshifter.client.impl.model;

import com.codingcat.modelshifter.client.ModelShifterClient;
import com.codingcat.modelshifter.client.api.entity.EntityRenderStateWrapper;
import com.codingcat.modelshifter.client.api.model.ModelDimensions;
import com.codingcat.modelshifter.client.api.model.PlayerModel;
import com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererStates;
import com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererType;
import com.codingcat.modelshifter.client.impl.Creators;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ChestPlayerModel extends PlayerModel {
    public ChestPlayerModel() {
        super(Identifier.of(ModelShifterClient.MOD_ID, "chest_player"), Set.of(Creators.BUG),
                new ModelDimensions(1f, 1f, -0.8f,true));
    }

    @Override
    protected @NotNull FeatureRendererStates createFeatureRendererStates() {
        return new FeatureRendererStates()
                .add(FeatureRendererType.HELD_ITEM_LEFT_RENDER_GROUND)
                .add(FeatureRendererType.HELD_ITEM_RIGHT)
                .add(FeatureRendererType.ELYTRA, ChestPlayerModel::modifyCloakRendering)
                .add(FeatureRendererType.CAPE, ChestPlayerModel::modifyCloakRendering)
                .add(FeatureRendererType.TRIDENT_RIPTIDE);
    }

    private static void modifyCloakRendering(EntityRenderStateWrapper state, MatrixStack poseStack) {
        poseStack.scale(1f,0.8f,1f);
    }
}
