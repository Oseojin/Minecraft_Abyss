package net.abyss.abyssmainplugin.Manager;

import net.abyss.abyssmainplugin.AbyssMainPlugin;
import net.abyss.abyssmainplugin.Gates.Gate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
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


    private static List<Gate> gateList = new ArrayList<>();
    public void generateGate(Gate _gate, String _gateName, Vector _mainVec, Vector _dimensionVec, Integer _lv, Integer _maxNum)
    {
        Location mainLoc = new Location(plugin.getServer().getWorld("world"), _mainVec.getX(), _mainVec.getY(), _mainVec.getZ());
        Location dimensionLoc = new Location(plugin.getServer().getWorld("world"), _dimensionVec.getX(), _dimensionVec.getY(), _dimensionVec.getZ());
        _gate.Init(_gateName, mainLoc, dimensionLoc, _lv, _maxNum);
        gateList.add(_gate);
        _gate.spawnRoutine();
        _gate.spawnBoss();
    }
    public void destroyGate(int _index)
    {
        gateList.get(_index).destroyGate();
        gateList.remove(_index);
    }
    public void clearGate(Gate _gate)
    {
        gateList.remove(_gate);

        TextComponent titleMessage = Component.text().color(TextColor.color(255, 170, 93)).content("게이트 클리어").build();
        TextComponent subTitleMessage = Component.text().color(TextColor.color(24, 24, 27)).content("" + _gate.getGateName()).build();
        TitleManager.getInstance().printTitle(titleMessage, subTitleMessage);
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
