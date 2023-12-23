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

public class DestroyGateCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if(strings.length == 0)
        {
            commandSender.sendMessage("/destroyGate [삭제할 게이트 인덱스]");
            return false;
        }
        int index = Integer.parseInt(strings[0]);
        Gate targetGate = GateManager.getInstance().getIndexGate(index);

        if(targetGate == null)
        {
            TextComponent message = Component.text().color(TextColor.color(255,0,0)).content("해당 게이트가 존재하지 않습니다.").build();
            commandSender.sendMessage(message);
        }

        Vector gateMainVec = targetGate.getGateMainLoc().toVector();
        Vector gateDimensionVec = targetGate.getGateDimensionLoc().toVector();
        int lv = targetGate.getGateLevel();

        GateManager.getInstance().destroyGate(index);

        TextComponent funcMessage = Component.text().color(TextColor.color(255, 0, 0)).content("게이트 제거").build();
        commandSender.sendMessage(funcMessage);

        TextComponent nameMessage = Component.text().color(TextColor.color(255, 249, 30)).content("게이트 이름: " + targetGate.getGateName()).build();
        commandSender.sendMessage(nameMessage);

        TextComponent detailMessage = Component.text().color(TextColor.color(0,255,0)).content("메인 위치: " + gateMainVec.getX() + " " + gateMainVec.getY() + " " + gateMainVec.getZ() + " / 차원 위치: " + gateDimensionVec.getX() + " " + gateDimensionVec.getY() + " " + gateDimensionVec.getZ() + " / 레벨: " + lv).build();
        commandSender.sendMessage(detailMessage);

        return false;
    }
}
