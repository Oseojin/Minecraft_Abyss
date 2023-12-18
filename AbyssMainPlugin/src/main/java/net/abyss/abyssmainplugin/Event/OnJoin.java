package net.abyss.abyssmainplugin.Event;

import net.abyss.abyssmainplugin.AbyssMainPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener
{
    public static AbyssMainPlugin plugin;
    public static void setPlugin(AbyssMainPlugin MainPlugin)
    {
        plugin = MainPlugin;
    }

    @EventHandler
    public void onEnable(PlayerJoinEvent event)
    {
        plugin.getServer().getConsoleSender().sendMessage("플러그인 실행!");
    }
}
