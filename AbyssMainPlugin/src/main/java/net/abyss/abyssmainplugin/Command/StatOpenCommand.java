package net.abyss.abyssmainplugin.Command;

import net.abyss.abyssmainplugin.Inventory.CharacterStatGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StatOpenCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;
        CharacterStatGUI inv = new CharacterStatGUI(player);
        inv.open();
        return false;
    }
}
