package com.codingcat.modelshifter.client.impl.model;

import com.codingcat.modelshifter.client.ModelShifterClient;
import com.codingcat.modelshifter.client.api.GeoFileDefaults;
import com.codingcat.modelshifter.client.api.animation.ModelAnimationController;
import com.codingcat.modelshifter.client.api.model.ModelDimensions;
import com.codingcat.modelshifter.client.api.model.PlayerModel;
import com.codingcat.modelshifter.client.api.renderer.GuiRenderInfo;
import com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererStates;
import com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererType;
import com.codingcat.modelshifter.client.impl.Creators;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.constant.DefaultAnimations;

import java.util.Set;

public class WitherPlayerModel extends PlayerModel {
    public WitherPlayerModel() {
        super(Identifier.of(ModelShifterClient.MOD_ID, "wither_player"), Set.of(Creators.BUG),
                new ModelDimensions(1.5f, 1.8f, true));
    }

    @Override
    protected @NotNull FeatureRendererStates createFeatureRendererStates() {
        return new FeatureRendererStates()
                .add(FeatureRendererType.HELD_ITEM_RIGHT)
                .add(FeatureRendererType.TRIDENT_RIPTIDE);
    }

    @Override
    protected @NotNull GuiRenderInfo createGuiRenderInfo() {
        return new GuiRenderInfo()
                .setButtonAnimation(DefaultAnimations.IDLE)
                .setButtonRenderTweakFunction(WitherPlayerModel::modifyGuiButtonRendering)
                .setInventoryRenderTweakFunction(WitherPlayerModel::modifyGuiInventoryRendering);
    }

    @Override
    protected @NotNull ModelAnimationController<PlayerEntity> createAnimationController() {
        return super.createAnimationController()
                .replace(1, animation -> animation.thenPlayAndHold(GeoFileDefaults.ANIMATION_RUN))
                .remove(2);
    }

    private static void modifyGuiInventoryRendering(MatrixStack matrixStack) {
        matrixStack.scale(0.8f, 0.8f, 0.8f);
        matrixStack.translate(0f, 0.2f, 0f);
    }

    private static void modifyGuiButtonRendering(MatrixStack matrixStack) {
        matrixStack.translate(0f, -0.15f, 0f);
    }
}
