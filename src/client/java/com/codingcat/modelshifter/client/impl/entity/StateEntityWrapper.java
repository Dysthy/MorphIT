package com.codingcat.modelshifter.client.impl.entity;

//? >=1.21.3 {
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import com.codingcat.modelshifter.client.api.entity.EntityRenderStateWrapper;
//?}

//? <1.21.3 {
/*@SuppressWarnings("unused")
*///?}
public class StateEntityWrapper
    //? >=1.21.3 {
        implements EntityRenderStateWrapper
    //?}
{
    //? >=1.21.3 {
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

    @Override
    public float leaningPitch() {
        return verifyPlayerState().leaningPitch;
    }

    @Override
    public float pitch() {
        return verifyPlayerState().pitch;
    }

    @Override
    public boolean isGliding() {
        return verifyPlayerState().isGliding;
    }

    @Override
    public boolean usingRiptide() {
        return verifyPlayerState().usingRiptide;
    }

    @Override
    public boolean touchingWater() {
        return verifyPlayerState().touchingWater;
    }

    @Override
    public boolean isSwimming() {
        return verifyPlayerState().isSwimming;
    }

    @Override
    public boolean applyFlyingRotation() {
        return verifyPlayerState().applyFlyingRotation;
    }

    @Override
    public float flyingRotation() {
        return verifyPlayerState().flyingRotation;
    }

    @Override
    public float getGlidingProgress() {
        return verifyPlayerState().getGlidingProgress();
    }

    private PlayerEntityRenderState verifyPlayerState() {
        if (!(this.state instanceof PlayerEntityRenderState playerState))
            throw new IllegalArgumentException("Player state method called on wrapper of non-player state");
        return playerState;
    }

    @Override
    @Nullable
    public Identifier getSkinTexture() {
        if (!(state instanceof PlayerEntityRenderState playerState)) return null;
        return playerState.skinTextures.texture();
    }
    //?}
}
