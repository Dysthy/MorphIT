package com.codingcat.modelshifter.client.impl.entity;

import com.codingcat.modelshifter.client.api.entity.EntityRenderStateWrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

public class StateEntityWrapper implements EntityRenderStateWrapper {
    private final EntityRenderState state;

    public StateEntityWrapper(EntityRenderState state) {
        this.state = state;
    }

    @Override
    public boolean isInSneakingPose() {
        return state.sneaking;
    }

    @Override
    public boolean isPlayer() {
        return this.getPlayer() != null;
    }

    @Override
    public @Nullable PlayerEntity getPlayer() {
        ClientWorld world = MinecraftClient.getInstance().world;
        if (!(state instanceof PlayerEntityRenderState playerState) || world == null) return null;
        if (!(world.getEntityById(playerState.id) instanceof AbstractClientPlayerEntity playerEntity)) return null;

        return playerEntity;
    }

    @Override
    public float getWidth() {
        return state.width;
    }

    @Override
    public float getHeight() {
        return state.height;
    }
}
