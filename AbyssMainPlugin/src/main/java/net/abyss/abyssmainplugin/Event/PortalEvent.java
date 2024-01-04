package net.abyss.abyssmainplugin.Event;

import net.abyss.abyssmainplugin.Gates.Gate;
import net.abyss.abyssmainplugin.Manager.GateManager;
import net.abyss.abyssmainplugin.Manager.MonsterManager;
import net.abyss.abyssmainplugin.Manager.TitleManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;

import java.util.List;

public class PortalEvent implements Listener
{
    @EventHandler
    public void bossNotPortal(EntityPortalEvent event)
    {
        if(MonsterManager.getInstance().getBossByEntity(event.getEntity()) != null)
        {
            event.setCancelled(true);
        }
        else if(MonsterManager.getInstance().getMonsterByEntity(event.getEntity()) != null)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerPortal(PlayerPortalEvent event)
    {
        event.setCancelled(true);

        Player player = event.getPlayer();
        List<Gate> gateList = GateManager.getInstance().getGateList();
        Gate targetGate = null;
        double minDistance = Double.MAX_VALUE;
        boolean isGo = true;
        for(Gate gate : gateList)
        {
            if(!gate.getOnGate())
            {
                continue;
            }
            if(minDistance > gate.getGateMainLoc().distance(player.getLocation()))
            {
                minDistance = gate.getGateMainLoc().distance(player.getLocation());
                targetGate = gate;
                isGo = true;
            }
            if(minDistance > gate.getGateDimensionLoc().distance(player.getLocation()))
            {
                minDistance = gate.getGateDimensionLoc().distance(player.getLocation());
                targetGate = gate;
                isGo = false;
            }
        }

        if(targetGate == null)
        {
            return;
        }

        double x;
        double y;
        double z;

        // isGo가 true 라면 들어간 포탈이 메인게이트이기 때문에 디멘션게이트 위치로 텔레포트
        if(isGo)
        {
            x = targetGate.getGateDimensionLoc().getX();
            y = targetGate.getGateDimensionLoc().getY();
            z = targetGate.getGateDimensionLoc().getZ();

            targetGate.playerEnterGate(player);

            TextComponent titleMessage = Component.text().color(TextColor.color(35, 255, 19)).content("게이트 입장").build();
            TextComponent subTitleMessage = (TextComponent) targetGate.getGateName();
            TitleManager.getInstance().printTitleToPlayer(titleMessage, subTitleMessage, player);
        }
        else
        {
            x = targetGate.getGateMainLoc().getX();
            y = targetGate.getGateMainLoc().getY();
            z = targetGate.getGateMainLoc().getZ();

            targetGate.playerExitGate(player);
        }

        Location targetLoc = new Location(player.getWorld(), x, y, z);
        player.teleport(targetLoc);
    }
}
