package net.abyss.abyssmainplugin.Gates;

import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class Wave
{
    private List<EntityType> monsterTypeList = new ArrayList<EntityType>();
    private List<Integer> monsterNumList = new ArrayList<Integer>();

    public void addMonster(EntityType type, Integer num)
    {
        monsterTypeList.add(type);
        monsterNumList.add(num);
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
