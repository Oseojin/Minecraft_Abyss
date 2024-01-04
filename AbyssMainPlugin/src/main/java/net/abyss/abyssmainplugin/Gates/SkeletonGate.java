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

public class SkeletonGate extends Gate
{
    @Override
    public void Init(Location _gateMainLoc)
    {
        gateMainLoc = _gateMainLoc;

        gateDimensionLoc = new Location(Bukkit.getWorld("world"), 4982, 94, 4963);
        gateLv = GameManager.getInstance().getWorldLevel();
        isOverload = false;
        gateSize = 4;
        gateName = Component.text().color(TextColor.color(0, 0, 0)).content("스켈레톤 게이트 lv." + gateLv).build();

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        int randomNum = random.nextInt((GameManager.getInstance().getWorldLevel() + 1));

        maxMonsterNum = PlayerManager.getInstance().getPlayerNum() * 10 + randomNum;
        spawnDelay= 60L;
        gateOverloadTime = 20L * 60L * 1L;
        scheduler = GateManager.getInstance().getPlugin().getServer().getScheduler();

        addSpawnLoc("SkeletalKnight", new Vector(-7.0, 0.0, 24.0));
        addSpawnLoc("SkeletalKnight", new Vector(13.0, 4.0, 14.0));
        addSpawnLoc("SkeletalKnight", new Vector(32.0, 7.0, 4.0));
        addSpawnLoc("SkeletalKnight", new Vector(30.0, 9.0, 26.0));
        addSpawnLoc("SkeletalKnight", new Vector(40.0, 17.0, 52.0));
        addSpawnLoc("SkeletalKnight", new Vector(23.0, 9.0, 47.0));
        addSpawnLoc("SkeletalKnight", new Vector(8.0, 4.0, 41.0));
        addSpawnLoc("SkeletalKnight", new Vector(6.0, 4.0, 58.0));
        addBoss("SkeletonKing", new Vector(20.0, 7.0, 66.0));

        buildPortal();
    }
}
