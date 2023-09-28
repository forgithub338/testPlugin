package bs.untitled13;

import org.bukkit.plugin.java.JavaPlugin;

public final class Untitled13 extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Event(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
