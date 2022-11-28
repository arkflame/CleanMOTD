package dev._2lstudios.cleanmotd.shared.variables;

import java.util.Collection;
import java.util.HashSet;

public class Variables {
	private static final String DEFAULT_MOTD = "CleanMOTD default generated MOTD\nWhoops... No MOTD has been specified!";

	private Collection<String> pinged = new HashSet<>();

	private String[] motds;
	private String[] sampleSamples;

	private int maxPlayers;
	private int fakePlayersAmount;

	private boolean motdEnabled;
	private boolean sampleEnabled;
	private boolean protocolEnabled;
	private boolean maxPlayersJustOneMore;
	private boolean maxPlayersEnabled;
	private boolean fakePlayersEnabled;

	private String protocolName;
	private String fakePlayersMode;

	public static String getDefaultMotd() {
		return DEFAULT_MOTD;
	}

	public Collection<String> getPinged() {
		return pinged;
	}

	public String[] getMotds() {
		return motds;
	}

	public void setMotds(String[] motds) {
		this.motds = motds;
	}

	public String[] getSampleSamples() {
		return sampleSamples;
	}

	public void setSampleSamples(String[] sampleSamples) {
		this.sampleSamples = sampleSamples;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public int getFakePlayersAmount() {
		return fakePlayersAmount;
	}

	public void setFakePlayersAmount(int fakePlayersAmount) {
		this.fakePlayersAmount = fakePlayersAmount;
	}

	public boolean isMotdEnabled() {
		return motdEnabled;
	}

	public void setMotdEnabled(boolean motdEnabled) {
		this.motdEnabled = motdEnabled;
	}

	public boolean isSampleEnabled() {
		return sampleEnabled;
	}

	public void setSampleEnabled(boolean sampleEnabled) {
		this.sampleEnabled = sampleEnabled;
	}

	public boolean isProtocolEnabled() {
		return protocolEnabled;
	}

	public void setProtocolEnabled(boolean protocolEnabled) {
		this.protocolEnabled = protocolEnabled;
	}

	public boolean isMaxPlayersJustOneMore() {
		return maxPlayersJustOneMore;
	}

	public void setMaxPlayersJustOneMore(boolean maxPlayersJustOneMore) {
		this.maxPlayersJustOneMore = maxPlayersJustOneMore;
	}

	public boolean isMaxPlayersEnabled() {
		return maxPlayersEnabled;
	}

	public void setMaxPlayersEnabled(boolean maxPlayersEnabled) {
		this.maxPlayersEnabled = maxPlayersEnabled;
	}

	public boolean isFakePlayersEnabled() {
		return fakePlayersEnabled;
	}

	public void setFakePlayersEnabled(boolean fakePlayersEnabled) {
		this.fakePlayersEnabled = fakePlayersEnabled;
	}

	public String getProtocolName() {
		return protocolName;
	}

	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}

	public String getFakePlayersMode() {
		return fakePlayersMode;
	}

	public void setFakePlayersMode(String fakePlayersMode) {
		this.fakePlayersMode = fakePlayersMode;
	}

	public String getMOTD(final int maxPlayers, final int onlinePlayers) {
		if (motds.length < 1) {
			return DEFAULT_MOTD;
		}

		final int randomIndex = (int) (Math.floor(Math.random() * motds.length));

		return motds[randomIndex].replace("%maxplayers%", String.valueOf(maxPlayers)).replace("%onlineplayers%",
				String.valueOf(onlinePlayers));
	}

	public String[] getSample(final int maxPlayers, final int onlinePlayers) {
		return sampleSamples[(int) (Math.floor(Math.random() * sampleSamples.length))]
				.replace("%maxplayers%", String.valueOf(maxPlayers))
				.replace("%onlineplayers%", String.valueOf(onlinePlayers))
				.split("\n");
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
