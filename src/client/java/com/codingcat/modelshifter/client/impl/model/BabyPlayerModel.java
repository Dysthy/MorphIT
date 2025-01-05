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

public class BabyPlayerModel extends PlayerModel {
    public BabyPlayerModel() {
        super(Identifier.of(ModelShifterClient.MOD_ID, "baby_player"), Set.of(Creators.DOMPLANTO),
                new ModelDimensions(0.5f, 1.2f, -0.6f));
    }

    @Override
    protected @NotNull FeatureRendererStates createFeatureRendererStates() {
        return new FeatureRendererStates()
                .add(FeatureRendererType.HELD_ITEM_LEFT, BabyPlayerModel::modifyHeldItemRendering)
                .add(FeatureRendererType.HELD_ITEM_RIGHT, BabyPlayerModel::modifyHeldItemRendering)
                .add(FeatureRendererType.ELYTRA, BabyPlayerModel::modifyCloakRendering)
                .add(FeatureRendererType.CAPE, BabyPlayerModel::modifyCloakRendering)
                .add(FeatureRendererType.TRIDENT_RIPTIDE);
    }

    private static void modifyHeldItemRendering(EntityRenderStateWrapper state, MatrixStack poseStack) {
        poseStack.scale(0.7f,0.7f,0.7f);
    }

    private static void modifyCloakRendering(EntityRenderStateWrapper state, MatrixStack poseStack) {
        poseStack.scale(0.5f,0.5f,0.5f);
    }
}
