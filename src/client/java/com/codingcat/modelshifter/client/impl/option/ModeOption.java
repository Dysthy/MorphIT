package com.codingcat.modelshifter.client.impl.option;

import com.codingcat.modelshifter.client.util.Util;
import com.google.common.base.Predicates;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public enum ModeOption {
    ALL("all_players", Predicates.alwaysTrue()),
    ONLY_ME("only_self", ModeOption::isSelf),
    ONLY_OTHERS("only_other_players", Predicate.not(ModeOption::isSelf));

    public static final List<ModeOption> OPTIONS = Arrays.stream(values()).toList();
    private final Text displayName;
    private final Predicate<UUID> predicate;
    @NotNull
    private final String id;

    ModeOption(@NotNull String id, Predicate<UUID> predicate) {
        this.displayName = Text.translatable(String.format("modelshifter.option.mode.%s", id));
        this.id = id;
        this.predicate = predicate;
    }

    public boolean test(UUID uuid) {
        return this.predicate.test(uuid);
    }

    @Nullable
    public static ModeOption byId(@NotNull String id) {
        return Arrays.stream(values())
                .filter(modeOption -> modeOption.getId().equals(id))
                .findFirst().orElse(null);
    }

    public Text getDisplayName() {
        return displayName;
    }

    @NotNull
    public String getId() {
        return id;
    }

    private static boolean isSelf(UUID uuid) {
        return Util.getGameProfile().getId().equals(uuid);
    }
}
