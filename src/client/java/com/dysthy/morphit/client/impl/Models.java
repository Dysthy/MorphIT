package com.dysthy.morphit.client.impl;

import com.dysthy.morphit.client.ModelShifterClient;
import com.dysthy.morphit.client.api.model.PlayerModel;
import com.dysthy.morphit.client.api.registry.ModelRegistry;
import com.codingcat.modelshifter.client.impl.model.*;
import com.dysthy.morphit.client.impl.model.ExternalPlayerModel;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Models {
    public static void registerAll() {
        register(0, "Template", new ExternalPlayerModel());
    }

    private static void register(int ordinal, String id, PlayerModel playerModel) {
        ModelRegistry.register(ordinal, Identifier.of(ModelShifterClient.MOD_ID, id), playerModel);
    }

    @Nullable
    public static String getTranslationKey(PlayerModel model) {
        Optional<Identifier> id = ModelRegistry.findId(model);
        return id.map(Models::getTranslationKey).orElse(null);
    }

    @NotNull
    public static String getTranslationKey(Identifier id) {
        return String.format("modelshifter.model.%s", id.getPath());
    }
}
