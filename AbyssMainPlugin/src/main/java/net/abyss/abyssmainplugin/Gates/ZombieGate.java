package net.abyss.abyssmainplugin.Gates;

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

        gateDimensionLoc = new Location(Bukkit.getWorld("world"), 4991, 22, 5139);
        gateLv = GameManager.getInstance().getWorldLevel();
        isOverload = false;
        gateSize = 2;
        gateName = Component.text().color(TextColor.color(0, 0, 0)).content("좀비 게이트 lv." + gateLv).build();

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        int randomNum = random.nextInt((GameManager.getInstance().getWorldLevel() + 1));

        maxMonsterNum = PlayerManager.getInstance().getPlayerNum() * 20 + randomNum;
        spawnDelay= 60L;
        gateOverloadTime = 20L * 60L * 1L;
        scheduler = GateManager.getInstance().getPlugin().getServer().getScheduler();

        addSpawnLoc("NormalZombie", new Vector(-18, 9, 57));
        addSpawnLoc("NormalZombie", new Vector(24, 8, 90));
        addSpawnLoc("NormalZombie", new Vector(56, 14, 74));
        addSpawnLoc("NormalZombie", new Vector(-23, -3, 123));
        addSpawnLoc("NormalZombie", new Vector(-24, 1, 24));
        addSpawnLoc("NormalZombie", new Vector(27, 10, 43));
        addSpawnLoc("NormalZombie", new Vector(-48, -2, 72));
        addSpawnLoc("NormalZombie", new Vector(27, 5, 113));
        addBoss("InhancedZombie", new Vector(3, -2, 144));

        buildPortal();
    }
}
