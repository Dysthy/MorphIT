package com.codingcat.modelshifter.client.api.renderer.feature;

import com.codingcat.modelshifter.client.render.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.*;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.util.Arm;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

import static com.codingcat.modelshifter.client.api.GeoFileDefaults.*;

@SuppressWarnings("unused")
public enum FeatureRendererType {
    HELD_ITEM_RIGHT(BONE_HELD_ITEM_RIGHT_ID, (ctx, featureCtx) -> new HeldItemFeatureRenderer<>(featureCtx, Arm.RIGHT, ctx.getItemRenderer())),
    HELD_ITEM_LEFT(BONE_HELD_ITEM_LEFT_ID, (ctx, featureCtx) -> new HeldItemFeatureRenderer<>(featureCtx, Arm.LEFT, ctx.getItemRenderer())),
    ELYTRA(BONE_ELYTRA_ID, (ctx, featureCtx) -> new ElytraFeatureRenderer<>(featureCtx, ctx.getModelLoader(), ctx.getEquipmentRenderer())),
    CAPE(BONE_CAPE_ID, (ctx, featureCtx) -> new CapeFeatureRenderer(featureCtx, ctx.getModelLoader(), ctx.getEquipmentModelLoader())),
    SHOULDER_PARROT(BONE_PARROT_ID, (ctx, featureCtx) -> new ShoulderParrotFeatureRenderer(featureCtx, ctx.getModelLoader())),
    TRIDENT_RIPTIDE((ctx, featureCtx) -> new TridentRiptideFeatureRenderer(featureCtx, ctx.getModelLoader()));

    @Nullable
    private final String assignedBone;
    private final BiFunction<EntityRendererFactory.Context, FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel>,
            FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel>> initFunction;

    FeatureRendererType(BiFunction<EntityRendererFactory.Context, FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel>,
            FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel>> initFunction) {
        this(null, initFunction);
    }

    FeatureRendererType(@Nullable String assignedBone, BiFunction<EntityRendererFactory.Context, FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel>,
            FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel>> initFunction) {
        this.assignedBone = assignedBone;
        this.initFunction = initFunction;
    }

    public @Nullable String getAssignedBone() {
        return assignedBone;
    }

    public FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> create(EntityRendererFactory.Context renderFactoryContext,
                                                                              FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel> featureRendererContext) {
        return initFunction.apply(renderFactoryContext, featureRendererContext);
    }
}
