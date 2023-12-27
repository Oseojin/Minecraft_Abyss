package net.abyss.abyssmainplugin.Gates;

import net.abyss.abyssmainplugin.Manager.CentralTower;
import net.abyss.abyssmainplugin.Manager.GameManager;
import net.abyss.abyssmainplugin.Manager.GateManager;
import net.abyss.abyssmainplugin.Manager.PlayerManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Random;

public class ZombieGate extends Gate
{
    @Override
    public void Init(Location _gateMainLoc)
    {
        gateMainLoc = _gateMainLoc;

        gateDimensionLoc = new Location(Bukkit.getWorld("world"), -531, 65, -40);
        gateLv = GameManager.getInstance().getWorldLevel();
        isOverload = false;
        gateSize = 2;
        gateName = Component.text().color(TextColor.color(0, 0, 0)).content("좀비 게이트 lv." + gateLv).build();

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        int randomNum = random.nextInt(GameManager.getInstance().getWorldLevel());

        maxMonsterNum = PlayerManager.getInstance().getPlayerNum() * 5 + randomNum;
        spawnDelay= 60L;
        gateOverloadTime = 20L * 60L * 15L;
        scheduler = GateManager.getInstance().getPlugin().getServer().getScheduler();

        addSpawnLoc("NormalZombie", new Vector(8, 0, 10));
        addSpawnLoc("NormalZombie", new Vector(9, 0, -14));
        addSpawnLoc("NormalZombie", new Vector(23, 0, 5));
        addSpawnLoc("NormalZombie", new Vector(26, 0, -7));
        addBoss("InhancedZombie", new Vector(25, 0, 0));

        buildPortal();
    }
}
