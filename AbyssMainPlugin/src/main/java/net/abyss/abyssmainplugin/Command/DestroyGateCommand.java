package net.abyss.abyssmainplugin.Command;

import net.abyss.abyssmainplugin.Gates.GateBase;
import net.abyss.abyssmainplugin.Manager.GateManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class DestroyGateCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        int index = Integer.parseInt(strings[0]);
        GateBase targetGate = GateManager.getInstance().getIndexGate(index);

        if(targetGate == null)
        {
            TextComponent message = Component.text().color(TextColor.color(255,0,0)).content("해당 게이트가 존재하지 않습니다.").build();
            commandSender.sendMessage(message);
        }

        Vector gateVec = targetGate.getLocation().toVector();
        int lv = targetGate.getGateLv();

        double x = gateVec.getX();
        double y = gateVec.getY();
        double z = gateVec.getZ();

        GateManager.getInstance().destroyGate(index);
        TextComponent message = Component.text().color(TextColor.color(0,255,0)).content("("+ x + ", " + y + ", " + z + ") 위치에 " + lv + " 레벨 게이트를 제거했습니다.").build();
        commandSender.sendMessage(message);

        return false;
    }
}
