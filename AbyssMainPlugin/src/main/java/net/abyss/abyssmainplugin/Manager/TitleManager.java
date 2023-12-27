package net.abyss.abyssmainplugin.Manager;

import net.abyss.abyssmainplugin.AbyssMainPlugin;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

public class TitleManager
{
    private static TitleManager instance;

    public static TitleManager getInstance()
    {
        if(instance == null)
        {
            synchronized (TitleManager.class)
            {
                instance = new TitleManager();
            }
        }

        return instance;
    }
    private static AbyssMainPlugin plugin;
    public static void setPlugin(AbyssMainPlugin MainPlugin)
    {
        plugin = MainPlugin;
    }
    public AbyssMainPlugin getPlugin()
    {
        return plugin;
    }


    public void printTitle(TextComponent title, TextComponent subtitle)
    {
        plugin.getServer().showTitle(Title.title(title, subtitle, Title.DEFAULT_TIMES));
    }

    public void printTitleToPlayer(TextComponent title, TextComponent subtitle, Player player)
    {
        player.showTitle(Title.title(title, subtitle));
    }
}
