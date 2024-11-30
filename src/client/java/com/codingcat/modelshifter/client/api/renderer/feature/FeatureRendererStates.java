package com.codingcat.modelshifter.client.api.renderer.feature;

import com.codingcat.modelshifter.client.api.entity.EntityRenderStateWrapper;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class FeatureRendererStates {
    private final Set<EnabledFeatureRenderer> enabledRenderers;

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

    @Nullable
    public EnabledFeatureRenderer getFeatureRenderer(FeatureRendererType type) {
        return this.enabledRenderers.stream()
                .filter(renderer -> renderer.type().equals(type))
                .findFirst()
                .orElse(null);
    }

    public boolean isRendererEnabled(FeatureRendererType type) {
        return getFeatureRenderer(type) != null;
    }

    public void modifyRendering(FeatureRendererType type, EntityRenderStateWrapper renderState, MatrixStack matrixStack) {
        EnabledFeatureRenderer renderer = getFeatureRenderer(type);
        if (renderer == null || renderer.renderModifierConsumer() == null) return;

        renderer.renderModifierConsumer().accept(renderState, matrixStack);
    }
}
