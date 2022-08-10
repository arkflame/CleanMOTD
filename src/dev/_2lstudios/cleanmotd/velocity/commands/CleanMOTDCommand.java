package dev._2lstudios.cleanmotd.velocity.commands;

import com.velocitypowered.api.command.SimpleCommand;

import dev._2lstudios.cleanmotd.velocity.CleanMOTD;
import dev._2lstudios.cleanmotd.velocity.utils.Components;
import net.kyori.adventure.text.Component;

public class CleanMOTDCommand implements SimpleCommand {
    private final CleanMOTD plugin;

    public CleanMOTDCommand(CleanMOTD plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        final String[] args = invocation.arguments();
        if (args.length < 1 || args[0].equalsIgnoreCase("help")) {
            invocation.source().sendMessage(Components.SERIALIZER.deserialize(plugin.getMessages().usage()));
        } else if (args[0].equalsIgnoreCase("reload")) {
            plugin.reload();
            invocation.source().sendMessage(Components.SERIALIZER.deserialize(plugin.getMessages().reload()));
        } else {
            invocation.source().sendMessage(Components.SERIALIZER.deserialize(plugin.getMessages().unknownCommand()));
        } 
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("cleanmotd.admin");
    }
    
}
