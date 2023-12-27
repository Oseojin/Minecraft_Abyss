package net.abyss.abyssmainplugin.Monsters;

import net.abyss.abyssmainplugin.Manager.CentralTower;
import org.bukkit.Bukkit;
import org.bukkit.entity.Zombie;

public class MonsterTypeZombie extends Monster
{
    private Zombie entity;
    @Override
    public void Overload()
    {
        isOverloaded = true;
        task = scheduler.runTaskTimer(plugin, () ->
        {
            entity = (Zombie) mob.getEntity().getBukkitEntity();
            Bukkit.getMobGoals().removeAllGoals(entity);
            entity.getPathfinder().moveTo(CentralTower.getInstance().getCenterLoc(), 1);

            if(entity.getLocation().distance(CentralTower.getInstance().getCenterLoc()) <= 10)
            {
                CentralTower.getInstance().getDamage(entity.getHealth());
                mob.remove();
                task.cancel();
            }
        }, 20 * 1, 20 * 1);
    }
}
