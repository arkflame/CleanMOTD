package dev._2lstudios.cleanmotd.bukkit;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
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
import java.util.Collections;
import java.util.UUID;

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

		if (variables.isProtocolEnabled() || variables.isSampleEnabled() || variables.isFakePlayersEnabled()) {
			if (!pluginManager.isPluginEnabled("ProtocolLib")) {
				throw new NullPointerException("Protocol feature requires ProtocolLib to change protocol name on Spigot.");
			}

			ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, Arrays.asList(PacketType.Status.Server.OUT_SERVER_INFO), ListenerOptions.ASYNC) {
				@Override
				public void onPacketSending(final PacketEvent event) {
					final WrappedServerPing ping = event.getPacket().getServerPings().read(0);
					final String protocol = variables.getProtocol();

					int onlinePlayers = ping.getPlayersOnline();

					if (variables.isFakePlayersEnabled()) {
						onlinePlayers = onlinePlayers + variables.getFakePlayersAmount(onlinePlayers);

						ping.setPlayersOnline(onlinePlayers);
					}

					if (variables.isProtocolEnabled()) {
						ping.setVersionName(protocol);
					}

					if (variables.isSampleEnabled()) {
						ping.setPlayers(Collections.singleton(
								new WrappedGameProfile(new UUID(0, 0), variables.getSample(ping.getPlayersMaximum(), onlinePlayers))));
					}
				}
			});
		}

		server.getScheduler().runTaskTimer(this, variables::clearPinged, variables.getCacheTime(), variables.getCacheTime());
	}
}