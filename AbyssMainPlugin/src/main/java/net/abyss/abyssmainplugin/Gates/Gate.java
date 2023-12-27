package net.abyss.abyssmainplugin.Gates;

import io.lumine.mythic.api.adapters.AbstractLocation;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import net.abyss.abyssmainplugin.Manager.*;
import net.abyss.abyssmainplugin.Monsters.Monster;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.*;

public abstract class Gate
{
    protected Component gateName;
    protected int gateLv = 0;
    protected Location gateMainLoc, gateDimensionLoc;
    protected int gateSize;
    protected List<Material> gatePrevBlockList = new ArrayList<>();
    protected int gateIndex;
    protected HashMap<String, List<Location>> monsterMap = new HashMap<>();
    protected List<ActiveMob> mobList = new ArrayList<>();
    protected List<Player> gateInPlayerList = new ArrayList<>();
    protected int maxMonsterNum;
    protected int currMonsterNum = 0;
    protected long spawnDelay = 0;
    protected long gateOverloadTime = 0;
    protected boolean isOverload = false;
    protected boolean onGate = false;
    protected String bossName;
    protected ActiveMob bossMob;
    protected Location bossLoc;
    protected BukkitScheduler scheduler;
    protected BukkitTask spawnTask;
    protected BukkitTask overloadTask;
    // 플레이어 별 기여도 만들어야 함 나중에!!

    public abstract void Init(Location _gateInLoc);
    public void buildPortal()
    {
        onGate = true;
        for(int x = 0; x < gateSize; x++)
        {
            for(int y = 1; y <= gateSize; y++)
            {
                Location placeLoc = new Location(gateMainLoc.getWorld(), gateMainLoc.getX() + x, gateMainLoc.getY() + y, gateMainLoc.getZ());
                gatePrevBlockList.add(Bukkit.getWorld("world").getBlockAt(placeLoc).getType());
                Bukkit.getWorld("world").getBlockAt(placeLoc).setType(Material.NETHER_PORTAL);
            }
        }
    }
    public void closePortal()
    {
        onGate = false;
        for(int x = 0; x < gateSize; x++)
        {
            for(int y = 1; y <= gateSize; y++)
            {
                Location placeLoc = new Location(gateMainLoc.getWorld(), gateMainLoc.getX() + x, gateMainLoc.getY() + y, gateMainLoc.getZ());
                Bukkit.getWorld("world").getBlockAt(placeLoc).setType(gatePrevBlockList.get(x * gateSize + (y - 1)));
            }
        }
        gatePrevBlockList.clear();
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

    public void gateOverload()
    {
        AbstractLocation mainLoc = new AbstractLocation(bossMob.getEntity().getWorld(), gateMainLoc.getX(), gateMainLoc.getY(), gateMainLoc.getZ());

        overloadTask = scheduler.runTaskLater(GateManager.getInstance().getPlugin(), () ->
        {
            TextComponent titleMessage = Component.text().color(TextColor.color(255, 35, 19)).content("GATE OVERLOADED").build();
            TextComponent subTitleMessage = (TextComponent) gateName;
            TitleManager.getInstance().printTitle(titleMessage, subTitleMessage);

            outAllPlayer();
            GameManager.getInstance().changeWorldLevel();

            for(ActiveMob mob : mobList)
            {
                Monster monster = MonsterManager.getInstance().getMonster(mob);
                double changedHealth = (mob.getEntity().getMaxHealth() * 1.5) - mob.getEntity().getMaxHealth();
                mob.getEntity().setMaxHealth(mob.getEntity().getMaxHealth() * 1.5);
                mob.getEntity().setHealth(mob.getEntity().getHealth() + changedHealth);
                monster.Overload();
                mob.getEntity().teleport(mainLoc);
            }

            spawnTask.cancel();
            mobList.clear();
            GateManager.getInstance().deleteGate(this);
            isOverload = true;

            Bukkit.getScheduler().runTaskLater(GateManager.getInstance().getPlugin(), () ->
            {
                Monster bossMonster = MonsterManager.getInstance().getBoss(bossMob);
                double changedHealth = (bossMob.getEntity().getMaxHealth() * 1.2) - bossMob.getEntity().getMaxHealth();
                bossMob.getEntity().setMaxHealth(bossMob.getEntity().getMaxHealth() * 1.2);
                bossMob.getEntity().setHealth(bossMob.getEntity().getHealth() + changedHealth);
                bossMonster.Overload();
                bossMob.getEntity().teleport(mainLoc);
                closePortal();
            }, 20 * 300);
        }, 20 * gateOverloadTime);
    }

    public void initialSpawn()
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

                    Monster newMonster = MonsterManager.getInstance().getMonster(newMob);
                    newMonster.Spawn(GateManager.getInstance().getPlugin(), this, newMob);

                    mobList.add(newMob);
                    currMonsterNum++;
                }
            }
        }

        spawnRoutine();
    }

    public void spawnRoutine()
    {
        spawnTask = scheduler.runTaskTimer(GateManager.getInstance().getPlugin(), () ->
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

                            Monster newMonster = MonsterManager.getInstance().getMonster(newMob);
                            newMonster.Spawn(GateManager.getInstance().getPlugin(), this, newMob);

                            mobList.add(newMob);
                            currMonsterNum++;
                        }
                    }
                }
            }
        }, 20 * 0, 20 * spawnDelay);
    }
    public void spawnBoss()
    {
        int bossLv = gateLv + 5;

        MythicMob mythicBossMob = MythicBukkit.inst().getMobManager().getMythicMob(bossName).orElse(null);

        if(mythicBossMob != null)
        {
            // 보스 몹
            ActiveMob newBossMob = mythicBossMob.spawn(BukkitAdapter.adapt(bossLoc), bossLv);

            Monster newBossMonster = MonsterManager.getInstance().getBoss(newBossMob);
            newBossMonster.Spawn(GateManager.getInstance().getPlugin(), this, newBossMob);

            bossMob = newBossMob;
        }
    }

    public void monsterDead(ActiveMob mob)
    {
        if(mob.equals(bossMob) && !isOverload)
        {
            clearGate();
        }
        else if(mobList.contains(mob) && !isOverload)
        {
            currMonsterNum--;
            mobList.remove(mob);
        }
    }
    public void clearGate()
    {
        for(Player player : gateInPlayerList)
        {
            TextComponent titleMessage = Component.text().color(TextColor.color(255, 170, 93)).content("게이트 클리어").build();
            TextComponent subTitleMessage = (TextComponent) getGateName();
            TitleManager.getInstance().printTitleToPlayer(titleMessage, subTitleMessage, player);
        }
        // 기여도에 따라 보상
        spawnTask.cancel();
        overloadTask.cancel();
        GameManager.getInstance().changeWorldLevel();
        outAllPlayer();
        closePortal();
    }
    public void destroyGate()
    {
        bossMob.remove();
        spawnTask.cancel();
        overloadTask.cancel();
        for (ActiveMob mob : mobList)
        {
            mob.remove();
        }
        outAllPlayer();
        closePortal();
    }

    public void outAllPlayer()
    {
        for(Player player : gateInPlayerList)
        {
            player.teleport(gateMainLoc);
        }
    }

    public void playerEnterGate(Player player)
    {
        gateInPlayerList.add(player);
    }
    public void playerExitGate(Player player)
    {
        gateInPlayerList.remove(player);
    }

    public Component getGateName()
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
    public int getGateIndex()
    {
        return gateIndex;
    }
    public void setGateIndex(int _gateIndex)
    {
        gateIndex = _gateIndex;
    }
}
