package com.codingcat.modelshifter.client.gui.widget;

import com.codingcat.modelshifter.client.ModelShifterClient;
import com.codingcat.modelshifter.client.api.model.PlayerModel;
import com.codingcat.modelshifter.client.api.skin.SingleAsyncSkinProvider;
import com.codingcat.modelshifter.client.impl.Models;
import com.codingcat.modelshifter.client.render.GuiPlayerEntityRenderer;
import com.codingcat.modelshifter.client.util.Util;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModelPreviewButtonWidget extends PressableWidget {
    private static final Identifier BUTTON_UNSELECTED = id("model_button");
    private static final Identifier BUTTON_SELECTED = id("model_button_active");
    private static final Identifier BUTTON_DISABLE_UNSELECTED = id("model_disable_button");
    private static final Identifier BUTTON_DISABLE_SELECTED = id("model_disable_button_active");
    @Nullable
    private final PlayerModel model;
    @Nullable
    private GuiPlayerEntityRenderer renderer;
    private final SingleAsyncSkinProvider skinProvider;
    private final Consumer<ModelPreviewButtonWidget> onPressConsumer;
    private final Supplier<Boolean> isVisibleSupplier;
    private boolean selected;
    private final ModelPreviewButtonWidget.Type type;

    public ModelPreviewButtonWidget(int x, int y, int size, ModelPreviewButtonWidget.Type type, @Nullable PlayerModel model, SingleAsyncSkinProvider skinProvider, Consumer<ModelPreviewButtonWidget> onPress, Supplier<Boolean> isVisible) {
        super(x, y, size, size, Text.empty());
        this.type = type;
        this.model = model;
        if (model != null)
            this.renderer = new GuiPlayerEntityRenderer(model.getModelDataIdentifier(), model.getGuiRenderInfo().getButtonAnimation());
        this.skinProvider = skinProvider;
        this.onPressConsumer = onPress;
        this.isVisibleSupplier = isVisible;
        this.selected = false;
        this.setTooltip(Tooltip.of(getModelName()));
    }

    private static Identifier id(String id) {
        return Identifier.of(ModelShifterClient.MOD_ID, String.format("widget/%s", id));
    }

    private Text getModelName() {
        if (model == null)
            return Text.translatable("modelshifter.button.disable");
        String translationKey = Models.getTranslationKey(model);
        return Text.translatable(translationKey);
    }

    @Nullable
    public PlayerModel getModel() {
        return this.model;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isButtonSelected() {
        return this.selected;
    }

    public void updateVisibility() {
        boolean visible = isVisibleSupplier.get();
        this.active = visible;
        this.setTooltip(visible ? Tooltip.of(getModelName()) : null);
    }

    @Override
    public void onPress() {
        if (!this.isVisibleSupplier.get()) return;

        this.onPressConsumer.accept(this);
    }

    @Override
    public void playDownSound(SoundManager soundManager) {
        if (!this.isVisibleSupplier.get()) return;

        super.playDownSound(soundManager);
    }

    //? <=1.20.1 {
    /*@Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderWidget(context, mouseX, mouseY, delta);
    }
    *///?}

    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        if (!this.isVisibleSupplier.get()) return;

        this.renderBackground(context);
        if (this.model != null) {
            this.renderModel(context);
            this.renderText(context);
        }
    }

    private void renderBackground(DrawContext context) {
        //? <1.21.3 {
        /*context.setShaderColor(1.0f, 1.0f, 1.0f, this.alpha);
        *///?}
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        Util.drawGuiTexture(context, this.type == Type.DISABLE_BUTTON ?
                        (this.selected ? BUTTON_DISABLE_SELECTED : BUTTON_DISABLE_UNSELECTED) :
                        (this.selected ? BUTTON_SELECTED : BUTTON_UNSELECTED),
                this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    private void renderText(DrawContext context) {
        PlayerShowcaseWidget.renderScaledText(context,
                getModelName(),
                this.selected ? 0xFFFFFF : 0x8F8F8F,
                getX() + 5,
                getY() + getHeight() - 11,
                0.7f, false);
    }

    private void renderModel(DrawContext context) {
        if (model == null || renderer == null) return;

        MatrixStack matrices = context.getMatrices();
        context.enableScissor(getX() + 2, getY() + 2, (getX() + getWidth()) - 2, (getY() + getHeight()) - 2);
        matrices.push();
        matrices.translate(getX() + (getWidth() / 2f), getY() + (getHeight() / 1.3f), 50);
        Quaternionf quaternionf = new Quaternionf().rotateZ((float) Math.PI);
        Quaternionf quaternionf2 = new Quaternionf().rotateY((float) (Math.PI * ((double) System.currentTimeMillis() / 3500f) % 360f));
        quaternionf.mul(quaternionf2);
        matrices.multiply(quaternionf);
        float size = getHeight() / 2.5f;
        matrices.scale(size, size, -size);
        int color = this.selected ? 255 : 175;
        Consumer<MatrixStack> tweakFunction = model.getGuiRenderInfo().getButtonRenderTweakFunction();
        if (tweakFunction != null)
            tweakFunction.accept(matrices);
        renderer.setRenderColor(color, color, color, color);
        renderer.render(skinProvider.getSkin(), 0, 0, matrices, Util.obtainVertexConsumer(skinProvider.getSkin()).getRight(), LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE);
        context.draw();
        matrices.pop();
        context.disableScissor();
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        builder.put(NarrationPart.TITLE, getModelName());
    }

    public enum Type {
        NORMAL,
        DISABLE_BUTTON
    }
}
