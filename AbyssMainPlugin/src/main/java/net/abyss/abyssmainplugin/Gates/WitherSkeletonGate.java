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

public class WitherSkeletonGate extends Gate
{
    @Override
    public void Init(Location _gateMainLoc)
    {
        gateMainLoc = _gateMainLoc;

        gateDimensionLoc = new Location(Bukkit.getWorld("world"), -681, -60, -21);
        gateLv = GameManager.getInstance().getWorldLevel();
        isOverload = false;
        gateSize = 4;
        gateName = Component.text().color(TextColor.color(0, 0, 0)).content("검은 스켈레톤 게이트 lv." + gateLv).build();

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        int randomNum = random.nextInt((GameManager.getInstance().getWorldLevel() + 1));

        maxMonsterNum = PlayerManager.getInstance().getPlayerNum() * 5 + randomNum;
        spawnDelay= 60L;
        gateOverloadTime = 20L * 60L * 30L;
        scheduler = GateManager.getInstance().getPlugin().getServer().getScheduler();

        addSpawnLoc("SkeletalKnight", new Vector(8, 0, 10));
        addSpawnLoc("SkeletalKnight", new Vector(9, 0, 14));
        addSpawnLoc("SkeletalKnight", new Vector(23, 0, 5));
        addSpawnLoc("SkeletalKnight", new Vector(26, 0, 7));
        addBoss("SkeletonKing", new Vector(0, 0, 25));

        buildPortal();
    }
}
