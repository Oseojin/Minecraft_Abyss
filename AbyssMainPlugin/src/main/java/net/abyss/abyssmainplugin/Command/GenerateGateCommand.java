package net.abyss.abyssmainplugin.Command;

import net.abyss.abyssmainplugin.Gates.ZombieGate;
import net.abyss.abyssmainplugin.Manager.GateManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class GenerateGateCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        ZombieGate newGate = new ZombieGate();
        double x = Double.parseDouble(strings[0]);
        double y = Double.parseDouble(strings[1]);
        double z = Double.parseDouble(strings[2]);
        int lv = Integer.parseInt(strings[3]);
        int type = Integer.parseInt(strings[4]);
        Vector newVec = new Vector(x, y, z);
        GateManager.getInstance().generateGate(newGate, newVec, lv, type);
        TextComponent message = Component.text().color(TextColor.color(0,255,0)).content("("+ x + ", " + y + ", " + z + ") 위치에 게이트를 생성했습니다.").build();
        commandSender.sendMessage(message);

        return false;
    }
}
