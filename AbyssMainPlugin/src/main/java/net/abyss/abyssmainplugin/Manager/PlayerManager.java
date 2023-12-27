package net.abyss.abyssmainplugin.Manager;

import net.abyss.abyssmainplugin.AbyssMainPlugin;
import net.abyss.abyssmainplugin.PlayerData;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerManager
{
    private static PlayerManager instance;

    public static PlayerManager getInstance()
    {
        if(instance == null)
        {
            synchronized (PlayerManager.class)
            {
                instance = new PlayerManager();
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

    private final HashMap<Player, PlayerData> playerDataHashMap = new HashMap<>();
    private final int maxStatHealth = 100;
    private final int maxStatStrength = 100;
    private final int maxStatRapid = 100;
    private final int maxStatAccel = 100;
    private final int maxStatIntuition = 100;
    private final int maxStatLuck = 20;

    public void addPlayer(Player _player)
    {
        PlayerData playerData = new PlayerData();
        playerData.initData(_player);

        playerDataHashMap.put(_player, playerData);
    }
    public void removePlayer(Player _player)
    {
        playerDataHashMap.remove(_player);
    }
    public void resetGame()
    {
        for(Player player : playerDataHashMap.keySet())
        {
            playerDataHashMap.get(player).initData(player);
        }
    }
    public int getPlayerNum()
    {
        return playerDataHashMap.size();
    }
    public int getAllPlayerStatSum()
    {
        int sum = 0;
        for(Player player : playerDataHashMap.keySet())
        {
            sum += playerDataHashMap.get(player).getTotalStat();
        }

        return sum;
    }
    public PlayerData getPlayerData(Player player)
    {
        return playerDataHashMap.get(player);
    }

    public int getMaxStatHealth()
    {
        return maxStatHealth;
    }
    public int getMaxStatStrength()
    {
        return maxStatStrength;
    }
    public int getMaxStatRapid()
    {
        return maxStatRapid;
    }
    public int getMaxStatAccel()
    {
        return maxStatAccel;
    }
    public int getMaxStatIntuition()
    {
        return maxStatIntuition;
    }
    public int getMaxStatLuck()
    {
        return maxStatLuck;
    }
}
