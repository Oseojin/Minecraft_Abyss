package net.abyss.abyssmainplugin.Manager;

import net.abyss.abyssmainplugin.AbyssMainPlugin;
import net.abyss.abyssmainplugin.Db.db_connect;
import net.abyss.abyssmainplugin.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
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

        if(db_connect.getInstance().insertMember(_player) == 0)
        {
            db_connect.getInstance().db_PlayerInfo(_player);

            playerData.loadPlayerEquipment();
            playerData.calcTotalStat();
        }
        else
        {
            _player.kick(Component.text().content("데이터베이스에서 정보를 로드중 오류가 발생 했습니다. " + db_connect.getInstance().insertMember(_player)).build());
        }
    }

    public void checkGameCodePlayer(Player player)
    {
        PlayerData playerData = playerDataHashMap.get(player);
        Bukkit.getConsoleSender().sendMessage(GameManager.getInstance().getGameCode());
        if(!playerData.getCode().equalsIgnoreCase(GameManager.getInstance().getGameCode()))
        {
            db_connect.getInstance().SetCode(player, GameManager.getInstance().getGameCode());
            db_connect.getInstance().SetStat(player, "health", 0);
            db_connect.getInstance().SetStat(player, "strength", 0);
            db_connect.getInstance().SetStat(player, "rapid", 0);
            db_connect.getInstance().SetStat(player, "accel", 0);
            db_connect.getInstance().SetStat(player, "intuition", 0);
            db_connect.getInstance().SetStat(player, "luck", 0);
            player.getInventory().clear();
            db_connect.getInstance().db_PlayerInfo(player);

            playerData.loadPlayerEquipment();
            playerData.calcTotalStat();
        }
    }

    public void removePlayer(Player _player)
    {
        playerDataHashMap.get(_player).takeOfPlayerEquipment();
        playerDataHashMap.remove(_player);
    }

    public int getPlayerNum()
    {
        int size = 0;
        for(Player player : playerDataHashMap.keySet())
        {
            if(playerDataHashMap.get(player).getIsLobby())
            {
                continue;
            }
            size++;
        }
        return size;
    }
    public int getAllPlayerStatSum()
    {
        int sum = 0;
        for(Player player : playerDataHashMap.keySet())
        {
            if(playerDataHashMap.get(player).getIsLobby())
            {
                continue;
            }
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
