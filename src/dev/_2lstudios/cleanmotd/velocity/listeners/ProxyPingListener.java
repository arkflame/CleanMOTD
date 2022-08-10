package dev._2lstudios.cleanmotd.velocity.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.server.ServerPing;

import dev._2lstudios.cleanmotd.velocity.CleanMOTD;

public class ProxyPingListener {
    private final CleanMOTD plugin;
    public ProxyPingListener(CleanMOTD plugin) {
        this.plugin = plugin;
    }
    @Subscribe
    public void onPing(ProxyPingEvent event) {
        ServerPing.Builder builder = event.getPing().asBuilder();

        /*final ServerPing.Players players = response.getPlayers();
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
			response.getVersion().setName(variables.getProtocolName());
		}

		if (variables.isSampleEnabled()) {
			final UUID fakeUuid = new UUID(0, 0);
			final String[] sampleString = variables.getSample(maxPlayers, onlinePlayers);
			final ServerPing.PlayerInfo[] sample = new ServerPing.PlayerInfo[sampleString.length];

			for (int i = 0; i < sampleString.length; i++) {
				sample[i] = new ServerPing.PlayerInfo(sampleString[i], fakeUuid);
			}

			players.setSample(sample);
		}*/
    }
}
