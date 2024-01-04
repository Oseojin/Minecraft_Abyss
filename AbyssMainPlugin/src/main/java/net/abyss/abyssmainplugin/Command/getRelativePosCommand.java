package net.abyss.abyssmainplugin.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class getRelativePosCommand implements CommandExecutor
{
    private static Vector referenceVec;
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        double x = Double.parseDouble(strings[0]);
        double y = Double.parseDouble(strings[1]);
        double z = Double.parseDouble(strings[2]);

        if(strings.length == 4 && strings[3].equalsIgnoreCase("set"))
        {
            referenceVec = new Vector(x, y, z);
            return false;
        }

        if(referenceVec == null)
        {
            commandSender.sendMessage("기준점이 없습니다.");
            return false;
        }

        double diffX = x - referenceVec.getX();
        double diffY = y - referenceVec.getY();
        double diffZ = z - referenceVec.getZ();

        commandSender.sendMessage("" + diffX + ", " + diffY + ", " + diffZ);

        return false;
    }
}
