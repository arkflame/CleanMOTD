package dev._2lstudios.cleanmotd.bungee.variables;

import java.util.Collection;
import java.util.HashSet;

import dev._2lstudios.cleanmotd.bungee.utils.ConfigurationUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;

public class Variables {
	private static final String DEFAULT_MOTD = "CleanMotD default generated MotD\nWhoops... No MotD has been specified!";

	private final ConfigurationUtil configurationUtil;
	private final Collection<String> pinged = new HashSet<>();
	private String[] motds;
	private String[] sampleSamples;
	private int maxPlayers, fakePlayersAmount;
	private boolean motdEnabled, sampleEnabled, protocolEnabled, maxPlayersJustOneMore, maxPlayersEnabled,
			fakePlayersEnabled;
	private String protocolName, fakePlayersMode;

	public Variables(ConfigurationUtil configurationUtil) {
		this.configurationUtil = configurationUtil;
		reloadConfig();
	}

	public void reloadConfig() {
		final Configuration configuration = configurationUtil.getConfiguration("%datafolder%/config.yml");

		motdEnabled = configuration.getBoolean("motd.enabled");
		motds = configuration.getStringList("motd.motds").toArray(new String[0]);
		sampleEnabled = configuration.getBoolean("sample.enabled");
		sampleSamples = configuration.getStringList("sample.samples").toArray(new String[0]);
		protocolEnabled = configuration.getBoolean("protocol.enabled");
		protocolName = configuration.getString("protocol.name");
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
		if (motds.length < 1) {
			return DEFAULT_MOTD;
		}

		final int randomIndex = (int) (Math.floor(Math.random() * motds.length));

		return ChatColor.translateAlternateColorCodes('&',
				motds[randomIndex].replace("%maxplayers%", String.valueOf(maxPlayers)).replace("%onlineplayers%",
						String.valueOf(onlinePlayers)));
	}

	public boolean isSampleEnabled() {
		return sampleEnabled;
	}

	public String[] getSample(final int maxPlayers, final int onlinePlayers) {
		return ChatColor.translateAlternateColorCodes('&',
				sampleSamples[(int) (Math.floor(Math.random() * sampleSamples.length))]
						.replace("%maxplayers%", String.valueOf(maxPlayers))
						.replace("%onlineplayers%", String.valueOf(onlinePlayers)))
				.split("\n");
	}

	public boolean isProtocolEnabled() {
		return protocolEnabled;
	}

	public String getProtocolName() {
		return protocolName;
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
