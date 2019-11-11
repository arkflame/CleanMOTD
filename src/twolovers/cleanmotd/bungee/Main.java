package twolovers.cleanmotd.bungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import twolovers.cleanmotd.bungee.commands.CleanMotDCommand;
import twolovers.cleanmotd.bungee.listeners.ProxyPingListener;
import twolovers.cleanmotd.bungee.utils.ConfigurationUtil;
import twolovers.cleanmotd.bungee.variables.Messages;
import twolovers.cleanmotd.bungee.variables.Variables;

import java.util.concurrent.TimeUnit;

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

		proxy.getScheduler().schedule(this, variables::clearPinged, variables.getCacheTime(), variables.getCacheTime(), TimeUnit.MILLISECONDS);
	}
}