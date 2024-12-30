package com.codingcat.modelshifter.client.mixin.renderer;

import com.codingcat.modelshifter.client.util.Util;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    // Prevents a crash caused by GameRenderer trying to render an arm with the replaced player renderer from modelshifter,
    // the vanilla player renderer will be used to perform the arm rendering instead
    @Redirect(
            method = "renderArmHoldingItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderDispatcher;getRenderer(Lnet/minecraft/entity/Entity;)Lnet/minecraft/client/render/entity/EntityRenderer;")
    )
    //? <1.21.3 {
    /*public <T extends Entity> EntityRenderer<?>
    *///?} else {
    @SuppressWarnings("InvalidInjectorMethodSignature")
    public <T extends Entity> EntityRenderer<?, ?>
    //?}
    redirectGetArmHoldingItemRenderer(EntityRenderDispatcher instance, T entity) {
        try {
            return Util.getNormalRendererReflect((AbstractClientPlayerEntity) entity, instance);
        } catch (ReflectiveOperationException | ClassCastException e) {
            return instance.getRenderer(entity);
        }
    }
}
