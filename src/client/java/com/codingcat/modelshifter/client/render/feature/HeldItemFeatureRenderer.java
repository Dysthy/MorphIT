package com.codingcat.modelshifter.client.render.feature;


import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import org.jetbrains.annotations.NotNull;
//? >=1.21.3 {
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import org.jetbrains.annotations.Nullable;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.client.render.OverlayTexture;
//?} else {
/*import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.entity.LivingEntity;
*///?}
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.RotationAxis;

//? <1.21.3 {
/*public class HeldItemFeatureRenderer<E extends AbstractClientPlayerEntity, M extends EntityModel<E>>
        extends FeatureRenderer<E, M> {
    *///?} else {
public class HeldItemFeatureRenderer<S extends LivingEntityRenderState, M extends EntityModel<S>>
        extends FeatureRenderer<S, M> {
//?}
    @NotNull
    private ModelTransformationMode transformationMode;
    //? <1.21.3 {
    /*private final HeldItemRenderer itemRenderer;
    *///?} else {
    private final ItemRenderer itemRenderer;
     //?}
    private final Arm arm;

    //? <1.21.3 {
    /*public HeldItemFeatureRenderer(FeatureRendererContext<E, M> context, Arm arm, HeldItemRenderer itemRenderer) {
        *///?} else {
        public HeldItemFeatureRenderer(FeatureRendererContext<S, M> context, Arm arm, ItemRenderer itemRenderer) {
         //?}
        super(context);
        this.itemRenderer = itemRenderer;
        this.arm = arm;
        this.transformationMode = ModelTransformationMode.THIRD_PERSON_RIGHT_HAND;
    }

    //? <1.21.3 {
    /*@Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, E entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        boolean mainHandLeft = entity.getMainArm() == Arm.LEFT;
        ItemStack leftHandStack = mainHandLeft ? entity.getMainHandStack() : entity.getOffHandStack();
        ItemStack rightHandStack = mainHandLeft ? entity.getOffHandStack() : entity.getMainHandStack();

        this.renderItem(arm == Arm.LEFT ? leftHandStack : rightHandStack, entity, matrixStack, vertexConsumerProvider, light);
    }
    *///?} else {
    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, S state, float f, float g) {
        this.renderItem(arm == Arm.LEFT ? state.leftHandItemModel : state.rightHandItemModel,
                arm == Arm.LEFT ? state.leftHandStack : state.rightHandStack, matrixStack, vertexConsumerProvider, i);
    }
    //?}

    //? <1.21.3 {
    /*protected void renderItem(ItemStack stack, LivingEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (stack.isEmpty()) return;
        *///?} else {
    protected void renderItem(@Nullable BakedModel model, ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (model == null || stack.isEmpty()) return;
    //?}

        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0f));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f));
        //? <1.21.3 {
        /*this.itemRenderer.renderItem(entity, stack, this.transformationMode, false,
                matrices, vertexConsumers, light);
        *///?} else {
        this.itemRenderer.renderItem(stack, this.transformationMode, false,
                matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, model);
        //?}
        matrices.pop();
    }

    //? <1.21.3 {
    /*public HeldItemFeatureRenderer<E, M> withTransformationMode(ModelTransformationMode transformationMode) {
        *///?} else {
        public HeldItemFeatureRenderer<S, M> withTransformationMode(@NotNull ModelTransformationMode transformationMode) {
         //?}
        this.transformationMode = transformationMode;
        return this;
    }
}

