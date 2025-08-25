package com.dysthy.morphit.client.api.renderer;

import com.dysthy.morphit.client.api.model.PlayerModel;
import com.dysthy.morphit.client.impl.option.ModeOption;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("UnusedReturnValue")
public class PlayerDependentStateHolder {
    @NotNull
    private ModeOption displayMode;
    @NotNull
    private final AdditionalRendererState globalState;
    private HashMap<UUID, AdditionalRendererState> stateOverrideMap;

    public PlayerDependentStateHolder(@NotNull AdditionalRendererState globalState, Set<ConfigPlayerOverride> overrides, @NotNull ModeOption displayMode) {
        this.reloadFromOverrides(overrides);
        this.globalState = globalState;
        this.displayMode = displayMode;
    }

    public PlayerDependentStateHolder reloadFromOverrides(Set<ConfigPlayerOverride> overrides) {
        this.stateOverrideMap = new HashMap<>(overrides.stream()
                .collect(Collectors.toMap(ConfigPlayerOverride::player, ConfigPlayerOverride::state)));
        return this;
    }

    public Set<ConfigPlayerOverride> generateOverrides() {
        return stateOverrideMap.entrySet()
                .stream()
                .map(entry -> new ConfigPlayerOverride(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());
    }

    public void writeConfig() {
        new ConfigurationLoader().write(new Configuration()
                .setGlobalState(this.getGlobalState())
                .setPlayerOverrides(this.generateOverrides())
                .setDisplayMode(this.getDisplayMode().getId()));
    }

    public PlayerDependentStateHolder setDisplayMode(@NotNull ModeOption displayMode) {
        this.displayMode = displayMode;
        return this;
    }

    public @NotNull ModeOption getDisplayMode() {
        return displayMode;
    }

    public PlayerDependentStateHolder setGlobalState(boolean rendererEnabled, @Nullable PlayerModel model) {
        this.globalState.setState(rendererEnabled, model);
        return this;
    }

    public PlayerDependentStateHolder setPlayerState(UUID uuid, boolean rendererEnabled, @Nullable PlayerModel model) {
        AdditionalRendererState state = stateOverrideMap.get(uuid);
        if (hasUniqueState(uuid))
            state.setState(rendererEnabled, model);

        this.stateOverrideMap.put(uuid, hasUniqueState(uuid) ? state : new AdditionalRendererState(rendererEnabled, model));
        return this;
    }

    public boolean isRendererStateEnabled(UUID uuid) {
        return getState(uuid).isRendererEnabled();
    }

    public boolean isRendererEnabled(UUID uuid) {
        if (!displayMode.test(uuid)) return false;
        return isRendererStateEnabled(uuid);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isRendererEnabled(PlayerEntity entity) {
        return isRendererEnabled(entity.getUuid());
    }

    @NotNull
    public AdditionalRendererState getGlobalState() {
        return this.globalState;
    }

    public boolean hasUniqueState(UUID uuid) {
        return this.stateOverrideMap.get(uuid) != null;
    }

    public AdditionalRendererState getState(UUID uuid) {
        if (hasUniqueState(uuid))
            return stateOverrideMap.get(uuid);

        return this.globalState;
    }

    public int getStateCount() {
        return stateOverrideMap.size();
    }

    public int getActiveStateCount() {
        return (int) stateOverrideMap.values()
                .stream()
                .filter(AdditionalRendererState::isRendererEnabled)
                .count();
    }
}