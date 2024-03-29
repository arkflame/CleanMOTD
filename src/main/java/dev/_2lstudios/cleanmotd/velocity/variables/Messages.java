package dev._2lstudios.cleanmotd.velocity.variables;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import dev._2lstudios.cleanmotd.velocity.CleanMOTD;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

public interface Messages {
    String usage();
    String reload();
    String unknownCommand();

    public static Messages loadConfig(Path path, CleanMOTD plugin) throws IOException {
        path = path.resolve("messages.yml");
        if (Files.notExists(path)) {
            try (InputStream in = plugin.getClass().getClassLoader().getResourceAsStream("messages.yml")) {
                Files.copy(in, path);
            }
        }

        final YAMLConfigurationLoader loader = YAMLConfigurationLoader.builder()
            .setPath(path)
            .build();

        // why linsa? messages.getString("messages.usage")? algo... redundante xd
        final ConfigurationNode node = loader.load().getNode("messages");
        final String usage = node.getNode("usage").getString();
        final String reload = node.getNode("reload").getString();
        final String unknownCommand = node.getNode("unknowncommand").getString();

        return new Messages() {
            @Override
            public String usage() {
                return usage;
            }

            @Override
            public String reload() {
                return reload;
            }

            @Override
            public String unknownCommand() {
                return unknownCommand;
            }
        };
    }
}
