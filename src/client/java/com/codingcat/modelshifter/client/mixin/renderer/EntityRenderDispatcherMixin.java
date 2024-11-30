package com.codingcat.modelshifter.client.mixin.renderer;

import com.codingcat.modelshifter.client.ModelShifterClient;
import com.codingcat.modelshifter.client.api.entity.EntityRenderStateWrapper;
import com.codingcat.modelshifter.client.api.model.ModelDimensions;
import com.codingcat.modelshifter.client.api.model.PlayerModel;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
//? >=1.21.3 {
import net.minecraft.client.render.entity.state.EntityRenderState;
//?}
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
    @Redirect(
            method = "renderFire",
            //? >=1.21.3 {
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/state/EntityRenderState;width:F")
    )
    public float redirectGetWidth(EntityRenderState e) {
        //?} else {
        /*at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getWidth()F")
    )
    public float redirectGetWidth(Entity e) {
    *///?}
        EntityRenderStateWrapper state = EntityRenderStateWrapper.of(e);
        ModelDimensions dimensions = getDimensions(state);
        return dimensions != null ? dimensions.width() : state.getWidth();
    }

    @Redirect(
            method = "renderFire",
            //? >=1.21.3 {
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/state/EntityRenderState;height:F")
    )
    public float redirectGetHeight(EntityRenderState e) {
        //?} else {
        /*at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getHeight()F")
    )
    public float redirectGetHeight(Entity e) {
    *///?}
        EntityRenderStateWrapper state = EntityRenderStateWrapper.of(e);
        ModelDimensions dimensions = getDimensions(state);
        return dimensions != null ? dimensions.height() : state.getHeight();
    }

    @Redirect(
            method = "renderHitbox",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getBoundingBox()Lnet/minecraft/util/math/Box;")
    )
    private static Box redirectGetBox(Entity entity) {
        if (!ModelShifterClient.isDev) return entity.getBoundingBox();
        if (!(entity instanceof AbstractClientPlayerEntity player)) return entity.getBoundingBox();
        ModelDimensions dimensions = getDimensions(player);
        if (dimensions == null) return entity.getBoundingBox();

        return getBox(dimensions.width(), dimensions.height(), entity.getX(), entity.getY(), entity.getZ());
    }


    @Unique
    private static Box getBox(float w, float h, double x, double y, double z) {
        float f = w / 2.0f;
        return new Box(x - (double) f, y, z - (double) f, x + (double) f, y + (double) h, z + (double) f);
    }

    @Unique
    @Nullable
    private static ModelDimensions getDimensions(EntityRenderStateWrapper state) {
        if (!state.isPlayer()) return null;
        assert state.getPlayer() != null;
        return getDimensions(state.getPlayer());
    }

    @Unique
    @Nullable
    private static ModelDimensions getDimensions(PlayerEntity player) {
        if (!ModelShifterClient.state.isRendererEnabled(player)) return null;
        PlayerModel model = ModelShifterClient.state.getState(player.getUuid()).getPlayerModel();

        if (model == null) return null;
        return model.getDimensions();
    }
}
