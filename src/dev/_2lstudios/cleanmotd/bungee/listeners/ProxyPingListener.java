package dev._2lstudios.cleanmotd.bungee.listeners;

import dev._2lstudios.cleanmotd.bungee.variables.Variables;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

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
			maxPlayers = variables.isMaxPlayersJustOneMore() ? onlinePlayers + 1 : variables.getMaxPlayers();

			players.setMax(maxPlayers);
		}

		if (variables.isMotdEnabled()) {
			response.setDescriptionComponent(new TextComponent(variables.getMOTD(maxPlayers, onlinePlayers)));
		}

		if (variables.isProtocolEnabled()) {
			response.getVersion().setName(variables.getProtocol());
		}

		if (variables.isSampleEnabled()) {
			final UUID fakeUuid = new UUID(0, 0);
			final String[] sampleString = variables.getSample(maxPlayers, onlinePlayers);
			final ServerPing.PlayerInfo[] sample = new ServerPing.PlayerInfo[sampleString.length];

			for (int i = 0; i < sampleString.length; i++) {
				sample[i] = new ServerPing.PlayerInfo(sampleString[i], fakeUuid);
			}

			players.setSample(sample);
		}
	}
}
