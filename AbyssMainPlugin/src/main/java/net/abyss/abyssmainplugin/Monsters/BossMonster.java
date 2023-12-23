package net.abyss.abyssmainplugin.Monsters;

import io.lumine.mythic.core.mobs.ActiveMob;
import net.abyss.abyssmainplugin.AbyssMainPlugin;
import net.abyss.abyssmainplugin.Gates.Gate;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class BossMonster
{
    private AbyssMainPlugin plugin;
    private Gate parentGate;
    private ActiveMob mob;
    private BukkitScheduler scheduler;
    private BukkitTask task;

    public void Spawn(AbyssMainPlugin _plugin, Gate _gate, ActiveMob _newMob)
    {
        plugin = _plugin;
        parentGate = _gate;
        mob = _newMob;
        scheduler = plugin.getServer().getScheduler();

        behaviorController();
    }

    public void behaviorController()
    {
        task = scheduler.runTaskLater(plugin, () ->
        {
            // Mythic Mob에서 Pathfinder 타입 검사하고 플레이어 따라가는 중 아니면 변경

            /*Bukkit.getMobGoals().removeAllGoals(entity);
            Location loc = new Location(entity.getWorld(), defenceCenterLoc.getX(), defenceCenterLoc.getY(), defenceCenterLoc.getZ());
            entity.getPathfinder().moveTo(loc, moveSpeed);
            Pathfinder.PathResult pathResult = entity.getPathfinder().getCurrentPath();
            if(pathResult != null)
            {
                //plugin.getServer().getConsoleSender().sendMessage(pathResult.getFinalPoint() + "");
            }*/
        }, 5L);
    }

    public void Dead()
    {
        task.cancel();
        parentGate.monsterDead(mob);
    }
}
