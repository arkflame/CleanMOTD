package dev._2lstudios.cleanmotd.bukkit;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.cleanmotd.bukkit.commands.CleanMOTDCommand;
import dev._2lstudios.cleanmotd.bukkit.listeners.ServerInfoListener;
import dev._2lstudios.cleanmotd.bukkit.listeners.ServerListPingListener;
import dev._2lstudios.cleanmotd.bukkit.utils.ConfigurationUtil;
import dev._2lstudios.cleanmotd.bukkit.variables.BukkitVariables;
import dev._2lstudios.cleanmotd.bukkit.variables.Messages;

public class CleanMOTD extends JavaPlugin {
	@Override
	public void onEnable() {
		final ConfigurationUtil configurationUtil = new ConfigurationUtil(this);

		configurationUtil.createConfiguration("%datafolder%/config.yml");
		configurationUtil.createConfiguration("%datafolder%/messages.yml");

		final Server server = getServer();
		final BukkitVariables variables = new BukkitVariables(configurationUtil);
		final Messages messages = new Messages(configurationUtil);
		final PluginManager pluginManager = server.getPluginManager();

		getCommand("cleanmotd").setExecutor(new CleanMOTDCommand(variables, messages));

		if (pluginManager.isPluginEnabled("ProtocolLib")) {
			new ServerInfoListener(this, variables).register();
		} else {
			pluginManager.registerEvents(new ServerListPingListener(variables), this);
		}
	}
}