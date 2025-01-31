package com.codingcat.modelshifter.client.impl.entity;

import com.codingcat.modelshifter.client.api.entity.EntityRenderStateWrapper;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NormalEntityWrapper implements EntityRenderStateWrapper {
    private final Entity entity;
    private final float tickDelta;

    public NormalEntityWrapper(@NotNull Entity entity, float tickDelta) {
        this.entity = entity;
        this.tickDelta = tickDelta;
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

    private AbstractClientPlayerEntity verifyPlayer() {
        if (getPlayer() == null)
            throw new IllegalArgumentException("Player-dependent method called on wrapper not containing a player");

        return (AbstractClientPlayerEntity) getPlayer();
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
    public float leaningPitch() {
        return verifyPlayer().getLeaningPitch(tickDelta);
    }

    @Override
    public float pitch() {
        return verifyPlayer().getPitch(tickDelta);
    }

    @Override
    public boolean isGliding() {
        //? >1.21.1 {
        return verifyPlayer().isGliding();
        //?} else {
        /*return verifyPlayer().isFallFlying();
        *///?}
    }

    @Override
    public boolean usingRiptide() {
        return verifyPlayer().isUsingRiptide();
    }

    @Override
    public boolean touchingWater() {
        return verifyPlayer().isTouchingWater();
    }

    @Override
    public boolean isSwimming() {
        return verifyPlayer().isSwimming();
    }

    @Override
    public boolean applyFlyingRotation() {
        AbstractClientPlayerEntity player = verifyPlayer();
        return player.getRotationVec(tickDelta).horizontalLengthSquared() > 0.0 && player.lerpVelocity(tickDelta).horizontalLengthSquared() > 0.0;
    }

    @Override
    public float flyingRotation() {
        AbstractClientPlayerEntity player = verifyPlayer();
        Vec3d rotVec = player.getRotationVec(tickDelta);
        Vec3d lerpVel = player.lerpVelocity(tickDelta);
        double l = (lerpVel.x * rotVec.x + lerpVel.z * rotVec.z) / Math.sqrt(lerpVel.horizontalLengthSquared() * rotVec.horizontalLengthSquared());
        double m = lerpVel.x * rotVec.z - lerpVel.z * rotVec.x;
        return (float) (Math.signum(m) * Math.acos(l));
    }

    @Override
    public float getGlidingProgress() {
        //? >1.21.1 {
        float glidingTicks = verifyPlayer().getGlidingTicks() + tickDelta;
        //?} else if >1.20.4 {
        /*float glidingTicks = verifyPlayer().getFallFlyingTicks() + tickDelta;
        *///?} else {
        /*float glidingTicks = verifyPlayer().getRoll() + tickDelta;
        *///?}
        return MathHelper.clamp(glidingTicks * glidingTicks / 100.0f, 0.0f, 1.0f);
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
