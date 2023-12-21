package net.abyss.abyssmainplugin.Gates;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.util.Vector;

public class ZombieGate extends GateBase
{
    @Override
    public void initGate(Vector vec, Integer lv, Integer type)
    {
        Location loc = new Location(world, vec.getX(), vec.getY(), vec.getZ());
        centerLoc = loc;

        gateLv = lv;

        gateType = GATE_TYPE.values()[type];

        originBlock = world.getBlockAt(loc).getType();
        world.getBlockAt(loc).setType(Material.CRYING_OBSIDIAN);

        // Wave1
        Wave newWave = new Wave();
        newWave.setDuration(10);
        newWave.addMonster(EntityType.ZOMBIE, 5 * gateLv);
        waveList.add(newWave);

        // Wave2
        newWave = new Wave();
        newWave.setDuration(20);
        newWave.addMonster(EntityType.ZOMBIE, 10 * gateLv);
        waveList.add(newWave);

        TextComponent message = Component.text().color(TextColor.color(255,255,0)).content("좀비 게이트 생성").build();
        plugin.getServer().getConsoleSender().sendMessage(message);

        startWave();
    }
}
