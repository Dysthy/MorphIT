package com.dysthy.morphit.client.api.renderer.feature;

import com.dysthy.morphit.client.api.entity.EntityRenderStateWrapper;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class FeatureRendererStates {
    private final Set<EnabledFeatureRenderer> enabledRenderers;
    private Consumer<MatrixStack> sillyHatRenderModifier;

    public FeatureRendererStates() {
        this.enabledRenderers = new HashSet<>();
    }

    public FeatureRendererStates add(@NotNull FeatureRendererType type, @Nullable BiConsumer<EntityRenderStateWrapper, MatrixStack> renderModifier) {
        this.enabledRenderers.add(new EnabledFeatureRenderer(type, renderModifier));
        return this;
    }

    public FeatureRendererStates add(@NotNull FeatureRendererType type) {
        return this.add(type, null);
    }

    public FeatureRendererStates setSillyHatRenderModifier(@Nullable Consumer<MatrixStack> sillyHatRenderModifier) {
        this.sillyHatRenderModifier = sillyHatRenderModifier;
        return this;
    }

    public Set<EnabledFeatureRenderer> getEnabledRenderers() {
        return this.enabledRenderers;
    }

    public void applySillyHatRenderModifier(MatrixStack poseStack) {
        if (this.sillyHatRenderModifier != null)
            this.sillyHatRenderModifier.accept(poseStack);
    }
}
