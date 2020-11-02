package dev._2lstudios.cleanmotd.bukkit;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.cleanmotd.bukkit.commands.CleanMotDCommand;
import dev._2lstudios.cleanmotd.bukkit.listeners.ServerListPingListener;
import dev._2lstudios.cleanmotd.bukkit.utils.ConfigurationUtil;
import dev._2lstudios.cleanmotd.bukkit.variables.Messages;
import dev._2lstudios.cleanmotd.bukkit.variables.Variables;

import java.util.Arrays;

public class Main extends JavaPlugin {
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

		if (variables.isProtocolEnabled()) {
			if (!pluginManager.isPluginEnabled("ProtocolLib")) {
				throw new NullPointerException("Protocol feature requires ProtocolLib to change protocol name on Spigot.");
			}

			ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, Arrays.asList(PacketType.Status.Server.OUT_SERVER_INFO), ListenerOptions.ASYNC) {
				@Override
				public void onPacketSending(final PacketEvent event) {
					final WrappedServerPing ping = event.getPacket().getServerPings().read(0);
					final String protocol = variables.getProtocol();

					ping.setVersionName(protocol);
				}
			});
		}

		server.getScheduler().runTaskTimer(this, variables::clearPinged, variables.getCacheTime(), variables.getCacheTime());
	}
}