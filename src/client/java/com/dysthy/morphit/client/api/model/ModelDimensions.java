package com.dysthy.morphit.client.api.model;

public record ModelDimensions(
        float width,
        float height,
        float labelOffset,
        boolean isStandingModel
) {
    public ModelDimensions(float width, float height, boolean isStandingModel) {
        this(width, height, 0f, isStandingModel);
    }
}
