package twolovers.bungeemotd.bungee.variables;

import net.md_5.bungee.config.Configuration;
import twolovers.bungeemotd.bungee.utils.ConfigurationUtil;

import java.util.List;

public class Variables {
	private final ConfigurationUtil configurationUtil;
	private List<String> motdMotds;
	private int maxPlayers, fakePlayersAmount;
	private boolean motdEnabled, maxPlayersJustOneMore, maxPlayersEnabled, fakePlayersEnabled;
	private String fakePlayersMode;

	public Variables(ConfigurationUtil configurationUtil) {
		this.configurationUtil = configurationUtil;
		reloadConfig();
	}

	public void reloadConfig() {
		final Configuration configuration = configurationUtil.getConfiguration("%datafolder%/config.yml");

		motdEnabled = configuration.getBoolean("motd.enabled");
		motdMotds = configuration.getStringList("motd.motds");
		maxPlayersEnabled = configuration.getBoolean("maxplayers.enabled");
		maxPlayers = configuration.getInt("maxplayers.maxplayers");
		maxPlayersJustOneMore = configuration.getBoolean("maxplayers.justonemore");
		fakePlayersEnabled = configuration.getBoolean("fakeplayers.enabled");
		fakePlayersAmount = configuration.getInt("fakeplayers.amount");
		fakePlayersMode = configuration.getString("fakeplayers.mode");
	}

	public boolean isMotdEnabled() {
		return motdEnabled;
	}

	public String getMOTD(int maxPlayers, int onlinePlayers) {
		return motdMotds.get((int) (Math.floor(Math.random() * motdMotds.size())))
				.replace("&", "\u00A7")
				.replace("%newline%", "\n")
				.replace("%maxplayers%", String.valueOf(maxPlayers))
				.replace("%onlineplayers%", String.valueOf(onlinePlayers));
	}

	public boolean isMaxPlayersEnabled() {
		return maxPlayersEnabled;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public boolean isMaxPlayersJustOneMore() {
		return maxPlayersJustOneMore;
	}

	public boolean isFakePlayersEnabled() {
		return fakePlayersEnabled;
	}

	public int getFakePlayersAmount(int players) {
		switch (fakePlayersMode) {
			case "STATIC":
				return fakePlayersAmount;
			case "RANDOM":
				return (int) (Math.floor(Math.random() * fakePlayersAmount) + 1);
			case "DIVISION":
				return players / fakePlayersAmount;
			default:
				return 0;
		}
	}
}