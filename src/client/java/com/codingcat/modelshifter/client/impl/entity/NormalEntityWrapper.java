package com.codingcat.modelshifter.client.impl.entity;

import com.codingcat.modelshifter.client.api.entity.EntityRenderStateWrapper;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NormalEntityWrapper implements EntityRenderStateWrapper {
    private final Entity entity;

    public NormalEntityWrapper(@NotNull Entity entity) {
        this.entity = entity;
    }

    @Override
    public boolean isInSneakingPose() {
        return entity.isInSneakingPose();
    }

    @Override
    public boolean isPlayer() {
        return this.getPlayer() != null;
    }

    @Override
    public @Nullable PlayerEntity getPlayer() {
        if (!(entity instanceof AbstractClientPlayerEntity playerEntity)) return null;
        return playerEntity;
    }

    @Override
    public float getWidth() {
        return entity.getWidth();
    }

    @Override
    public float getHeight() {
        return entity.getHeight();
    }

    @Override
    @Nullable
    public Identifier getSkinTexture() {
        if (!this.isPlayer()) return null;
        assert this.getPlayer() != null;
        //? >1.20.1 {
        return ((AbstractClientPlayerEntity) getPlayer()).getSkinTextures().texture();
        //?} else {
        /*return ((AbstractClientPlayerEntity) getPlayer()).getSkinTexture();
        *///?}
    }
}
