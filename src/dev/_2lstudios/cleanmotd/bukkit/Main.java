package dev._2lstudios.cleanmotd.bukkit;

import com.comphenix.protocol.ProtocolLibrary;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.cleanmotd.bukkit.commands.CleanMotDCommand;
import dev._2lstudios.cleanmotd.bukkit.listeners.ServerInfoListener;
import dev._2lstudios.cleanmotd.bukkit.listeners.ServerListPingListener;
import dev._2lstudios.cleanmotd.bukkit.utils.ConfigurationUtil;
import dev._2lstudios.cleanmotd.bukkit.variables.Messages;
import dev._2lstudios.cleanmotd.bukkit.variables.Variables;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		final ConfigurationUtil configurationUtil = new ConfigurationUtil(this);

		configurationUtil.createConfiguration("%datafolder%/config.yml");
		configurationUtil.createConfiguration("%datafolder%/messages.yml");

		final Server server = getServer();
		final Variables variables = new Variables(configurationUtil);
		final Messages messages = new Messages(configurationUtil);
		final PluginManager pluginManager = server.getPluginManager();

		pluginManager.registerEvents(new ServerListPingListener(variables), this);
		getCommand("cleanmotd").setExecutor(new CleanMotDCommand(variables, messages));

		if (variables.isProtocolEnabled() || variables.isSampleEnabled() || variables.isFakePlayersEnabled()) {
			if (!pluginManager.isPluginEnabled("ProtocolLib")) {
				throw new IllegalStateException("Protocol feature requires ProtocolLib to change protocol name on Spigot.");
			}

			ProtocolLibrary.getProtocolManager().addPacketListener(new ServerInfoListener(this, variables));
		}

		server.getScheduler().runTaskTimer(this, variables::clearPinged, variables.getCacheTime(), variables.getCacheTime());
	}
}