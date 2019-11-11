package twolovers.cleanmotd.bukkit.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import twolovers.cleanmotd.bukkit.variables.Variables;

public class ServerListPingListener implements Listener {
	private Variables variables;

	public ServerListPingListener(final Variables variables) {
		this.variables = variables;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onServerListPing(final ServerListPingEvent event) {
		// Theres no method to get the player name on Spigot.
		final String name = "";
		int onlinePlayers = event.getNumPlayers();
		int maxPlayers = event.getMaxPlayers();

		// Theres no method yet to change player num on ping event.
			/*if (variables.isFakePlayersEnabled())
				onlinePlayers = onlinePlayers + variables.getFakePlayersAmount(onlinePlayers);*/

		if (variables.isMaxPlayersEnabled())
			if (variables.isMaxPlayersJustOneMore())
				maxPlayers = onlinePlayers + 1;
			else
				maxPlayers = variables.getMaxPlayers();

		if (variables.isMotdEnabled())
			event.setMotd(variables.getMOTD(maxPlayers, onlinePlayers));

		event.setMaxPlayers(maxPlayers);
	}
}
