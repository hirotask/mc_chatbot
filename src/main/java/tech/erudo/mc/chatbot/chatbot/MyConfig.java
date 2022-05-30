package tech.erudo.mc.chatbot.chatbot;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class MyConfig {
    private Plugin plugin;
    private FileConfiguration config;

    @Getter
    public String appKey;

    public MyConfig(Plugin plugin) {
        this.plugin = plugin;
        
        load();
    }

    private void load() {
        plugin.saveDefaultConfig();

        if(config != null) reload();

        config = plugin.getConfig();

        appKey = config.getString("appKey");
    }

    public void reload() {
        plugin.reloadConfig();
    }
}
