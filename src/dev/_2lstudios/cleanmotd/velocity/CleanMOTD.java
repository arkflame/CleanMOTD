package dev._2lstudios.cleanmotd.velocity;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;

import dev._2lstudios.cleanmotd.velocity.commands.CleanMOTDCommand;
import dev._2lstudios.cleanmotd.velocity.listeners.ProxyPingListener;
import dev._2lstudios.cleanmotd.velocity.variables.Messages;
import dev._2lstudios.cleanmotd.velocity.variables.Variables;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

@Plugin(
    id = "cleanmotd",
    name = "CleanMOTD",
    version = "0.2.7",
    description = "Simple MOTD managing plugin",
    authors = ("2LS")
)
public final class CleanMOTD {
    private final Path path;
    private final CommandManager commandManager;
    private final EventManager eventManager;
    private Variables variables;
    private Messages messages;

    @Inject
    public CleanMOTD(
        @DataDirectory Path path,
        CommandManager commandManager,
        EventManager eventManager
    ) {
        this.path = path;
        this.commandManager = commandManager;
        this.eventManager = eventManager;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        if (Files.notExists(path)) {
            try {
                Files.createDirectory(path);
            } catch(IOException e) {
                e.printStackTrace();
            }
            
        }
        if (!reload()) {
            return;
        }

        CommandMeta meta = commandManager.metaBuilder("cleanmotd")
            .plugin(this)
            .build();

        commandManager.register(meta, new CleanMOTDCommand(this));
        eventManager.register(this, new ProxyPingListener(this));
    }

    public Variables getVariables() {
        return variables;
    }

    public Messages getMessages() {
        return messages;
    }
    
    public boolean reload() {
        Variables vars;
        try {
            vars = Variables.loadConfig(path, this);
        } catch (IOException | ObjectMappingException e) {
            e.printStackTrace();
            return false;
        }

        Messages msg;
        try {
            msg = Messages.loadConfig(path, this);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (msg != null) {
            this.messages = msg;
        }

        if (vars != null) {
            this.variables = vars;
        }

        return vars != null && msg != null;
    }
}
