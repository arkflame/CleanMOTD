package twolovers.bungeemotd.bungee.listeners;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import twolovers.bungeemotd.bungee.variables.Variables;

public class ProxyPingListener implements Listener {
	private Variables variables;

	public ProxyPingListener(final Variables variables) {
		this.variables = variables;
	}

	@EventHandler(priority = 64)
	public void onProxyPing(final ProxyPingEvent event) {
		final ServerPing response = event.getResponse();

		if (response != null) {
			final ServerPing.Players players = response.getPlayers();
			int onlinePlayers = players.getOnline();
			int maxPlayers = players.getMax();

			if (variables.isFakePlayersEnabled())
				onlinePlayers = onlinePlayers + variables.getFakePlayersAmount(onlinePlayers);

			if (variables.isMaxPlayersEnabled())
				if (variables.isMaxPlayersJustOneMore())
					maxPlayers = onlinePlayers + 1;
				else
					maxPlayers = variables.getMaxPlayers();

			if (variables.isMotdEnabled())
				response.setDescriptionComponent(new TextComponent(variables.getMOTD(maxPlayers, onlinePlayers)));

			players.setMax(maxPlayers);
			players.setOnline(onlinePlayers);
		}
	}
}
