package net.abyss.abyssmainplugin.Manager;

import net.abyss.abyssmainplugin.AbyssMainPlugin;
import net.abyss.abyssmainplugin.Gates.Gate;
import net.abyss.abyssmainplugin.Gates.WitherSkeletonGate;
import net.abyss.abyssmainplugin.Gates.ZombieGate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
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


    private final List<Gate> gateList = new ArrayList<>();

    public void Init()
    {
        gateList.add(new ZombieGate());
        gateList.add(new WitherSkeletonGate());
    }

    public void generateGate(int _gate, Vector _mainVec)
    {
        Location mainLoc = new Location(plugin.getServer().getWorld("world"), _mainVec.getX(), _mainVec.getY(), _mainVec.getZ());
        gateList.get(_gate).Init(mainLoc);
        gateList.get(_gate).initialSpawn();
        gateList.get(_gate).spawnBoss();
        gateList.get(_gate).gateOverload();
    }
    public void destroyGate(int _index)
    {
        gateList.get(_index).destroyGate();
    }
    public void clearGate(Gate _gate)
    {

    }
    public void deleteGate(Gate _gate)
    {
        gateList.remove(_gate);
    }

    public Gate getIndexGate(Integer index)
    {
        return gateList.get(index);
    }
    public List<Gate> getGateList()
    {
        return gateList;
    }
}
