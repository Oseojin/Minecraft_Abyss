package net.abyss.abyssmainplugin.Event;

import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import io.lumine.mythic.core.mobs.ActiveMob;
import net.abyss.abyssmainplugin.Manager.MonsterManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class MonsterDeathEvent implements Listener
{
    @EventHandler
    public void MobDeath(MythicMobDeathEvent event)
    {
        ActiveMob targetMob = event.getMob();
        event.getDrops().clear();
        event.getDrops().add(new ItemStack(Material.DIAMOND, 1));
        MonsterManager.getInstance().monsterDead(targetMob);
    }
}
