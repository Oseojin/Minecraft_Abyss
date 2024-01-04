package net.abyss.abyssmainplugin.Monsters;

import io.lumine.mythic.core.mobs.ActiveMob;
import net.abyss.abyssmainplugin.AbyssMainPlugin;
import net.abyss.abyssmainplugin.Gates.Gate;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public abstract class Monster
{
    protected AbyssMainPlugin plugin;
    protected Gate parentGate = null;
    protected ActiveMob mob;
    protected BukkitScheduler scheduler;
    protected BukkitTask task;
    protected boolean isOverloaded = false;

    public void Spawn(AbyssMainPlugin _plugin, Gate _gate, ActiveMob _newMob)
    {
        plugin = _plugin;
        parentGate = _gate;
        mob = _newMob;
        scheduler = plugin.getServer().getScheduler();
    }

    public abstract void Overload();

    public ActiveMob getMob()
    {
        return mob;
    }
    public Gate getParentGate()
    {
        return parentGate;
    }
    public boolean getOverloaded()
    {
        return isOverloaded;
    }

    public void Dead()
    {
        if(parentGate == null)
        {
            return;
        }
        parentGate.monsterDead(mob);
    }
}
