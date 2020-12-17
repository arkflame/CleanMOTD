package dev._2lstudios.cleanmotd.bukkit.listeners;

import java.util.Arrays;
import java.util.UUID;

import com.comphenix.protocol.PacketType;
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

    @Override
    public void onPacketSending(final PacketEvent event) {
        if (event.isCancelled()) {
            return;
        }

        final WrappedServerPing ping = event.getPacket().getServerPings().read(0);
        final String protocol = variables.getProtocol();

        int onlinePlayers = ping.getPlayersOnline();

        if (variables.isFakePlayersEnabled()) {
            onlinePlayers = onlinePlayers + variables.getFakePlayersAmount(onlinePlayers);

            ping.setPlayersOnline(onlinePlayers);
        }

        if (variables.isProtocolEnabled()) {
            ping.setVersionName(protocol);
        }

        if (variables.isSampleEnabled()) {
            final UUID fakeUuid = new UUID(0, 0);
            final String[] sampleString = variables.getSample(ping.getPlayersMaximum(), onlinePlayers);
            final WrappedGameProfile[] sample = new WrappedGameProfile[sampleString.length];

            for (int i = 0; i < sampleString.length; i++) {
                sample[i] = new WrappedGameProfile(fakeUuid, sampleString[i]);
            }

            ping.setPlayers(Arrays.asList(sample));
        }
    }
}
