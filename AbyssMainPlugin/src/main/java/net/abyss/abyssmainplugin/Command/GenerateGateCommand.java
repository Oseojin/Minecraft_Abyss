package net.abyss.abyssmainplugin.Command;

import net.abyss.abyssmainplugin.Gates.SkeletonGate;
import net.abyss.abyssmainplugin.Manager.GateManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
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
        if(strings.length < 8)
        {
            commandSender.sendMessage("/generateGate0 [메인좌표 x] [메인좌표 y] [메인좌표 z] [차원좌표 x] [차원좌표 y] [차원좌표 z] [레벨] [최대마릿수]0 [스폰간격] [과부하시간]");
            return false;
        }
        SkeletonGate newGate = new SkeletonGate();
        String gateName = strings[0];
        Vector gateMainVec = new Vector(Double.parseDouble(strings[1]), Double.parseDouble(strings[2]) ,Double.parseDouble(strings[3]));
        Vector gateDimensionVec = new Vector(Double.parseDouble(strings[4]), Double.parseDouble(strings[5]), Double.parseDouble(strings[6]));
        int lv = Integer.parseInt(strings[7]);
        int maxNum = Integer.parseInt(strings[8]);
        Long spawnDelay = Long.parseLong(strings[9]);
        Long overloadTime = Long.parseLong(strings[10]);

        GateManager.getInstance().generateGate(0, gateMainVec);

        TextComponent funcMessage = Component.text().color(TextColor.color(255, 0, 0)).content("게이트 생성").build();
        commandSender.sendMessage(funcMessage);

        TextComponent nameMessage = Component.text().color(TextColor.color(255, 249, 30)).content("게이트 이름: " + gateName).build();
        commandSender.sendMessage(nameMessage);

        TextComponent detailMessage = Component.text().color(TextColor.color(0,255,0)).content("메인 위치: " + gateMainVec.getX() + " " + gateMainVec.getY() + " " + gateMainVec.getZ() + " / 차원 위치: " + gateDimensionVec.getX() + " " + gateDimensionVec.getY() + " " + gateDimensionVec.getZ() + " / 레벨: " + lv).build();
        commandSender.sendMessage(detailMessage);

        return false;
    }
}
