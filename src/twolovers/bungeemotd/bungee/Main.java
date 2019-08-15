package twolovers.bungeemotd.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import twolovers.bungeemotd.bungee.commands.Commands;
import twolovers.bungeemotd.bungee.listeners.ProxyPingListener;
import twolovers.bungeemotd.bungee.utils.ConfigurationUtil;
import twolovers.bungeemotd.bungee.variables.Messages;
import twolovers.bungeemotd.bungee.variables.Variables;

public class Main extends Plugin {

    public void onEnable() {
        final ConfigurationUtil configurationUtil = new ConfigurationUtil(this);

        configurationUtil.createConfiguration("%datafolder%/config.yml");
        configurationUtil.createConfiguration("%datafolder%/messages.yml");

        final Variables variables = new Variables(configurationUtil);
        final Messages messages = new Messages(configurationUtil);
        final PluginManager pluginManager = getProxy().getPluginManager();

        pluginManager.registerListener(this, new ProxyPingListener(variables));
        pluginManager.registerCommand(this, new Commands("bungeemotd", variables, messages));
    }
}