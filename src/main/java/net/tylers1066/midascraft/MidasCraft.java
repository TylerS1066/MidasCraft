package net.tylers1066.midascraft;

import org.bukkit.plugin.java.JavaPlugin;

public final class MidasCraft extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("midas").setExecutor(new MidasCommand());
    }
}
