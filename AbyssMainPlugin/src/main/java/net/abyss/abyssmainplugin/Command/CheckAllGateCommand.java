package net.abyss.abyssmainplugin.Command;

import net.abyss.abyssmainplugin.Gates.GateBase;
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

import java.util.List;

public class CheckAllGateCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        List<GateBase> gateList = GateManager.getInstance().getGateList();

        TextComponent total = Component.text().color(TextColor.color(0,255,0)).content("totalNum: " + gateList.size()).build();
        commandSender.sendMessage(total);

        for(int i = 0; i < gateList.size(); i++)
        {
            Vector gateVec = gateList.get(i).getLocation().toVector();
            int lv = gateList.get(i).getGateLv();
            TextComponent message = Component.text().color(TextColor.color(0,255,0)).content("pos: " + gateVec.getX() + ", " + gateVec.getY() + ", " + gateVec.getZ() + "/ lv: " + lv + "/ type: " + gateList.get(i).getType()).build();
            commandSender.sendMessage(message);
        }

        return false;
    }
}
