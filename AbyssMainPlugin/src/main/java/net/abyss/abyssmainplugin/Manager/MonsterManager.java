package net.abyss.abyssmainplugin.Manager;

import io.lumine.mythic.core.mobs.ActiveMob;
import net.abyss.abyssmainplugin.AbyssMainPlugin;
import net.abyss.abyssmainplugin.Monsters.Monster;
import net.abyss.abyssmainplugin.Monsters.MonsterTypeSkeleton;
import net.abyss.abyssmainplugin.Monsters.MonsterTypeWitherSkeleton;
import net.abyss.abyssmainplugin.Monsters.MonsterTypeZombie;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

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



    private final HashMap<ActiveMob, Monster> monsterHashMap = new HashMap<>();
    private final HashMap<Entity, Monster> monsterEntityHashMap = new HashMap<>();
    private final HashMap<ActiveMob, Monster> bossHashMap = new HashMap<>();
    private final HashMap<Entity, Monster> bossEntityHashMap = new HashMap<>();

    public Monster findMonsterType(EntityType type)
    {
        switch(type)
        {
            case ZOMBIE:
                return new MonsterTypeZombie();
            case SKELETON:
                return new MonsterTypeSkeleton();
            case WITHER_SKELETON:
                return new MonsterTypeWitherSkeleton();
            default:
                return null;
        }
    }
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
    public void addBoss(ActiveMob _mob, Monster _bossMonster)
    {
        bossHashMap.put(_mob, _bossMonster);
        bossEntityHashMap.put(_mob.getEntity().getBukkitEntity(), _bossMonster);
    }
    public Monster getBoss(ActiveMob _mob)
    {
        if(!bossHashMap.containsKey(_mob))
        {
            return null;
        }
        return bossHashMap.get(_mob);
    }
    public Monster getBossByEntity(Entity _mob)
    {
        if(!bossEntityHashMap.containsKey(_mob))
        {
            return null;
        }
        return bossEntityHashMap.get(_mob);
    }
    public void monsterDead(ActiveMob _mob)
    {
        if(monsterHashMap.containsKey(_mob))
        {
            monsterHashMap.get(_mob).Dead();
        }
        else if(bossHashMap.containsKey(_mob))
        {
            bossHashMap.get(_mob).Dead();
        }
    }
}
