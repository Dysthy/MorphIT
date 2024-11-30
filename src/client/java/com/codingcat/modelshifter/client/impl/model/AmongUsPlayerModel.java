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

public class AmongUsPlayerModel extends PlayerModel {
    public AmongUsPlayerModel() {
        super(Identifier.of(ModelShifterClient.MOD_ID, "among_us_player"), Set.of(Creators.EGBERT),
                new ModelDimensions(0.7f, 1.6f, -0.4f));
    }

    @Override
    protected @NotNull FeatureRendererStates createFeatureRendererStates() {
        return new FeatureRendererStates()
                .add(FeatureRendererType.HELD_ITEM, AmongUsPlayerModel::modifyHeldItemRendering)
                .add(FeatureRendererType.ELYTRA)
                .add(FeatureRendererType.CAPE)
                .add(FeatureRendererType.TRIDENT_RIPTIDE);
    }

    private static void modifyHeldItemRendering(EntityRenderStateWrapper state, MatrixStack matrixStack) {
        matrixStack.translate(0.3f, 0.2f, 0f);
        if (state.isInSneakingPose())
            matrixStack.translate(0f, 0f, -0.2f);
    }
}
