package com.codingcat.modelshifter.client.render.feature;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.Arm;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.Nullable;

public class HeldItemFeatureRenderer<S extends LivingEntityRenderState, M extends EntityModel<S>>
        extends FeatureRenderer<S, M> {
    private final ItemRenderer itemRenderer;
    private final Arm arm;

    public HeldItemFeatureRenderer(FeatureRendererContext<S, M> context, Arm arm, ItemRenderer itemRenderer) {
        super(context);
        this.itemRenderer = itemRenderer;
        this.arm = arm;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, S state, float f, float g) {
        this.renderItem(arm == Arm.LEFT ? state.leftHandItemModel : state.rightHandItemModel,
                arm == Arm.LEFT ? state.leftHandStack : state.rightHandStack, matrixStack, vertexConsumerProvider, i);
    }

    protected void renderItem(@Nullable BakedModel model, ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (model == null || stack.isEmpty()) return;

        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0f));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f));
        this.itemRenderer.renderItem(stack, ModelTransformationMode.THIRD_PERSON_RIGHT_HAND, false,
                matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, model);
        matrices.pop();
    }
}

