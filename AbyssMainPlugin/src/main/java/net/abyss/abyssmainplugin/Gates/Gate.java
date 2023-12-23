package net.abyss.abyssmainplugin.Gates;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.api.skills.placeholders.PlaceholderDouble;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import net.abyss.abyssmainplugin.Manager.GateManager;
import net.abyss.abyssmainplugin.Manager.MonsterManager;
import net.abyss.abyssmainplugin.Monsters.BossMonster;
import net.abyss.abyssmainplugin.Monsters.Monster;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.*;

public abstract class Gate
{
    protected String gateName;
    protected int gateLv = 0;
    protected Location gateMainLoc, gateDimensionLoc;
    protected int gateSize;
    protected HashMap<String, List<Location>> monsterMap = new HashMap<>();
    protected List<ActiveMob> mobList = new ArrayList<>();
    protected int maxMonsterNum;
    protected int currMonsterNum = 0;
    protected String bossName;
    protected ActiveMob bossMob;
    protected Location bossLoc;
    protected BukkitScheduler scheduler;
    protected BukkitTask task;
    // 플레이어 별 기여도 만들어야 함 나중에!!

    public abstract void Init(String _gateName, Location _gateInLoc, Location _gateOutLoc, Integer _gateLv, Integer _maxMonsterNum);
    {

    }

    public void addSpawnLoc(String _monsterName, Vector _vec)
    {
        Location spawnLoc = new Location(gateDimensionLoc.getWorld(), gateDimensionLoc.getX() + _vec.getX(), gateDimensionLoc.getY() + _vec.getY(), gateDimensionLoc.getZ() + _vec.getZ());
        if(monsterMap.containsKey(_monsterName))
        {
            monsterMap.get(_monsterName).add(spawnLoc);
        }
        else
        {
            monsterMap.put(_monsterName, new ArrayList<>());
            monsterMap.get(_monsterName).add(spawnLoc);
        }
    }
    public void addBoss(String _bossName, Vector _vec)
    {
        bossLoc = new Location(gateDimensionLoc.getWorld(), gateDimensionLoc.getX() + _vec.getX(), gateDimensionLoc.getY() + _vec.getY(), gateDimensionLoc.getZ() + _vec.getZ());
        bossName = _bossName;
    }
    public void spawnRoutine()
    {
        task = scheduler.runTaskTimer(GateManager.getInstance().getPlugin(), () ->
        {
            if(currMonsterNum <= maxMonsterNum)
            {
                for(String monsterType : monsterMap.keySet())
                {
                    if(currMonsterNum >= maxMonsterNum)
                    {
                        break;
                    }
                    for(Location spawnLoc : monsterMap.get(monsterType))
                    {
                        if(currMonsterNum >= maxMonsterNum)
                        {
                            break;
                        }
                        MythicMob mythicMob = MythicBukkit.inst().getMobManager().getMythicMob(monsterType).orElse(null);

                        if(mythicMob != null)
                        {
                            ActiveMob newMob = mythicMob.spawn(BukkitAdapter.adapt(spawnLoc), gateLv);

                            Monster newMonster = new Monster();
                            newMonster.Spawn(GateManager.getInstance().getPlugin(), this, newMob);

                            MonsterManager.getInstance().addMonster(newMob, newMonster);

                            mobList.add(newMob);
                            currMonsterNum++;
                        }
                    }
                }
            }
        }, 20 * 0, 20 * 5);
    }
    public void spawnBoss()
    {
        int bossLv = gateLv + 5;

        MythicMob mythicBossMob = MythicBukkit.inst().getMobManager().getMythicMob(bossName).orElse(null);

        if(mythicBossMob != null)
        {
            // 보스 몹
            ActiveMob newBossMob = mythicBossMob.spawn(BukkitAdapter.adapt(bossLoc), bossLv);

            Bukkit.getServer().getConsoleSender().sendMessage("perLevelHealth: " + mythicBossMob.getPerLevelHealth());
            Bukkit.getServer().getConsoleSender().sendMessage("currHealth: " + newBossMob.getEntity().getMaxHealth());
            Bukkit.getServer().getConsoleSender().sendMessage("currDamage: " + newBossMob.getDamage());

            BossMonster newBossMonster = new BossMonster();
            newBossMonster.Spawn(GateManager.getInstance().getPlugin(), this, newBossMob);

            MonsterManager.getInstance().addBoss(newBossMob, newBossMonster);

            bossMob = newBossMob;
        }
    }

    public ActiveMob monsterDead(ActiveMob mob)
    {
        if(mob.equals(bossMob))
        {
            clearGate();
            return mob;
        }
        else if(mobList.contains(mob))
        {
            currMonsterNum--;
            mobList.remove(mob);
            return mob;
        }
        return null;
    }
    public void clearGate()
    {
        // 기여도에 따라 보상
        task.cancel();
        GateManager.getInstance().clearGate(this);
    }
    public void destroyGate()
    {
        bossMob.remove();
        task.cancel();
        for (ActiveMob mob : mobList)
        {
            mob.remove();
        }
    }

    public String getGateName()
    {
        return gateName;
    }
    public Location getGateMainLoc()
    {
        return gateMainLoc;
    }
    public Location getGateDimensionLoc()
    {
        return gateDimensionLoc;
    }
    public Integer getGateLevel()
    {
        return gateLv;
    }
}
