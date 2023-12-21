package net.abyss.abyssmainplugin.Gates;

import net.abyss.abyssmainplugin.AbyssMainPlugin;
import net.abyss.abyssmainplugin.Manager.GateManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class GateBase
{
    enum GATE_TYPE
    {
        ZOMBIE,
        Skeleton
    }
    static GateManager gateManager = GateManager.getInstance();
    static AbyssMainPlugin plugin = gateManager.getPlugin();
    static World world = plugin.getServer().getWorld("world");
    int gateLv = 1;
    GATE_TYPE gateType;
    Location centerLoc = null;
    List<Wave> waveList = new ArrayList<Wave>();
    Material originBlock;
    public void initGate(Vector vec, Integer lv, Integer type)
    {

    }
    public void startWave()
    {
        if(waveList.isEmpty())
        {
            TextComponent message = Component.text().color(TextColor.color(255,0,0)).content("게이트에 몬스터 정보가 없습니다!!").build();
            plugin.getServer().getConsoleSender().sendMessage(message);
        }

        TextComponent message = Component.text().color(TextColor.color(255,255,0)).content("몬스터가 몰려옵니다!!").build();
        plugin.getServer().getConsoleSender().sendMessage(message);

        for(int i = 1; i <= waveList.get(0).getIndexNum(0); i++)
        {
            world.spawnEntity(centerLoc, waveList.get(0).getIndexType(0));
        }
    }
    public Location getLocation()
    {
        return centerLoc;
    }
    public Integer getGateLv()
    {
        return gateLv;
    }
    public GATE_TYPE getType()
    {
        return gateType;
    }
    public Material getOriginBlock()
    {
        return originBlock;
    }
}