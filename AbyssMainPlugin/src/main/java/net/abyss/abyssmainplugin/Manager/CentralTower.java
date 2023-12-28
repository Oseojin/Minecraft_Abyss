package net.abyss.abyssmainplugin.Manager;

import io.lumine.mythic.core.mobs.ActiveMob;
import net.abyss.abyssmainplugin.AbyssMainPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class CentralTower
{
    private static CentralTower instance;

    public static CentralTower getInstance()
    {
        if(instance == null)
        {
            synchronized (CentralTower.class)
            {
                instance = new CentralTower();
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

    private double maxHP;
    private double currHP;
    private int towerLevel;
    private final Location centerLoc = new Location(plugin.getServer().getWorld("world"), 0, -60, 0);

    public void Init()
    {
        maxHP = 100000;
        currHP = maxHP;
        towerLevel = 1;
    }
    public void levelUp()
    {
        towerLevel++;
        maxHP += (maxHP * 0.1);
    }
    public void getDamage(double amount)
    {
        currHP -= amount;
        if(currHP <= 0)
        {
            currHP = 0;
            GameManager.getInstance().GameEnd();
        }
        Bukkit.getConsoleSender().sendMessage(currHP + "");
    }
    public void getHeal(double amount)
    {
        currHP += amount;
        if(currHP > maxHP)
        {
            currHP = maxHP;
        }
    }
    public Location getCenterLoc()
    {
        return centerLoc;
    }
}
