package net.abyss.abyssmainplugin.Command;

import net.abyss.abyssmainplugin.Gates.ZombieGate;
import net.abyss.abyssmainplugin.Manager.GateManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class ZombieGateOpenCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        int lv = Integer.parseInt(strings[0]);

        ZombieGate newGate = new ZombieGate();
        Vector newVec = new Vector(52, 80, -37);

        TextComponent message = Component.text().color(TextColor.color(26, 112, 51)).content("좀비 게이트를 시작합니다.").build();
        commandSender.sendMessage(message);

        TextComponent titleMessage = Component.text().color(TextColor.color(26, 112, 51)).content("좀비 게이트 Lv." + lv).build();
        TextComponent subtitleMessage = Component.text().content("").build();
        Title title =  Title.title(titleMessage, subtitleMessage, Title.DEFAULT_TIMES);
        Bukkit.getServer().showTitle(title);

        return false;
    }
}
