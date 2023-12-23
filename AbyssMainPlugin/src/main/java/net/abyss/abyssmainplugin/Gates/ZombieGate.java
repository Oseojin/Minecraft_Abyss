package net.abyss.abyssmainplugin.Gates;

import net.abyss.abyssmainplugin.Manager.GateManager;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ZombieGate extends Gate
{
    @Override
    public void Init(String _gateName, Location _gateMainLoc, Location _gateDimensionLoc, Integer _gateLv, Integer _maxNum)
    {
        gateName = _gateName;
        gateMainLoc = _gateMainLoc;
        gateDimensionLoc = _gateDimensionLoc;
        gateSize = 5;
        gateLv = _gateLv;
        maxMonsterNum = _maxNum;
        scheduler = GateManager.getInstance().getPlugin().getServer().getScheduler();

        addSpawnLoc("SkeletalKnight", new Vector(8, 0, 10));
        addSpawnLoc("SkeletalKnight", new Vector(9, 0, -14));
        addSpawnLoc("SkeletalKnight", new Vector(23, 0, 5));
        addSpawnLoc("SkeletalKnight", new Vector(26, 0, -7));
        addBoss("SkeletonKing", new Vector(25, 0, 0));
    }
}
