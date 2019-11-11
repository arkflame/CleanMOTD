package twolovers.cleanmotd.bukkit.variables;

import org.bukkit.configuration.Configuration;
import twolovers.cleanmotd.bukkit.utils.ConfigurationUtil;

import java.util.Collection;
import java.util.HashSet;

public class Variables {
	private final ConfigurationUtil configurationUtil;
	private Collection<String> pinged = new HashSet<>();
	private String[] motdMotds;
	private int cacheTime, maxPlayers, fakePlayersAmount;
	private boolean motdEnabled, cacheEnabled, maxPlayersJustOneMore, maxPlayersEnabled, fakePlayersEnabled;
	private String fakePlayersMode;

	public Variables(ConfigurationUtil configurationUtil) {
		this.configurationUtil = configurationUtil;
		reloadConfig();
	}

	public void reloadConfig() {
		final Configuration configuration = configurationUtil.getConfiguration("%datafolder%/config.yml");

		motdEnabled = configuration.getBoolean("motd.enabled");
		motdMotds = configuration.getStringList("motd.motds").toArray(new String[0]);
		cacheEnabled = configuration.getBoolean("cache.enabled");
		cacheTime = configuration.getInt("cache.time");
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

	public String getMOTD(final int maxPlayers, final int onlinePlayers) {
		return motdMotds[(int) (Math.floor(Math.random() * motdMotds.length))]
				.replace("&", "\u00A7")
				.replace("%newline%", "\n")
				.replace("%maxplayers%", String.valueOf(maxPlayers))
				.replace("%onlineplayers%", String.valueOf(onlinePlayers));
	}

	public boolean isCacheEnabled() {
		return cacheEnabled;
	}

	public int getCacheTime() {
		return cacheTime;
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

	public boolean hasPinged(final String name) {
		return pinged.contains(name);
	}

	public void addPinged(final String name) {
		pinged.add(name);
	}

	public void clearPinged() {
		pinged.clear();
	}
}