package net.abyss.abyssmainplugin.Event;

import io.lumine.mythic.api.adapters.AbstractLocation;
import net.abyss.abyssmainplugin.Gates.Gate;
import net.abyss.abyssmainplugin.Manager.GateManager;
import net.abyss.abyssmainplugin.Manager.MonsterManager;
import net.abyss.abyssmainplugin.Monsters.BossMonster;
import net.abyss.abyssmainplugin.Monsters.Monster;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
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
    public void mobPortal(EntityPortalEnterEvent event)
    {
        Monster targetMonster = MonsterManager.getInstance().getMonsterByEntity(event.getEntity());
        if(targetMonster == null)
        {
            return;
        }

        Gate parentGate = targetMonster.getParentGate();

        if(event.getLocation().distance(parentGate.getGateDimensionLoc()) < event.getLocation().distance(parentGate.getGateMainLoc()))
        {
            double x = parentGate.getGateMainLoc().getX();
            double y = parentGate.getGateMainLoc().getY();
            double z = parentGate.getGateMainLoc().getZ();
            AbstractLocation targetLoc = new AbstractLocation(targetMonster.getMob().getEntity().getWorld(), x, y, z);
            targetMonster.getMob().getEntity().teleport(targetLoc);
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

        double x;
        double y;
        double z;

        // isGo가 true 라면 들어간 포탈이 메인게이트이기 때문에 디멘션게이트 위치로 텔레포트
        if(isGo)
        {
            x = targetGate.getGateDimensionLoc().getX();
            y = targetGate.getGateDimensionLoc().getY();
            z = targetGate.getGateDimensionLoc().getZ();
        }
        else
        {
            x = targetGate.getGateMainLoc().getX();
            y = targetGate.getGateMainLoc().getY();
            z = targetGate.getGateMainLoc().getZ();
        }

        Location targetLoc = new Location(player.getWorld(), x, y, z);
        player.teleport(targetLoc);
    }
}
