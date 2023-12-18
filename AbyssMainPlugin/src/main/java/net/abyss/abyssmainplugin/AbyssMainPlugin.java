package net.abyss.abyssmainplugin;

import net.abyss.abyssmainplugin.Event.OnJoin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AbyssMainPlugin extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        OnJoin.setPlugin(this);
        OnJoin eventOnEnable = new OnJoin();
        Bukkit.getPluginManager().registerEvents(eventOnEnable, this);
    }

    @Override
    public void onDisable()
    {

    }
}
