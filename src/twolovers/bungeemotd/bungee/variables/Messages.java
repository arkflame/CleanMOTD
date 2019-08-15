package twolovers.bungeemotd.bungee.variables;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import twolovers.bungeemotd.bungee.utils.ConfigurationUtil;

public class Messages {
	private final ConfigurationUtil configurationUtil;
	private String reload;
	private String usage;
	private String unknownCommand;
	private String noPermission;

	public Messages(final ConfigurationUtil configurationUtil) {
		this.configurationUtil = configurationUtil;
		reload();
	}

	public void reload() {
		final Configuration messages = configurationUtil.getConfiguration("%datafolder%/messages.yml");

		reload = ChatColor.translateAlternateColorCodes('&', messages.getString("messages.reload").replace("&", "\u00A7"));
		usage = ChatColor.translateAlternateColorCodes('&', messages.getString("messages.usage").replace("&", "\u00A7"));
		unknownCommand = ChatColor.translateAlternateColorCodes('&', messages.getString("messages.unknowncommand").replace("&", "\u00A7"));
		noPermission = ChatColor.translateAlternateColorCodes('&', messages.getString("messages.nopermission"));
	}

	public String getReload() {
		return reload;
	}

	public String getUsage() {
		return usage;
	}

	public String getUnknownCommand() {
		return unknownCommand;
	}

	public String getNoPermission() {
		return noPermission;
	}
}
