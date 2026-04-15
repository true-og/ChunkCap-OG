// This is free and unencumbered software released into the public domain.
// Author: NotAlexNoyle.
package plugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ChunkCapOG extends JavaPlugin {

    private static ChunkCapOG plugin;
    private static FileConfiguration config;

    @Override
    public void onEnable() {

        plugin = this;

        saveDefaultConfig();
        config = getConfig();

        getServer().getPluginManager().registerEvents(new Listeners(), this);

    }

    public static ChunkCapOG getPlugin() {

        return plugin;

    }

    public static FileConfiguration config() {

        return config;

    }

}
