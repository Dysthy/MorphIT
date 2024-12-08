package com.codingcat.modelshifter.client.api.renderer.feature;

import net.minecraft.client.render.entity.feature.*;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public enum FeatureRendererType {
    HELD_ITEM(HeldItemFeatureRenderer.class),
    ARMOR(ArmorFeatureRenderer.class),
    ELYTRA(ElytraFeatureRenderer.class),
    CAPE(CapeFeatureRenderer.class),
    DEADMAU5_EARS(Deadmau5FeatureRenderer.class),
    STUCK_ARROWS(StuckArrowsFeatureRenderer.class),
    STUCK_STINGERS(StuckStingersFeatureRenderer.class),
    SHOULDER_PARROT(ShoulderParrotFeatureRenderer.class),
    TRIDENT_RIPTIDE(TridentRiptideFeatureRenderer.class);

    private final Class<?> rendererClass;

    FeatureRendererType(Class<?> rendererClass) {
        this.rendererClass = rendererClass;
    }

    public Class<?> getRendererClass() {
        return rendererClass;
    }

    @Nullable
    public static FeatureRendererType findByClass(Class<?> rendererClass) {
        return Arrays.stream(values())
                .filter(type -> type.getRendererClass().isAssignableFrom(rendererClass))
                .findFirst().orElse(null);
    }
}
