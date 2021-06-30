package org.mineblock.motd.bungee.handler;

import org.mineblock.motd.bungee.BungeePlugin;
import org.mineblock.motd.bungee.variables.Messages;
import org.mineblock.motd.bungee.variables.Variables;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class CommandHandler extends Command {
	private final Variables variables;
	private final Messages messages;
	private final BungeePlugin plugin;

	public CommandHandler(final String string, final Variables variables, final Messages messages, BungeePlugin plugin) {
		super(string);
		this.variables = variables;
		this.messages = messages;
		this.plugin = plugin;
	}

	public void execute(final CommandSender commandSender, final String[] args) {
		switch (args[0].toLowerCase()){
			case "help":{
				if (commandSender.hasPermission("mineblock.command.motd")) {
					commandSender.sendMessage(new TextComponent(messages.getUsage()));
				} else commandSender.sendMessage(new TextComponent(messages.getNoPermission()));
				break;
			}
			case "reload":{
				if (commandSender.hasPermission("mineblock.command.motd")) {
					variables.reloadConfig();
					messages.reload();
					commandSender.sendMessage(new TextComponent(messages.getReload()));
				} else commandSender.sendMessage(new TextComponent(messages.getNoPermission()));
				break;
			}
			default:{
				commandSender.sendMessage(new TextComponent("This server is running "+ plugin.getDescription().getName() +" version "+ plugin.getDescription().getVersion()+" by "+ plugin.getDescription().getAuthor()));
				commandSender.sendMessage(new TextComponent(messages.getUnknownCommand()));
				break;
			}
		}
	}
}
