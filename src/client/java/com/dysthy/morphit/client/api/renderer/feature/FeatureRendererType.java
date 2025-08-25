package com.dysthy.morphit.client.api.renderer.feature;

import com.dysthy.morphit.client.render.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.*;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
//? >=1.21.4 {
import net.minecraft.client.render.entity.model.LoadedEntityModels;
//?} else {
/*import net.minecraft.client.render.entity.model.EntityModelLoader;
*///?}
//? >=1.21.3 {
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ModelTransformationMode;
//?} else {
/*import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
*///?}
import net.minecraft.util.Arm;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

import static com.dysthy.morphit.client.api.GeoFileDefaults.*;

@SuppressWarnings({"unused", "rawtypes", "unchecked", "RedundantSuppression"})
public enum FeatureRendererType {
    HELD_ITEM_RIGHT(BONE_HELD_ITEM_RIGHT_ID, (ctx, featureCtx) -> new HeldItemFeatureRenderer<>(featureCtx, Arm.RIGHT, getHeldItemRenderer(ctx))),
    HELD_ITEM_RIGHT_RENDER_GROUND(BONE_HELD_ITEM_RIGHT_ID, (ctx, featureCtx) -> new HeldItemFeatureRenderer<>(featureCtx, Arm.RIGHT, getHeldItemRenderer(ctx))
            .withTransformationMode(ModelTransformationMode.GROUND)),
    HELD_ITEM_LEFT(BONE_HELD_ITEM_LEFT_ID, (ctx, featureCtx) -> new HeldItemFeatureRenderer<>(featureCtx, Arm.LEFT, getHeldItemRenderer(ctx))),
    HELD_ITEM_LEFT_RENDER_GROUND(BONE_HELD_ITEM_LEFT_ID, (ctx, featureCtx) -> new HeldItemFeatureRenderer<>(featureCtx, Arm.LEFT, getHeldItemRenderer(ctx))
            .withTransformationMode(ModelTransformationMode.GROUND)),
    ELYTRA(BONE_ELYTRA_ID, (ctx, featureCtx) -> new ElytraFeatureRenderer<>(featureCtx, getModelLoader(ctx)
            //? >=1.21.3 {
            , ctx.getEquipmentRenderer()
            //?}
    )),
    CAPE(BONE_CAPE_ID, (ctx, featureCtx) -> new CapeFeatureRenderer(featureCtx
            //? >=1.21.3 {
            , getModelLoader(ctx), ctx.getEquipmentModelLoader()
            //?}
    )),
    SHOULDER_PARROT(BONE_PARROT_ID, (ctx, featureCtx) -> new ShoulderParrotFeatureRenderer(featureCtx, getModelLoader(ctx))),
    TRIDENT_RIPTIDE((ctx, featureCtx) -> new TridentRiptideFeatureRenderer(featureCtx, getModelLoader(ctx)));

    @Nullable
    private final String assignedBone;
    //? <1.21.3 {
    /*private final BiFunction<EntityRendererFactory.Context, FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>,
            FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>> initFunction;
    *///?} else {
    private final BiFunction<EntityRendererFactory.Context, FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel>,
            FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel>> initFunction;
    //?}

    //? <1.21.3 {
    /*FeatureRendererType(BiFunction<EntityRendererFactory.Context, FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>,
            FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>> initFunction) {
    *///?} else {
    FeatureRendererType(BiFunction<EntityRendererFactory.Context, FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel>,
            FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel>> initFunction) {
    //?}
        this(null, initFunction);
    }

    //? <1.21.3 {
    /*FeatureRendererType(@Nullable String assignedBone, BiFunction<EntityRendererFactory.Context, FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>,
            FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>> initFunction) {
    *///?} else {
    FeatureRendererType(@Nullable String assignedBone, BiFunction<EntityRendererFactory.Context, FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel>,
            FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel>> initFunction) {
    //?}
        this.assignedBone = assignedBone;
        this.initFunction = initFunction;
    }

    public @Nullable String getAssignedBone() {
        return assignedBone;
    }

    //? <1.21.3 {
    /*public FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> create(EntityRendererFactory.Context renderFactoryContext,
                                                                              FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> featureRendererContext) {
    *///?} else {
    public FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> create(EntityRendererFactory.Context renderFactoryContext,
            FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel> featureRendererContext) {
    //?}
        return initFunction.apply(renderFactoryContext, featureRendererContext);
    }

    //? >=1.21.4 {
    private static ItemRenderer getHeldItemRenderer(EntityRendererFactory.Context ctx) {
        return null;
    }
    //?} >=1.21.3 {
    /*private static ItemRenderer getHeldItemRenderer(EntityRendererFactory.Context ctx) {
        return ctx.getItemRenderer();
    }
    *///?} else {
    
     /*private static HeldItemRenderer getHeldItemRenderer(EntityRendererFactory.Context ctx) {
        return ctx.getHeldItemRenderer();
    }
    *///?}

    @NotNull
    public String getTranslationKey() {
        return String.format("modelshifter.text.model_feature.%s", name().toLowerCase());
    }

    //? >=1.21.4 {
    private static LoadedEntityModels getModelLoader(EntityRendererFactory.Context context) {
        return context.getEntityModels();
    }
    //?} else {
    /*private static EntityModelLoader getModelLoader(EntityRendererFactory.Context context) {
        return context.getModelLoader();
    }
    *///?}
}
