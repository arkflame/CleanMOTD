package dev._2lstudios.cleanmotd.bungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import dev._2lstudios.cleanmotd.bungee.commands.CleanMotDCommand;
import dev._2lstudios.cleanmotd.bungee.listeners.ProxyPingListener;
import dev._2lstudios.cleanmotd.bungee.utils.ConfigurationUtil;
import dev._2lstudios.cleanmotd.bungee.variables.Messages;
import dev._2lstudios.cleanmotd.bungee.variables.Variables;

public class Main extends Plugin {
	public void onEnable() {
		final ConfigurationUtil configurationUtil = new ConfigurationUtil(this);

		configurationUtil.createConfiguration("%datafolder%/config.yml");
		configurationUtil.createConfiguration("%datafolder%/messages.yml");

		final ProxyServer proxy = getProxy();
		final Variables variables = new Variables(configurationUtil);
		final Messages messages = new Messages(configurationUtil);
		final PluginManager pluginManager = proxy.getPluginManager();

		pluginManager.registerListener(this, new ProxyPingListener(variables));
		pluginManager.registerCommand(this, new CleanMotDCommand("cleanmotd", variables, messages));

	}
}