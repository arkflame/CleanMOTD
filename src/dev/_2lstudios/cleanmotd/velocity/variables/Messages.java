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
            try (InputStream in = plugin.getClass().getResourceAsStream("messages.yml")) {
                Files.copy(in, path);
            }
        }

        YAMLConfigurationLoader loader = YAMLConfigurationLoader.builder()
            .setPath(path)
            .build();

        // why linsa? messages.getString("messages.usage")? algo... redundante xd
        ConfigurationNode node = loader.load().getNode("messages");
        String usage = node.getNode("usage").getString();
        String reload = node.getNode("reload").getString();
        String unknownCommand = node.getNode("unknowncommand").getString();

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
