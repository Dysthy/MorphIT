package com.codingcat.modelshifter.client.render;

import com.codingcat.modelshifter.client.ModelShifterClient;
import com.codingcat.modelshifter.client.api.entity.EntityRenderStateWrapper;
import com.codingcat.modelshifter.client.api.model.PlayerModel;
import com.codingcat.modelshifter.client.api.renderer.feature.EnabledFeatureRenderer;
import com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererType;
import com.codingcat.modelshifter.client.render.animatable.ReplacedPlayerEntity;
import com.codingcat.modelshifter.client.render.layer.SillyHatRenderLayer;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
//? >=1.21.3 {
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import com.codingcat.modelshifter.client.util.Util;
import org.jetbrains.annotations.Nullable;
//?}
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;

import java.util.*;
import java.util.function.BiConsumer;

//? <1.21.3 {
/*@SuppressWarnings({"rawtypes", "UnstableApiUsage", "unchecked", "RedundantSuppression"})
public class ReplacedPlayerEntityRenderer extends GeoReplacedEntityRenderer<AbstractClientPlayerEntity, ReplacedPlayerEntity>
        implements FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    *///?} else {
@SuppressWarnings({"UnstableApiUsage", "UnnecessaryLocalVariable"})
public class ReplacedPlayerEntityRenderer extends GeoReplacedEntityRenderer<AbstractClientPlayerEntity, ReplacedPlayerEntity>
        implements FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel> {
    //?}
    private final PlayerModel playerModel;
    private final PlayerEntityModel simulatedModel;
    private final Map<String, ActiveFeatureRenderer> featureRenderersByBone;
    private final Set<ActiveFeatureRenderer> featureRenderers;
    //? >=1.21.3 {
    private PlayerEntityRenderState currentState;
    //?}
    private VertexConsumerProvider currentBufferSource;
    private int currentPackedLight;

    public ReplacedPlayerEntityRenderer(EntityRendererFactory.Context renderManager, PlayerModel model) {
        super(renderManager,
                new DefaultedEntityGeoModel<ReplacedPlayerEntity>(model.getModelDataIdentifier()).withAltTexture(ModelShifterClient.EMPTY_TEXTURE),
                new ReplacedPlayerEntity(model::getCurrentAnimation, true, false));
        this.playerModel = model;
        this.simulatedModel = this.createModel();
        this.featureRenderersByBone = new HashMap<>();
        this.featureRenderers = new HashSet<>();
        this.addRenderLayer(new SillyHatRenderLayer(this));
        this.addFeatures(renderManager);
    }

    private void addFeatures(EntityRendererFactory.Context ctx) {
        for (EnabledFeatureRenderer renderer : getPlayerModel().getFeatureRendererStates().getEnabledRenderers()) {
            FeatureRendererType type = renderer.type();
            //? <1.21.3 {
            /*FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> featureRenderer = type.create(ctx, this);
             *///?} else {
            FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> featureRenderer = type.create(ctx, this);
            //?}
            if (type.getAssignedBone() == null) {
                this.featureRenderers.add(new ActiveFeatureRenderer(featureRenderer, renderer.renderModifierConsumer()));
                continue;
            }

            this.featureRenderersByBone.put(type.getAssignedBone(), new ActiveFeatureRenderer(featureRenderer, renderer.renderModifierConsumer()));
        }
    }

    //? <1.21.3 {
    /*@Override
    public void render(AbstractClientPlayerEntity e, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        *///?} else {
    @Override
    public void render(EntityRenderState entityRenderState, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if (!(entityRenderState instanceof PlayerEntityRenderState e)) return;
        this.currentState = e;
        //?}
        //? <1.21.3 {
        /*this.currentEntity = e;
         *///?}
        this.currentBufferSource = bufferSource;
        this.currentPackedLight = packedLight;
        RenderLayer type = getRenderLayer(EntityRenderStateWrapper.of(e), bufferSource, partialTick);
        defaultRender(poseStack, this.animatable, bufferSource, type, null,
                //? <1.21.3 {
                /*entityYaw,
                 *///?}
                partialTick, packedLight);
        //? <1.21.3 {
        /*this.currentEntity = null;
         *///?}
    }

    // Feature renderers have to be rendered in a specific phase during actuallyRender(), which is why the render calls are being placed in applyRotations(),
    // which is being called at that location in the actuallyRender() method of the GeoReplacedEntityRenderer instance. Since certain values (render state (>=1.21.3), buffer source, packet light) are
    // not directly available as parameters in this method, they have to be stored in helper variables to be accessed from here.
    @Override
    protected void applyRotations(ReplacedPlayerEntity animatable, MatrixStack poseStack, float ageInTicks, float rotationYaw, float partialTick
                                  //? >1.20.6 {
            , float nativeScale
                                  //?}
    ) {
        //? >=1.21.3 {
        EntityRenderStateWrapper state = EntityRenderStateWrapper.of(this.currentState);
        //?} else {
        /*EntityRenderStateWrapper state = EntityRenderStateWrapper.of(this.currentEntity, partialTick);
         *///?}
        //? >1.20.6 {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick, nativeScale);
        //?} else {
        /*super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
         *///?}
        this.setupTransforms(state, poseStack);
        for (Map.Entry<String, ActiveFeatureRenderer> entry : this.featureRenderersByBone.entrySet()) {
            Optional<GeoBone> bone = model.getBone(entry.getKey());
            if (bone.isEmpty()) continue;

            poseStack.push();
            poseStack.multiplyPositionMatrix(bone.get().getModelSpaceMatrix());
            //? <1.21.3 {
            /*FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> renderer = featureRenderersByBone.get(bone.get().getName()).renderer();
             *///?} else {
            FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> renderer = featureRenderersByBone.get(bone.get().getName()).renderer();
            //?}
            this.runFeatureRenderer(poseStack, renderer, partialTick, entry.getValue().renderModifierConsumer());
        }

        for (ActiveFeatureRenderer featureRenderer : this.featureRenderers) {
            poseStack.push();
            this.runFeatureRenderer(poseStack, featureRenderer.renderer(), partialTick, featureRenderer.renderModifierConsumer());
        }

        // Prevents the currentLayer of the VertexConsumerProvider from changing to a layer used by the feature renderers
        //? <=1.20.6 {
        /*RenderLayer layer = getRenderLayer(state, currentBufferSource, partialTick);
        if (layer != null)
            this.currentBufferSource.getBuffer(layer);
        *///?}
    }

    private void setupTransforms(EntityRenderStateWrapper state, MatrixStack poseStack) {
        float leaningPitch = state.leaningPitch();
        float pitch = state.pitch();
        if (state.isGliding()) {
            float rotation = playerModel.getDimensions().isStandingModel() ? -90f : 0f;
            if (!state.usingRiptide())
                poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(state.getGlidingProgress() * (rotation - pitch)));
            if (state.applyFlyingRotation())
                poseStack.multiply(RotationAxis.POSITIVE_Y.rotation(state.flyingRotation()));
        } else if (leaningPitch > 0.0f) {
            float j = state.touchingWater() ? -90.0f - pitch : -90.0f;
            float k = MathHelper.lerp(leaningPitch, 0.0f, j);
            poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(k));
            if (state.isSwimming())
                poseStack.translate(0.0f, -1.0f, 0.3f);
        }
    }

    private void runFeatureRenderer(MatrixStack poseStack,
                                    //? <1.21.3 {
            /*FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> featureRenderer,
             *///?} else {
                                    FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> featureRenderer,
                                    //?}
                                    @SuppressWarnings({"unused"}) float tickDelta,
                                    BiConsumer<EntityRenderStateWrapper, MatrixStack> renderModifierConsumer) {
        poseStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));
        if (renderModifierConsumer != null)
            //? <1.21.3 {
            /*renderModifierConsumer.accept(EntityRenderStateWrapper.of(this.currentEntity), poseStack);
             *///?} else {
            renderModifierConsumer.accept(EntityRenderStateWrapper.of(this.currentState), poseStack);
        //?}
        //? <1.21.3 {
        /*featureRenderer.render(poseStack, this.currentBufferSource, this.currentPackedLight, this.currentEntity, 0, 0, tickDelta, (float) currentEntity.age + tickDelta, 0, 0);
         *///?} else {
        featureRenderer.render(poseStack, this.currentBufferSource, this.currentPackedLight, this.currentState, currentState.yawDegrees, currentState.pitch);
        //?}
        poseStack.pop();
    }

    //? >=1.21.3 {
    @Override
    public @Nullable EntityRenderState createRenderState() {
        return new PlayerEntityRenderState();
    }

    @Override
    public void updateRenderState(AbstractClientPlayerEntity entity, EntityRenderState entityRenderState,
                                  float partialTick) {
        super.updateRenderState(entity, entityRenderState, partialTick);
        try {
            Util.getNormalRendererReflect(entity, this.dispatcher)
                    .updateRenderState(entity, entityRenderState, partialTick);
        } catch (ReflectiveOperationException ignore) {
        }
    }
    //?}

    private PlayerEntityModel createModel() {
        ModelData modelData = PlayerEntityModel.getTexturedModelData(Dilation.NONE, false);
        ModelPart part = TexturedModelData.of(modelData, 64, 64).createModel();
        PlayerEntityModel playerEntityModel = new PlayerEntityModel(part, false);
        //? <1.21.3 {
        /*playerEntityModel.child = false;
         *///?}
        return playerEntityModel;
    }

    @Override
    public PlayerEntityModel getModel() {
        return this.simulatedModel;
    }

    public PlayerModel getPlayerModel() {
        return this.playerModel;
    }

    private RenderLayer getRenderLayer(EntityRenderStateWrapper state, VertexConsumerProvider bufferSource,
                                       float partialTick) {
        return getRenderType(animatable, state.getSkinTexture(), bufferSource, partialTick);
    }

    private record ActiveFeatureRenderer(
            //? <1.21.3 {
            /*FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> renderer,
             *///?} else {
            FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> renderer,
            //?}
            BiConsumer<EntityRenderStateWrapper, MatrixStack> renderModifierConsumer
    ) {
    }
}