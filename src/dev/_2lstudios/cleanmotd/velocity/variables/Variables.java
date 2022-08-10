package dev._2lstudios.cleanmotd.velocity.variables;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import dev._2lstudios.cleanmotd.velocity.CleanMOTD;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

@ConfigSerializable
public class Variables {
    private static final ObjectMapper<Variables> MAPPER;

    static {
        try {
            MAPPER = ObjectMapper.forClass(Variables.class);
        } catch (ObjectMappingException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private static Variables loadFrom(ConfigurationNode node) throws ObjectMappingException {
        return MAPPER.bindToNew().populate(node);
    }

    public static Variables loadConfig(Path path, CleanMOTD plugin) throws IOException, ObjectMappingException {
        path = path.resolve("config.yml");
        if (Files.notExists(path)) {
            try (InputStream in = plugin.getClass().getResourceAsStream("config.yml")) {
                Files.copy(in, path);
            }
        }

        YAMLConfigurationLoader loader = YAMLConfigurationLoader.builder()
            .setPath(path)
            .build();

        ConfigurationNode node = loader.load();
        return Variables.loadFrom(node);
    }

    @Setting
    private Motd motd;
    public Motd motd() {
        return this.motd;
    }
    @Setting
    private Sample sample;
    public Sample sample() {
        return this.sample;
    }
    @Setting
    private Protocol protocol;
    public Protocol protocol() {
        return this.protocol;
    }
    @Setting
    private MaxPlayers maxplayers;
    public MaxPlayers maxPlayers() {
        return this.maxplayers;
    }
    @Setting
    private FakePlayers fakeplayers;
    public FakePlayers fakePlayers() {
        return this.fakeplayers;
    }

    @ConfigSerializable
    public static class Motd {
        @Setting
        private boolean enabled;
        public boolean enabled() {
            return this.enabled;
        }

        @Setting
        private List<String> motds;
        public List<String> motds() {
            return this.motds;
        }
    }

    @ConfigSerializable
    public static class Sample {
        @Setting
        private boolean enabled;
        public boolean enabled() {
            return this.enabled;
        }

        @Setting
        private List<String> samples;
        public List<String> samples() {
            return this.samples;
        }
    }

    @ConfigSerializable
    public static class Protocol {
        @Setting
        private boolean enabled;
        public boolean enabled() {
            return this.enabled;
        }

        private String name;
        public String name() {
            return this.name;
        }
    }

    @ConfigSerializable
    public static class MaxPlayers {
        @Setting
        private boolean enabled;
        public boolean enabled() {
            return this.enabled;
        }

        @Setting
        private int maxplayers;
        public int maxPlayers() {
            return this.maxplayers;
        }

        @Setting
        private boolean justonemore;
        public boolean justOneMore() {
            return this.justonemore;
        }
    }

    @ConfigSerializable
    public static class FakePlayers {
        @Setting
        private boolean enabled;
        public boolean enabled() {
            return this.enabled;
        }

        @Setting
        private int amount;
        public int amount() {
            return this.amount;
        }

        @Setting
        private Mode mode;
        public Mode mode() {
            return this.mode;
        }
    }

    public enum Mode {
        STATIC,
        RANDOM,
        DIVISION
    }
}
