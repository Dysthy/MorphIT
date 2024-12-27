package com.codingcat.modelshifter.client.render;

import com.codingcat.modelshifter.client.ModelShifterClient;
import com.codingcat.modelshifter.client.api.model.PlayerModel;
import com.codingcat.modelshifter.client.api.renderer.feature.EnabledFeatureRenderer;
import com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererType;
import com.codingcat.modelshifter.client.render.entity.ReplacedPlayerEntity;
import com.codingcat.modelshifter.client.util.Util;
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
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("UnstableApiUsage")
public class ReplacedPlayerEntityRenderer extends GeoReplacedEntityRenderer<AbstractClientPlayerEntity, ReplacedPlayerEntity>
        implements FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel> {
    private final PlayerModel playerModel;
    private final PlayerEntityModel simulatedModel;
    private final Map<String, FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel>> featureRenderersByBone;
    private PlayerEntityRenderState currentState;
    private VertexConsumerProvider currentBufferSource;
    private int currentPackedLight;

    public ReplacedPlayerEntityRenderer(EntityRendererFactory.Context renderManager, PlayerModel model) {
        super(renderManager,
                new DefaultedEntityGeoModel<ReplacedPlayerEntity>(model.getModelDataIdentifier()).withAltTexture(ModelShifterClient.EMPTY_TEXTURE),
                new ReplacedPlayerEntity(model::getCurrentAnimation, true, false));
        this.playerModel = model;
        this.simulatedModel = this.createModel();
        this.featureRenderersByBone = new HashMap<>();
        this.addFeatures(renderManager);
    }

    private void addFeatures(EntityRendererFactory.Context ctx) {
        for (EnabledFeatureRenderer renderer : getPlayerModel().getFeatureRendererStates().getEnabledRenderers()) {
            FeatureRendererType type = renderer.type();
            this.featureRenderersByBone.put(type.getAssignedBone(), type.create(ctx, this));
        }
    }

    @Override
    public void render(EntityRenderState entityRenderState, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if (!(entityRenderState instanceof PlayerEntityRenderState playerState)) return;

        this.currentState = playerState;
        this.currentBufferSource = bufferSource;
        this.currentPackedLight = packedLight;
        RenderLayer type = getRenderType(animatable, playerState.skinTextures.texture(), bufferSource, partialTick);
        defaultRender(poseStack, this.animatable, bufferSource, type, null, this.partialTick, packedLight);
    }

    // Feature renderers have to be rendered in a specific phase during actuallyRender(), which is why the render calls are being placed in applyRotations(),
    // which is being called at that location in the actuallyRender() method of the GeoReplacedEntityRenderer instance. Since certain values (render state, buffer source, packet light) are
    // not directly available as parameters in this method, they have to be stored in helper variables to be accessed from here.
    @Override
    protected void applyRotations(ReplacedPlayerEntity animatable, MatrixStack poseStack, float ageInTicks, float rotationYaw, float partialTick, float nativeScale) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick, nativeScale);
        for (Map.Entry<String, FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel>> entry : this.featureRenderersByBone.entrySet()) {
            Optional<GeoBone> bone = model.getBone(entry.getKey());
            if (bone.isEmpty()) continue;

            poseStack.push();
            poseStack.multiplyPositionMatrix(bone.get().getModelSpaceMatrix());
            poseStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));
            FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> renderer = featureRenderersByBone.get(bone.get().getName());
            renderer.render(poseStack, this.currentBufferSource, this.currentPackedLight, this.currentState, currentState.yawDegrees, currentState.pitch);
            poseStack.pop();
        }
    }

    @Override
    public void updateRenderState(AbstractClientPlayerEntity entity, EntityRenderState entityRenderState, float partialTick) {
        super.updateRenderState(entity, entityRenderState, partialTick);
        try {
            Util.getNormalRendererReflect(entity, this.dispatcher)
                    .updateRenderState(entity, entityRenderState, partialTick);
        } catch (ReflectiveOperationException ignore) {}
    }

    private PlayerEntityModel createModel() {
        ModelData modelData = PlayerEntityModel.getTexturedModelData(Dilation.NONE, false);
        ModelPart part = TexturedModelData.of(modelData, 64, 64).createModel();
        return new PlayerEntityModel(part, false);
    }

    @Override
    public @Nullable EntityRenderState createRenderState() {
        return new PlayerEntityRenderState();
    }

    @Override
    public PlayerEntityModel getModel() {
        return this.simulatedModel;
    }

    public PlayerModel getPlayerModel() {
        return this.playerModel;
    }
}