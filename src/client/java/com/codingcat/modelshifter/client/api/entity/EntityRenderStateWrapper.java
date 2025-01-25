package com.codingcat.modelshifter.client.api.entity;

import com.codingcat.modelshifter.client.impl.entity.NormalEntityWrapper;
//? >=1.21.3 {
import net.minecraft.client.render.entity.state.EntityRenderState;
import com.codingcat.modelshifter.client.impl.entity.StateEntityWrapper;
//?}
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public interface EntityRenderStateWrapper {
    boolean isInSneakingPose();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isPlayer();

    @Nullable
    PlayerEntity getPlayer();

    float getWidth();

    float getHeight();

    float leaningPitch();

    float pitch();

    boolean isGliding();

    boolean usingRiptide();

    boolean touchingWater();

    boolean isSwimming();

    boolean applyFlyingRotation();

    float flyingRotation();

    float getGlidingProgress();

    Identifier getSkinTexture();

    static EntityRenderStateWrapper of(Entity entity) {
        return new NormalEntityWrapper(entity, -1);
    }

    static EntityRenderStateWrapper of(Entity entity, float tickDelta) {
        return new NormalEntityWrapper(entity, tickDelta);
    }

    //? >=1.21.3 {
    static EntityRenderStateWrapper of(EntityRenderState state) {
        return new StateEntityWrapper(state);
    }
    //?}
}
