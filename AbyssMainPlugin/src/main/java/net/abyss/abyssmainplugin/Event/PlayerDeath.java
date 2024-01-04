package net.abyss.abyssmainplugin.Event;

import net.abyss.abyssmainplugin.Gates.Gate;
import net.abyss.abyssmainplugin.Manager.GateManager;
import net.abyss.abyssmainplugin.Manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeath implements Listener
{
    private final Location spawnLoc = new Location(Bukkit.getWorld("world"), 5000, -60, 5002);
    @EventHandler
    public void playerDeath(PlayerDeathEvent event)
    {
        for(Gate gate: GateManager.getInstance().getGateList())
        {
            if(gate.playerDeath(event.getPlayer()))
            {
                break;
            }
        }
    }

    @EventHandler
    public void playerRevive(PlayerRespawnEvent event)
    {
        event.getPlayer().teleport(spawnLoc);
        PlayerManager.getInstance().getPlayerData(event.getPlayer()).setIsLobby(true);
    }
}
