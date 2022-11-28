package dev._2lstudios.cleanmotd.velocity.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.velocitypowered.api.command.*;

import dev._2lstudios.cleanmotd.velocity.CleanMOTD;
import dev._2lstudios.cleanmotd.velocity.utils.Components;

public final class CleanMOTDCommand {

    public static void register(CleanMOTD plugin, CommandManager commandManager) {
        var command = new BrigadierCommand(LiteralArgumentBuilder.<CommandSource>literal("cleanmotd")
                .requires(src -> src.hasPermission("cleanmotd.admin"))
                .executes(cmd -> {
                    cmd.getSource().sendMessage(Components.SERIALIZER.deserialize(plugin.getMessages().usage()));
                    return Command.SINGLE_SUCCESS;
                })
                .then(LiteralArgumentBuilder.<CommandSource>literal("reload")
                        .executes(cmd -> {
                            plugin.reload();
                            cmd.getSource().sendMessage(Components.SERIALIZER.deserialize(plugin.getMessages().reload()));
                            return Command.SINGLE_SUCCESS;
                        }))
                .then(LiteralArgumentBuilder.<CommandSource>literal("help")
                        .executes(cmd -> {
                            cmd.getSource().sendMessage(Components.SERIALIZER.deserialize(plugin.getMessages().usage()));
                            return Command.SINGLE_SUCCESS;
                        }))
                .build());

        final CommandMeta meta = commandManager.metaBuilder(command)
                .plugin(plugin)
                .build();
        commandManager.register(meta, command);
    }
}
