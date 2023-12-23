package net.abyss.abyssmainplugin.Manager;

import io.lumine.mythic.core.mobs.ActiveMob;
import net.abyss.abyssmainplugin.AbyssMainPlugin;
import net.abyss.abyssmainplugin.Monsters.BossMonster;
import net.abyss.abyssmainplugin.Monsters.Monster;
import org.bukkit.entity.Entity;

import java.util.HashMap;

public class MonsterManager
{
    private static MonsterManager instance;

    public static MonsterManager getInstance()
    {
        if(instance == null)
        {
            synchronized (MonsterManager.class)
            {
                instance = new MonsterManager();
            }
        }

        return instance;
    }
    private static AbyssMainPlugin plugin;
    public static void setPlugin(AbyssMainPlugin MainPlugin)
    {
        plugin = MainPlugin;
    }
    public AbyssMainPlugin getPlugin()
    {
        return plugin;
    }



    private HashMap<ActiveMob, Monster> monsterHashMap = new HashMap<>();
    private HashMap<Entity, Monster> monsterEntityHashMap = new HashMap<>();
    private HashMap<ActiveMob, BossMonster> bossHashMap = new HashMap<>();
    private HashMap<Entity, BossMonster> bossEntityHashMap = new HashMap<>();
    public void addMonster(ActiveMob _mob, Monster _monster)
    {
        monsterHashMap.put(_mob, _monster);
        monsterEntityHashMap.put(_mob.getEntity().getBukkitEntity(), _monster);
    }
    public Monster getMonster(ActiveMob _mob)
    {
        if(!monsterHashMap.containsKey(_mob))
        {
            return null;
        }
        return monsterHashMap.get(_mob);
    }
    public Monster getMonsterByEntity(Entity _mob)
    {
        if(!monsterEntityHashMap.containsKey(_mob))
        {
            return null;
        }
        return monsterEntityHashMap.get(_mob);
    }
    public void addBoss(ActiveMob _mob, BossMonster _bossMonster)
    {
        bossHashMap.put(_mob, _bossMonster);
        bossEntityHashMap.put(_mob.getEntity().getBukkitEntity(), _bossMonster);
    }
    public BossMonster getBoss(ActiveMob _mob)
    {
        if(!bossHashMap.containsKey(_mob))
        {
            return null;
        }
        return bossHashMap.get(_mob);
    }
    public BossMonster getBossByEntity(Entity _mob)
    {
        if(!bossEntityHashMap.containsKey(_mob))
        {
            return null;
        }
        return bossEntityHashMap.get(_mob);
    }
    public ActiveMob monsterDead(ActiveMob _mob)
    {
        if(monsterHashMap.containsKey(_mob))
        {
            monsterHashMap.get(_mob).Dead();
            return _mob;
        }
        else if(bossHashMap.containsKey(_mob))
        {
            bossHashMap.get(_mob).Dead();
            return _mob;
        }

        return null;
    }
}
