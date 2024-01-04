package net.abyss.abyssmainplugin.Manager;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import net.abyss.abyssmainplugin.AbyssMainPlugin;
import net.abyss.abyssmainplugin.Monsters.Monster;
import net.abyss.abyssmainplugin.Monsters.MonsterTypeSkeleton;
import net.abyss.abyssmainplugin.Monsters.MonsterTypeWitherSkeleton;
import net.abyss.abyssmainplugin.Monsters.MonsterTypeZombie;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.UUID;

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



    private final HashMap<String, Monster> monsterHashMap = new HashMap<>();
    private final HashMap<Entity, Monster> monsterEntityHashMap = new HashMap<>();
    private final HashMap<String, Monster> bossHashMap = new HashMap<>();
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
        monsterHashMap.put(_mob.getUniqueId().toString(), _monster);
        monsterEntityHashMap.put(_mob.getEntity().getBukkitEntity(), _monster);
    }
    public Monster getMonster(AbstractEntity _mob)
    {
        ActiveMob activeMob = new ActiveMob(_mob);

        if(!monsterHashMap.containsKey(activeMob.getUniqueId().toString()))
        {
            return null;
        }
        return monsterHashMap.get(activeMob.getUniqueId().toString());
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
        bossHashMap.put(_mob.getUniqueId().toString(), _bossMonster);
        bossEntityHashMap.put(_mob.getEntity().getBukkitEntity(), _bossMonster);
    }
    public Monster getBoss(ActiveMob _mob)
    {
        if(!bossHashMap.containsKey(_mob.getUniqueId().toString()))
        {
            return null;
        }
        return bossHashMap.get(_mob.getUniqueId().toString());
    }
    public Monster getBossByEntity(Entity _mob)
    {
        if(!bossEntityHashMap.containsKey(_mob))
        {
            return null;
        }
        return bossEntityHashMap.get(_mob);
    }
    public void monsterDead(String uuid)
    {
        if(monsterHashMap.containsKey(uuid))
        {
            monsterHashMap.get(uuid).Dead();
        }
        else if(bossHashMap.containsKey(uuid))
        {
            bossHashMap.get(uuid).Dead();
        }
    }
}
