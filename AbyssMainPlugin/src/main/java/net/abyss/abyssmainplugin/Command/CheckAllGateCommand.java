package net.abyss.abyssmainplugin.Command;

import net.abyss.abyssmainplugin.Gates.Gate;
import net.abyss.abyssmainplugin.Manager.GateManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
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
        List<Gate> gateList = GateManager.getInstance().getGateList();

        commandSender.sendMessage(Component.text().color(TextColor.color(141, 139, 140)).content("============게이트 총 개수 : " + gateList.size() + "============").build());

        for(int i = 0; i < gateList.size(); i++)
        {
            Gate targetGate = gateList.get(i);
            if(!targetGate.getOnGate())
            {
                continue;
            }
            Vector gateMainVec = targetGate.getGateMainLoc().toVector();
            Vector gateDimensionVec = targetGate.getGateDimensionLoc().toVector();
            int lv = targetGate.getGateLevel();

            TextComponent funcMessage = Component.text().color(TextColor.color(255, 0, 0)).content("게이트 인덱스: " + (i)).build();
            commandSender.sendMessage(funcMessage);

            TextComponent nameMessage = Component.text().color(TextColor.color(255, 249, 30)).content("게이트 이름: " + targetGate.getGateName()).build();
            commandSender.sendMessage(nameMessage);

            TextComponent detailMessage = Component.text().color(TextColor.color(0,255,0)).content("메인 위치: " + gateMainVec.getX() + " " + gateMainVec.getY() + " " + gateMainVec.getZ() + " / 차원 위치: " + gateDimensionVec.getX() + " " + gateDimensionVec.getY() + " " + gateDimensionVec.getZ() + " / 레벨: " + lv).build();
            commandSender.sendMessage(detailMessage);
        }

        commandSender.sendMessage(Component.text().color(TextColor.color(141, 139, 140)).content("====================================").build());

        return false;
    }
}
