package net.abyss.abyssmainplugin.Gates;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Interaction;

import java.util.ArrayList;
import java.util.List;

public class Wave
{
    private int duration;
    private List<EntityType> monsterTypeList = new ArrayList<EntityType>();
    private List<Integer> monsterNumList = new ArrayList<Integer>();

    public void setDuration(int _duration)
    {
        duration = _duration;
    }

    public void addMonster(EntityType type, Integer num)
    {
        monsterTypeList.add(type);
        monsterNumList.add(num);
    }

    public Integer getTypeNum()
    {
        return monsterTypeList.size();
    }

    public Integer getDuration()
    {
        return duration;
    }

    public Integer getNumNum()
    {
        return monsterNumList.size();
    }

    public EntityType getIndexType(Integer index)
    {
        return monsterTypeList.get(index);
    }

    public Integer getIndexNum(Integer index)
    {
        return monsterNumList.get(index);
    }
}
