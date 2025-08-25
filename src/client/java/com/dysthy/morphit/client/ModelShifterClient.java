package com.dysthy.morphit.client;

import com.dysthy.morphit.client.api.renderer.AdditionalRendererManager;
import com.dysthy.morphit.client.api.renderer.PlayerDependentStateHolder;
import com.dysthy.morphit.client.impl.Models;
import com.dysthy.morphit.client.impl.option.ModeOption;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

public class ModelShifterClient implements ClientModInitializer {
    public static final String MOD_ID = "modelshifter";
    public static final Logger LOGGER = LoggerFactory.getLogger("ModelShifter");
    public static final Identifier EMPTY_TEXTURE = Identifier.of(MOD_ID, "empty");
    public static PlayerDependentStateHolder state;
    public static AdditionalRendererManager rendererManager = new AdditionalRendererManager();
    public static boolean isDev;

    @Override
    public void onInitializeClient() {
        ModelShifterClient.isDev = FabricLoader.getInstance().isDevelopmentEnvironment();
        Models.registerAll();
        this.loadConfig();
        this.registerCommands();
    }

    private void registerCommands() {
        ClientCommands commands = new ClientCommands();
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                commands.registerAll(dispatcher));
    }

    private void loadConfig() {
        ConfigurationLoader loader = new ConfigurationLoader();
        Configuration config = loader.load(Configuration.class);

        ModeOption displayMode = ModeOption.byId(config.getDisplayMode());
        state = new PlayerDependentStateHolder(config.getGlobalState(), new HashSet<>(config.getPlayerOverrides()), displayMode != null ? displayMode : ModeOption.ALL);
        loader.write(config);
    }
}
