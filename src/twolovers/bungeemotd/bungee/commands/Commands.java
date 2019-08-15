package twolovers.bungeemotd.bungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import twolovers.bungeemotd.bungee.variables.Messages;
import twolovers.bungeemotd.bungee.variables.Variables;

public class Commands extends Command {
    private final Variables variables;
    private final Messages messages;

    public Commands(final String string, final Variables variables, final Messages messages) {
        super(string);
        this.variables = variables;
        this.messages = messages;
    }

    public void execute(final CommandSender commandSender, final String[] args) {
        if (commandSender.hasPermission("bungeemotd.admin")) {
            if (args.length < 1 || args[0].equalsIgnoreCase("help"))
                    commandSender.sendMessage(new TextComponent(messages.getUsage()));
            else if (args[0].equalsIgnoreCase("reload")) {
                variables.reloadConfig();
                messages.reload();
                commandSender.sendMessage(new TextComponent(messages.getReload()));
            } else
                commandSender.sendMessage(new TextComponent(messages.getUnknownCommand()));
        } else
            commandSender.sendMessage(new TextComponent(messages.getNoPermission()));
    }
}
