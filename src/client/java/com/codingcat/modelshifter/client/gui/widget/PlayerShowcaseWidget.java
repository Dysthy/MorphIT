package com.codingcat.modelshifter.client.gui.widget;

import com.codingcat.modelshifter.client.ModelShifterClient;
import com.codingcat.modelshifter.client.api.model.PlayerModel;
import com.codingcat.modelshifter.client.api.renderer.feature.EnabledFeatureRenderer;
import com.codingcat.modelshifter.client.api.renderer.feature.FeatureRendererType;
import com.codingcat.modelshifter.client.api.skin.SingleAsyncSkinProvider;
import com.codingcat.modelshifter.client.impl.Models;
import com.codingcat.modelshifter.client.render.GuiPlayerEntityRenderer;
import com.codingcat.modelshifter.client.util.Util;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class PlayerShowcaseWidget extends TextWidget {
    private static final Identifier BACKGROUND = Identifier.of(ModelShifterClient.MOD_ID, "widget/preview_background");
    @Nullable
    private GuiPlayerEntityRenderer renderer;
    @Nullable
    private ModelFeaturesDrawer featuresDrawer;
    @NotNull
    private final SingleAsyncSkinProvider skinProvider;
    @NotNull
    private GameProfile gameProfile;
    //? >=1.21.3 {
    private final PlayerEntityModel playerEntityModel;
    //?} else {
    /*private final PlayerEntityModel<?> playerEntityModel;
     *///?}
    private final TextMode textMode;
    private boolean contentVisible;

    public PlayerShowcaseWidget(@NotNull GameProfile player, @NotNull SingleAsyncSkinProvider skinProvider, TextMode textMode, int x, int y, int width, int height) {
        super(x, y, width, height, Text.empty(), MinecraftClient.getInstance().textRenderer);
        this.skinProvider = skinProvider;
        this.playerEntityModel = createModel();
        this.gameProfile = player;
        this.textMode = textMode;
        this.contentVisible = true;
        this.update();
    }

    public void setContentVisible(boolean contentVisible) {
        this.contentVisible = contentVisible;
    }

    public void setPlayer(GameProfile player) {
        this.gameProfile = player;
        this.skinProvider.setProfileAndFetch(player);
        this.update();
    }

    private PlayerModel getPlayerModel() {
        return ModelShifterClient.state.getState(gameProfile.getId()).getPlayerModel();
    }

    private Text getModelName() {
        PlayerModel model = getPlayerModel();
        if (model == null)
            return Text.translatable("modelshifter.state.no_custom_model");

        String translationKey = Models.getTranslationKey(model);
        return Text.translatable(translationKey);
    }

    public void update() {
        PlayerModel model = getPlayerModel();
        if (model == null || !ModelShifterClient.state.isRendererStateEnabled(gameProfile.getId())) return;

        this.renderer = new GuiPlayerEntityRenderer(model.getModelDataIdentifier(), model.getGuiRenderInfo().getShowcaseAnimation());
        this.featuresDrawer = new ModelFeaturesDrawer(getWidth() - (getTextureX() - getX()), model);
    }

    //? <=1.20.1 {
    /*@Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderWidget(context, mouseX, mouseY, delta);
    }
    *///?}

    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        if (!this.contentVisible) return;

        this.renderModel(context);
        this.renderText(context);
    }

    private void renderBackground(DrawContext context) {
        //? <1.21.3 {
        /*context.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
         *///?}
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        Util.drawGuiTexture(context, BACKGROUND,
                this.getTextureX(),
                this.getY() + 32,
                this.getWidth(),
                this.getHeight() - 64);
    }

    private void renderText(DrawContext context) {
        float textSizeMultiplier = width / 320f;
        int rtCorner = getX() + getWidth();
        int lbCorner = getY() + getHeight();
        renderScaledText(context,
                getText(true),
                0xFFFFFF,
                rtCorner - (getWidth() / 4f),
                lbCorner - (getHeight() / 3f),
                2f * textSizeMultiplier, true);
        renderScaledText(context,
                getText(false),
                0xADADAD,
                rtCorner - (getWidth() / 4f),
                lbCorner - (getHeight() / 3f) + (24f * textSizeMultiplier),
                textSizeMultiplier, true);
        if (featuresDrawer != null && ModelShifterClient.state.isRendererEnabled(gameProfile.getId()) && textMode == TextMode.MODEL)
            featuresDrawer.draw(context, getTextureX(),
                    (int) (lbCorner - (getHeight() / 3f) + (40f * textSizeMultiplier)));
    }

    private int getTextureX() {
        return getX() + getWidth() - (getWidth() / 2) - 16;
    }

    public static void renderScaledText(DrawContext context, Text text, int color, double x, double y, float scale, boolean centered) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        context.getMatrices().push();
        context.getMatrices().translate(x, y, 0);
        context.getMatrices().scale(scale, scale, scale);
        if (centered)
            context.drawCenteredTextWithShadow(textRenderer, text, 0, 0, color);
        else
            context.drawTextWithShadow(textRenderer, text, 0, 0, color);
        context.getMatrices().pop();
    }

    private void renderModel(DrawContext context) {
        MatrixStack matrices = context.getMatrices();
        context.enableScissor(getX(), getY(), getX() + getWidth(), getY() + getHeight());
        matrices.push();
        matrices.translate(getX() + getWidth() - (getWidth() / 4f), getY() + ((float) height / 1.6), 50);
        Quaternionf quaternionf = new Quaternionf().rotateZ((float) Math.PI);
        Quaternionf quaternionf2 = new Quaternionf().rotateY((float) (Math.PI * 0.25f));
        quaternionf.mul(quaternionf2);
        matrices.multiply(quaternionf);
        float size = getHeight() / 4f;
        Pair<VertexConsumer, VertexConsumerProvider.Immediate> consumers = Util.obtainVertexConsumer(skinProvider.getSkin());
        if (ModelShifterClient.state.isRendererStateEnabled(gameProfile.getId()) && renderer != null) {
            PlayerModel model = getPlayerModel();
            assert model != null;
            Consumer<MatrixStack> tweakFunction = model.getGuiRenderInfo().getShowcaseRenderTweakFunction();
            matrices.scale(size, size, -size);
            if (tweakFunction != null)
                tweakFunction.accept(matrices);
            renderer.setRenderColor(255, 255, 255, 255);
            renderer.render(skinProvider.getSkin(), 0, 0, matrices, consumers.getRight(), LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE);
        } else if (!ModelShifterClient.state.isRendererStateEnabled(gameProfile.getId()) && playerEntityModel != null) {
            size /= 1.2f;
            matrices.scale(size, size, -size);
            matrices.translate(0, 1.4f, 0);
            matrices.multiply(new Quaternionf().rotateZ((float) Math.PI));
            int overlay = OverlayTexture.packUv(OverlayTexture.getU(0), OverlayTexture.getV(false));

            playerEntityModel.render(matrices,
                    consumers.getLeft(),
                    LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE,
                    overlay,
                    //? <1.21 {
                    /*1f, 1f, 1f, 1f
                     *///?} else {
                    -1
                    //?}
            );
        }

        context.draw();
        matrices.pop();
        context.disableScissor();
    }

    private Text getText(boolean top) {
        boolean active = ModelShifterClient.state.isRendererStateEnabled(gameProfile.getId());
        PlayerModel model = getPlayerModel();
        Text modelName = getModelName();
        String creators = (active && model != null) ? String.join(", ", model.getCreators()) : "";
        if (top)
            return (textMode == TextMode.MODEL) ? modelName
                    : Text.literal(gameProfile.getName());
        else
            return (textMode == TextMode.MODEL) ? Text.translatable(active ? "modelshifter.text.made_by" : "modelshifter.text.no_model_active", creators)
                    : Text.translatable(active ? "modelshifter.text.model_info" : "modelshifter.text.no_model_active", modelName);
    }

    //? >=1.21.3 {
    private PlayerEntityModel createModel() {
        //?} else {
        /*private PlayerEntityModel<?> createModel () {
         *///?}
        ModelData data = PlayerEntityModel.getTexturedModelData(Dilation.NONE, false);
        ModelPart root = TexturedModelData.of(data, 64, 64).createModel();
        //? >=1.21.3 {
        @SuppressWarnings("UnnecessaryLocalVariable")
        PlayerEntityModel model = new PlayerEntityModel(root, false);
        //?} else {
        /*PlayerEntityModel<?> model = new PlayerEntityModel<>(root, false);
        model.child = false;
        *///?}

        return model;
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        builder.put(NarrationPart.TITLE, getModelName());
    }

    public enum TextMode {
        MODEL,
        PLAYER
    }

    public static class ModelFeaturesDrawer {
        private static final Identifier CARD_COLORED = Identifier.of(ModelShifterClient.MOD_ID, "widget/card_colored.png");
        private static final int CARD_HEIGHT = 15;
        private static final int CARD_SPACING_X = 6;
        private static final int CARD_SPACING_Y = 8;
        private static final int CARD_TEXT_SPACING = 5;
        private final TextRenderer renderer;
        @NotNull
        private final List<FeatureRendererType> types;
        private final int maxWidth;

        public ModelFeaturesDrawer(int maxWidth, @NotNull PlayerModel model) {
            this.types = model.getFeatureRendererStates()
                    .getEnabledRenderers().stream()
                    .map(EnabledFeatureRenderer::type)
                    .sorted(Comparator.comparingInt(Enum::ordinal)).toList();
            this.maxWidth = maxWidth - (CARD_SPACING_X * 4);
            this.renderer = MinecraftClient.getInstance().textRenderer;
        }

        public void draw(DrawContext context, int posX, int posY) {
            int x = posX + (CARD_SPACING_X * 2);
            int y = posY;
            for (FeatureRendererType type : types) {
                if ((x + getCardWidth(type) + CARD_SPACING_X) > (posX + maxWidth)) {
                    x = posX + (CARD_SPACING_X * 2);
                    y += CARD_HEIGHT + CARD_SPACING_Y;
                }

                x += drawCard(context, x, y, type);
                x += CARD_SPACING_X;
            }
        }

        private int drawCard(DrawContext context, int x, int y, FeatureRendererType type) {
            Text text = getFeatureText(type);
            int w = getCardWidth(type);
            Util.drawGuiTexture(context, CARD_COLORED, x, y, w, CARD_HEIGHT);
            context.drawText(renderer, text, x + CARD_TEXT_SPACING, y + (CARD_HEIGHT / 4), 0xFFFFFF, true);
            return w;
        }

        private int getCardWidth(FeatureRendererType type) {
            return renderer.getWidth(getFeatureText(type)) + (CARD_TEXT_SPACING * 2);
        }

        private Text getFeatureText(FeatureRendererType type) {
            return Text.translatable(type.getTranslationKey());
        }
    }
}