package dev._2lstudios.cleanmotd.bungee.variables;

import dev._2lstudios.cleanmotd.bungee.utils.ConfigurationUtil;
import dev._2lstudios.cleanmotd.shared.variables.Variables;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;

public class BungeeVariables extends Variables {
	private final ConfigurationUtil configurationUtil;

	public BungeeVariables(ConfigurationUtil configurationUtil) {
		this.configurationUtil = configurationUtil;
		reloadConfig();
	}

	public void reloadConfig() {
		final Configuration configuration = configurationUtil.getConfiguration("%datafolder%/config.yml");

		setMotdEnabled(configuration.getBoolean("motd.enabled"));
		setMotds(configuration.getStringList("motd.motds").toArray(new String[0]));
		setSampleEnabled(configuration.getBoolean("sample.enabled"));
		setSampleSamples(configuration.getStringList("sample.samples").toArray(new String[0]));
		setProtocolEnabled(configuration.getBoolean("protocol.enabled"));
		setProtocolName(configuration.getString("protocol.name"));
		setMaxPlayersEnabled(configuration.getBoolean("maxplayers.enabled"));
		setMaxPlayers(configuration.getInt("maxplayers.maxplayers"));
		setMaxPlayersJustOneMore(configuration.getBoolean("maxplayers.justonemore"));
		setFakePlayersEnabled(configuration.getBoolean("fakeplayers.enabled"));
		setFakePlayersAmount(configuration.getInt("fakeplayers.amount"));
		setFakePlayersMode(configuration.getString("fakeplayers.mode"));
	}

	@Override
	public String[] getSample(int maxPlayers, int onlinePlayers) {
		String[] sample = super.getSample(maxPlayers, onlinePlayers);

		for (int i = 0; i < sample.length; i++) {
			sample[i] = ChatColor.translateAlternateColorCodes('&', sample[i]);
		}

		return sample;
	}

	@Override
	public String getMOTD(int maxPlayers, int onlinePlayers) {
		return ChatColor.translateAlternateColorCodes('&', super.getMOTD(maxPlayers, onlinePlayers));
	}
}
