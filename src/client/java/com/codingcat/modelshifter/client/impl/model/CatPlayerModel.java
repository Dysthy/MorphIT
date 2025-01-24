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

public class CatPlayerModel extends PlayerModel {
    public CatPlayerModel() {
        super(Identifier.of(ModelShifterClient.MOD_ID, "cat_player"), Set.of(Creators.EGBERT),
                new ModelDimensions(1.2f, 0.8f, -1f, false));
    }

    @Override
    protected @NotNull FeatureRendererStates createFeatureRendererStates() {
        return new FeatureRendererStates()
                .add(FeatureRendererType.HELD_ITEM_RIGHT_RENDER_GROUND)
                .add(FeatureRendererType.ELYTRA, CatPlayerModel::modifyElytraRendering)
                .add(FeatureRendererType.TRIDENT_RIPTIDE);
    }

    private static void modifyElytraRendering(EntityRenderStateWrapper state, MatrixStack poseStack) {
        poseStack.scale(0.6f, 0.6f, 0.6f);
    }
}
