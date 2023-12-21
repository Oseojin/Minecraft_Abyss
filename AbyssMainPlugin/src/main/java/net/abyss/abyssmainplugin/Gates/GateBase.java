package net.abyss.abyssmainplugin.Gates;

import net.abyss.abyssmainplugin.AbyssMainPlugin;
import net.abyss.abyssmainplugin.Manager.GateManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
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

        waveControl();
    }
    public void waveControl()
    {
        plugin.getServer().getConsoleSender().sendMessage(""+ waveList.size());

        for(int waveCnt = 0; waveCnt < waveList.size(); waveCnt++)
        {
            Wave currWave = waveList.get(waveCnt);
            int delay;
            if(waveCnt == 0)
            {
                delay = 0;
            }
            else
            {
                delay = waveList.get(waveCnt-1).getDuration();
            }
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    // wave 안에 몬스터 종류만큼 반복
                    for (int typeCnt = 0; typeCnt < currWave.getTypeNum(); typeCnt++)
                    {
                        // 해당 몬스터 마릿수 만큼 반복
                        for (int monsterCnt = 0; monsterCnt < currWave.getIndexNum(typeCnt); monsterCnt++)
                        {
                            // 소환
                            world.spawnEntity(centerLoc, currWave.getIndexType(typeCnt));
                        }
                    }
                }
            }.runTaskLater(plugin, 20 * delay);
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