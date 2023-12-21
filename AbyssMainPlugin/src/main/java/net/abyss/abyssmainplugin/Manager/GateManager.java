package net.abyss.abyssmainplugin.Manager;

import net.abyss.abyssmainplugin.AbyssMainPlugin;
import net.abyss.abyssmainplugin.Gates.GateBase;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GateManager
{
    private static GateManager instance;

    public static GateManager getInstance()
    {
        if(instance == null)
        {
            synchronized (GateManager.class)
            {
                instance = new GateManager();
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


    static List<GateBase> gateList = new ArrayList<GateBase>();
    public void generateGate(GateBase gate, Vector vec, Integer lv,Integer type)
    {
        gate.initGate(vec, lv, type);
        gateList.add(gate);
    }
    public void destroyGate(int index)
    {
        Material originBlock = gateList.get(index).getOriginBlock();
        gateList.get(index).getLocation().getBlock().setType(originBlock);
        gateList.remove(index);
    }
    public GateBase getIndexGate(Integer index)
    {
        return gateList.get(index);
    }
    public List<GateBase> getGateList()
    {
        return gateList;
    }
}
