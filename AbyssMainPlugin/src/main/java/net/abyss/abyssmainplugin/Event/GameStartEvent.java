package net.abyss.abyssmainplugin.Event;

import net.abyss.abyssmainplugin.Manager.CentralTower;
import net.abyss.abyssmainplugin.Manager.GameManager;
import net.abyss.abyssmainplugin.Manager.PlayerManager;
import net.abyss.abyssmainplugin.Manager.TitleManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class GameStartEvent implements Listener
{
    @EventHandler
    public void GameStart(PlayerInteractEvent event)
    {
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK)
        {
            return;
        }
        if(event.getClickedBlock().getType() == Material.OBSIDIAN)
        {
            if(!GameManager.getInstance().getStart())
            {
                TextComponent titleMessage = Component.text().color(TextColor.color(255, 197, 61)).content("게임 시작").build();
                TextComponent subTitleMessage = Component.text().color(TextColor.color(0, 7, 77)).content("세계에 심연이 나타납니다.").build();
                TitleManager.getInstance().printTitleToPlayer(titleMessage, subTitleMessage, event.getPlayer());
                GameManager.getInstance().GameStart();
                PlayerManager.getInstance().checkGameCodePlayer(event.getPlayer());
                PlayerManager.getInstance().getPlayerData(event.getPlayer()).setIsLobby(false);
                // 플레이어 텔레포트
                event.getPlayer().teleport(CentralTower.getInstance().getCenterLoc());
            }
        }
        else if(event.getClickedBlock().getType() == Material.CRYING_OBSIDIAN)
        {
            if(GameManager.getInstance().getStart())
            {
                TextComponent titleMessage = Component.text().color(TextColor.color(255, 197, 61)).content("게임 참여").build();
                TextComponent subTitleMessage = Component.text().color(TextColor.color(0, 7, 77)).content("세계에 나타나는 심연을 저지하세요.").build();
                TitleManager.getInstance().printTitleToPlayer(titleMessage, subTitleMessage, event.getPlayer());
                PlayerManager.getInstance().checkGameCodePlayer(event.getPlayer());
                PlayerManager.getInstance().getPlayerData(event.getPlayer()).setIsLobby(false);
                // 플레이어 텔레포트
                event.getPlayer().teleport(CentralTower.getInstance().getCenterLoc());
            }
        }
    }
}
