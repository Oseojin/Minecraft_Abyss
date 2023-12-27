package net.abyss.abyssmainplugin.Command;

import net.abyss.abyssmainplugin.Manager.PlayerManager;
import net.abyss.abyssmainplugin.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class setStatCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;
        String statKind = strings[0];
        int value = Integer.parseInt(strings[1]);

        PlayerData playerData = PlayerManager.getInstance().getPlayerData(player);

        switch(statKind)
        {
            case "health":
                playerData.setStatHealth(value);
                break;
            case "strength":
                playerData.setStatStrength(value);
                break;
            case "rapid":
                playerData.setStatRapid(value);
                break;
            case "accel":
                playerData.setStatAccel(value);
                break;
            case "intuition":
                playerData.setStatIntuition(value);
                break;
            case "luck":
                playerData.setStatLuck(value);
                break;
            default:
                return false;
        }

        return false;
    }
}
