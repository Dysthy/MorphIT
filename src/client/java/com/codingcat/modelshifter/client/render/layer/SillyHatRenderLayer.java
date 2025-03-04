package com.codingcat.modelshifter.client.render.layer;

import com.codingcat.modelshifter.client.ModelShifterClient;
import com.codingcat.modelshifter.client.api.GeoFileDefaults;
import com.codingcat.modelshifter.client.api.model.PlayerModel;
import com.codingcat.modelshifter.client.render.animatable.ReplacedPlayerEntity;
import com.codingcat.modelshifter.client.render.animatable.SillyHatAnimatable;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoObjectRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class SillyHatRenderLayer extends GeoRenderLayer<ReplacedPlayerEntity> {
    private final static Identifier SILLY_HAT_ID = Identifier.of(ModelShifterClient.MOD_ID, "the_silly_hat");
    private final static Identifier SILLY_HAT_TEXTURE = SILLY_HAT_ID.withPrefixedPath("textures/entity/").withSuffixedPath(".png");
    private final SillyHatAnimatable sillyHatAnimatable = new SillyHatAnimatable();
    private final GeoObjectRenderer<SillyHatAnimatable> renderer;
    private final PlayerModel model;

    public SillyHatRenderLayer(GeoRenderer<ReplacedPlayerEntity> entityRendererIn, PlayerModel model) {
        super(entityRendererIn);
        this.model = model;
        this.renderer = new GeoObjectRenderer<>(new DefaultedEntityGeoModel<>(SILLY_HAT_ID, false));
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void renderForBone(MatrixStack poseStack, ReplacedPlayerEntity animatable, GeoBone bone, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay, int renderColor) {
        if (!bone.getName().equals(GeoFileDefaults.BONE_SILLY_HAT_ID)) return;

        poseStack.push();
        //Undo the model translation from GeoObjectRenderer#preRender
        poseStack.translate(-0.5f, -0.51f, -0.5f);
        poseStack.multiplyPositionMatrix(bone.getModelSpaceMatrix());
        this.model.getFeatureRendererStates().applySillyHatRenderModifier(poseStack);
        RenderLayer sillyHatRenderType = this.renderer.getRenderType(sillyHatAnimatable, SILLY_HAT_TEXTURE, bufferSource, partialTick);
        this.renderer.render(poseStack, sillyHatAnimatable, bufferSource, sillyHatRenderType, null, packedLight, partialTick);
        poseStack.pop();
    }
}
