package dev._2lstudios.cleanmotd.bungee.listeners;

import dev._2lstudios.cleanmotd.bungee.variables.Variables;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPingListener implements Listener {
	private final Variables variables;

	public ProxyPingListener(final Variables variables) {
		this.variables = variables;
	}

	@EventHandler(priority = 64)
	public void onProxyPing(final ProxyPingEvent event) {
		final ServerPing response = event.getResponse();

		if (response == null || event instanceof Cancellable && ((Cancellable) event).isCancelled()) {
			return;
		}

		final ServerPing.Players players = response.getPlayers();
		int onlinePlayers = players.getOnline();
		int maxPlayers = players.getMax();

		if (variables.isFakePlayersEnabled()) {
			onlinePlayers = onlinePlayers + variables.getFakePlayersAmount(onlinePlayers);

			players.setOnline(onlinePlayers);
		}

		if (variables.isMaxPlayersEnabled()) {
			if (variables.isMaxPlayersJustOneMore()) {
				maxPlayers = onlinePlayers + 1;
			} else {
				maxPlayers = variables.getMaxPlayers();
			}

			players.setMax(maxPlayers);
		}

		if (variables.isMotdEnabled()) {
			response.setDescriptionComponent(new TextComponent(variables.getMOTD(maxPlayers, onlinePlayers)));
		}

		if (variables.isProtocolEnabled()) {
			response.getVersion().setName(variables.getProtocol());
		}

		if (variables.isCacheEnabled()) {
			final String address = event.getConnection().getAddress().getHostString();

			if (variables.hasPinged(address)) {
				response.setFavicon("");
			} else {
				variables.addPinged(address);
			}
		}
	}
}
