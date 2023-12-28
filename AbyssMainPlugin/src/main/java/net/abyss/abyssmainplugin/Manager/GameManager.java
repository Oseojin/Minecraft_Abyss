package net.abyss.abyssmainplugin.Manager;

import net.abyss.abyssmainplugin.AbyssMainPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.Random;

public class GameManager
{
    private static GameManager instance;

    public static GameManager getInstance()
    {
        if(instance == null)
        {
            synchronized (GameManager.class)
            {
                instance = new GameManager();
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

    private BukkitTask mainTask;

    private static Location startLoc = new Location(Bukkit.getServer().getWorld("world"), 250, -60, 250);
    private static Location endLoc = new Location(Bukkit.getServer().getWorld("world"), -250, -60, -250);

    private static int worldLevel = 1;
    private boolean hardWorld = false;
    private boolean isStart = false;
    private String gameCode = "";
    private static Location blockLoc = new Location(Bukkit.getWorld("world"), 5000, -58, 5000);

    public void blockActive()
    {
        blockLoc.getBlock().setType(Material.CRYING_OBSIDIAN);
    }
    public void blockDeactive()
    {
        blockLoc.getBlock().setType(Material.OBSIDIAN);
    }

    public Location getStartLoc()
    {
        return startLoc;
    }
    public Location getEndLoc()
    {
        return endLoc;
    }
    public int getWorldLevel()
    {
        return worldLevel;
    }
    public boolean getStart()
    {
        return isStart;
    }

    public void Init()
    {
        blockDeactive();
    }

    public void GameStart()
    {
        blockActive();
        setGameCode();
        Reset();
        GateRoutine();
        isStart = true;
    }
    public void GameEnd()
    {
        blockDeactive();
        mainTask.cancel();
        isStart = false;
        Reset();
    }

    public void changeWorldLevel()
    {
        worldLevel = PlayerManager.getInstance().getAllPlayerStatSum() / (PlayerManager.getInstance().getPlayerNum() + 1);
        if(worldLevel >= 100) // 유저 평균 스텟 총합이 100 이상이면 하드모드 시작
        {
            hardWorld = true;
            TextComponent titleMessage = Component.text().color(TextColor.color(17, 0, 2)).content("ENABLE HARDWORLD").build();
            TextComponent subTitleMessage = Component.text().color(TextColor.color(96, 5, 5)).content("게이트의 개수가 증가합니다.").build();
            TitleManager.getInstance().printTitle(titleMessage, subTitleMessage);
        }
    }

    public void GateRoutine()
    {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        mainTask = Bukkit.getScheduler().runTaskTimer(plugin, () ->
        {
            if(hardWorld && GateManager.getInstance().getGateNum() < GateManager.getInstance().getGateList().size())
            {
                int randomNum = random.nextInt(GateManager.getInstance().getGateList().size());
                for(int i = 0; i < randomNum; i++)
                {
                    GateSet();
                }
            }
            if(GateManager.getInstance().getGateNum() == 0)
            {
                GateSet();
            }

        }, 20 * 60 * 1, 20 * 60 * 20 / worldLevel);
    }

    public void GateSet()
    {
        Random randomPos = new Random();
        randomPos.setSeed(System.currentTimeMillis());

        double x = randomPos.nextInt((int) startLoc.getX() - (int) endLoc.getX() + 1) + endLoc.getX();
        double y = startLoc.getY();
        double z = randomPos.nextInt((int) startLoc.getZ() - (int) endLoc.getZ() + 1) + endLoc.getZ();

        Random randomGate = new Random();
        randomGate.setSeed(System.currentTimeMillis());
        int gateCase = randomGate.nextInt(GateManager.getInstance().getGateList().size());
        while(GateManager.getInstance().getIndexGate(gateCase).getOnGate())
        {
            gateCase = randomGate.nextInt(GateManager.getInstance().getGateList().size());
        }

        Vector mainVec = new Vector(x, y, z);

        GateManager.getInstance().generateGate(1, mainVec);
    }

    public void setGameCode()
    {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        gameCode = "" + random.nextInt(Integer.MAX_VALUE);
    }

    public void Reset()
    {
        worldLevel = 1;
        hardWorld = false;
        CentralTower.getInstance().Init();
        GateManager.getInstance().Init();
    }

    public String getGameCode()
    {
        return gameCode;
    }
}
