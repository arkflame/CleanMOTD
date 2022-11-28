package dev._2lstudios.cleanmotd.bukkit.variables;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;

import dev._2lstudios.cleanmotd.bukkit.utils.ConfigurationUtil;
import dev._2lstudios.cleanmotd.shared.variables.Variables;

public class BukkitVariables extends Variables {
	private final ConfigurationUtil configurationUtil;
	
	public BukkitVariables(ConfigurationUtil configurationUtil) {
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