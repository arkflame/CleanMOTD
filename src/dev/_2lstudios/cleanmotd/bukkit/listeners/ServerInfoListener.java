package dev._2lstudios.cleanmotd.bukkit.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;

import org.bukkit.plugin.Plugin;

import dev._2lstudios.cleanmotd.bukkit.variables.Variables;

public class ServerInfoListener extends PacketAdapter {
    private final Variables variables;

    public ServerInfoListener(final Plugin plugin, final Variables variables) {
        super(plugin, ListenerPriority.HIGH, Arrays.asList(PacketType.Status.Server.OUT_SERVER_INFO),
                ListenerOptions.ASYNC);
        this.variables = variables;
    }

    public void register() {
        ProtocolLibrary.getProtocolManager().addPacketListener(this);
    }

    @Override
    public void onPacketSending(final PacketEvent event) {
        if (event.isCancelled()) {
            return;
        }

        final WrappedServerPing ping = event.getPacket().getServerPings().read(0);
        int maxPlayers = ping.getPlayersMaximum();
        int onlinePlayers = ping.getPlayersOnline();

        if (variables.isFakePlayersEnabled()) {
            onlinePlayers = onlinePlayers + variables.getFakePlayersAmount(onlinePlayers);

            ping.setPlayersOnline(onlinePlayers);
        }

        if (variables.isMaxPlayersEnabled()) {
            maxPlayers = variables.isMaxPlayersJustOneMore() ? onlinePlayers + 1 : variables.getMaxPlayers();

            ping.setPlayersMaximum(maxPlayers);
        }

        if (variables.isMotdEnabled()) {
            ping.setMotD(variables.getMOTD(maxPlayers, onlinePlayers));
        }

        if (variables.isSampleEnabled()) {
            final UUID fakeUuid = new UUID(0, 0);
            final String[] sampleString = variables.getSample(ping.getPlayersMaximum(), onlinePlayers);
            final List<WrappedGameProfile> sample = new ArrayList<WrappedGameProfile>(sampleString.length);

            for (final String sampleStringEntry : sampleString) {
                sample.add(new WrappedGameProfile(fakeUuid, sampleStringEntry));
            }

            ping.setPlayers(sample);
        }

        if (variables.isProtocolEnabled()) {
            ping.setVersionName(variables.getProtocolName());
        }
    }
}
