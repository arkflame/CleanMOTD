package org.mineblock.motd.bukkit;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import org.mineblock.motd.bukkit.handler.CommandHandler;
import org.mineblock.motd.bukkit.listeners.ServerInfoListener;
import org.mineblock.motd.bukkit.listeners.ServerListPingListener;
import org.mineblock.motd.bukkit.utils.ConfigurationUtil;
import org.mineblock.motd.bukkit.variables.Messages;
import org.mineblock.motd.bukkit.variables.Variables;

public class BukkitPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		final ConfigurationUtil configurationUtil = new ConfigurationUtil(this);

		configurationUtil.createConfiguration("%datafolder%/config.yml");
		configurationUtil.createConfiguration("%datafolder%/messages.yml");

		final Server server = getServer();
		final Variables variables = new Variables(configurationUtil);
		final Messages messages = new Messages(configurationUtil);
		final PluginManager pluginManager = server.getPluginManager();

		getCommand("motd").setExecutor(new CommandHandler(variables, messages, this));

		if (pluginManager.isPluginEnabled("ProtocolLib")) {
			new ServerInfoListener(this, variables).register();
		} else {
			pluginManager.registerEvents(new ServerListPingListener(variables), this);
		}
	}
}