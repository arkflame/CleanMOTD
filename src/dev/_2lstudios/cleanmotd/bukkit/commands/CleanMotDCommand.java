package dev._2lstudios.cleanmotd.bukkit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import dev._2lstudios.cleanmotd.bukkit.variables.Messages;
import dev._2lstudios.cleanmotd.bukkit.variables.Variables;

public class CleanMotDCommand implements CommandExecutor {
	private final Variables variables;
	private final Messages messages;

	public CleanMotDCommand(final Variables variables, final Messages messages) {
		this.variables = variables;
		this.messages = messages;
	}

	@Override
	public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] strings) {
		if (commandSender.hasPermission("cleanmotd.admin")) {
			if (strings.length < 1 || strings[0].equalsIgnoreCase("help"))
				commandSender.sendMessage(messages.getUsage());
			else if (strings[0].equalsIgnoreCase("reload")) {
				variables.reloadConfig();
				messages.reload();
				commandSender.sendMessage(messages.getReload());
			} else
				commandSender.sendMessage(messages.getUnknownCommand());
		} else
			commandSender.sendMessage(messages.getNoPermission());

		return true;
	}
}
