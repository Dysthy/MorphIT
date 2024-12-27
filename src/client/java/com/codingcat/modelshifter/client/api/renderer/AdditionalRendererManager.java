package com.codingcat.modelshifter.client.api.renderer;

import com.codingcat.modelshifter.client.api.model.PlayerModel;
import com.codingcat.modelshifter.client.api.registry.ModelRegistry;
import com.codingcat.modelshifter.client.render.ReplacedPlayerEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import static com.codingcat.modelshifter.client.ModelShifterClient.LOGGER;

public class AdditionalRendererManager {
    @NotNull
    private final Map<Identifier, ReplacedPlayerEntityRenderer> additionalRenderers;

    public AdditionalRendererManager() {
        this.additionalRenderers = new HashMap<>();
    }

    public void reload(EntityRendererFactory.Context context) {
        this.additionalRenderers.clear();
        for (Map.Entry<Pair<Integer, Identifier>, PlayerModel> model : ModelRegistry.entries()) {
            try {
                ReplacedPlayerEntityRenderer renderer = new ReplacedPlayerEntityRenderer(context, model.getValue());
                this.additionalRenderers.put(model.getKey().getValue(), renderer);
            } catch (Exception e) {
                LOGGER.error("Failed to create renderer for %s: ".formatted(model.getKey().getValue()), e);
            }
        }

        LOGGER.info("Created {} additional renderers for ModelShifter!", rendererCount());
    }

    @Nullable
    public ReplacedPlayerEntityRenderer getRenderer(PlayerModel model) {
        if (model == null) return null;
        return this.getRenderer(ModelRegistry.findId(model).orElse(null));
    }

    @Nullable
    public ReplacedPlayerEntityRenderer getRenderer(Identifier modelIdentifier) {
        if (modelIdentifier == null) return null;
        return additionalRenderers.get(modelIdentifier);
    }

    public int rendererCount() {
        return this.additionalRenderers.size();
    }
}
