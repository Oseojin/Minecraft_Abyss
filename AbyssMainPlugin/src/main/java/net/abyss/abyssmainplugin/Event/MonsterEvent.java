package net.abyss.abyssmainplugin.Event;

import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import io.lumine.mythic.bukkit.events.MythicMobSpawnEvent;
import io.lumine.mythic.core.mobs.ActiveMob;
import net.abyss.abyssmainplugin.Manager.MonsterManager;
import net.abyss.abyssmainplugin.Monsters.Monster;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MonsterEvent implements Listener
{
    @EventHandler
    public void MobDeath(MythicMobDeathEvent event)
    {
        ActiveMob targetMob = event.getMob();

        Monster monster = MonsterManager.getInstance().getMonster(targetMob);

        if(targetMob.getFaction().equals("Boss"))
        {
            monster = MonsterManager.getInstance().getBoss(targetMob);
            Bukkit.getConsoleSender().sendMessage("보스 사망");
        }
        else {
            Bukkit.getConsoleSender().sendMessage("몹 사망");
        }

        if(monster == null)
        {
            Bukkit.getConsoleSender().sendMessage("널임!");
            return;
        }

        if(monster.getOverloaded())
        {
            event.getDrops().clear();
        }

        Bukkit.getConsoleSender().sendMessage("널 아님!");
        MonsterManager.getInstance().monsterDead(targetMob);
    }

    @EventHandler
    public void MobSpawn(MythicMobSpawnEvent event)
    {
        ActiveMob targetMob = event.getMob();
        Monster monster = MonsterManager.getInstance().findMonsterType(targetMob.getEntity().getBukkitEntity().getType());
        if(targetMob.getFaction().equals("Boss"))
        {
            Bukkit.getConsoleSender().sendMessage("보스 소환 이벤트!");
            MonsterManager.getInstance().addBoss(targetMob, monster);
        }
        else
        {
            MonsterManager.getInstance().addMonster(targetMob, monster);
        }
    }
}
