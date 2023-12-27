package net.abyss.abyssmainplugin.Event;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerExpCancel implements Listener
{
    @EventHandler
    public void playerGetExp(PlayerPickupExperienceEvent event)
    {
        event.setCancelled(true);
    }
}
