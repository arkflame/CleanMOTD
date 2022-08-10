package dev._2lstudios.cleanmotd.velocity.utils;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class Components {
    public static final LegacyComponentSerializer SERIALIZER = LegacyComponentSerializer.builder()
        .hexColors().build();
}
