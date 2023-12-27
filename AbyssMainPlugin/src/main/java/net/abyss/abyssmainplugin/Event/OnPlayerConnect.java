package net.abyss.abyssmainplugin.Event;

import net.abyss.abyssmainplugin.Manager.PlayerManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerConnect implements Listener
{
    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        String playerName = player.getName();
        TextComponent message = Component.text().color(TextColor.color(0,255,0)).content(playerName + "님이 접속하셨습니다.").build();
        event.joinMessage(message);
        PlayerManager.getInstance().addPlayer(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        String playerName = player.getName();
        TextComponent message = Component.text().color(TextColor.color(0,255,0)).content(playerName + "님이 퇴장하셨습니다.").build();
        event.quitMessage(message);
        PlayerManager.getInstance().removePlayer(player);
    }
}