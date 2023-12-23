package net.abyss.abyssmainplugin;

import net.abyss.abyssmainplugin.Command.CheckAllGateCommand;
import net.abyss.abyssmainplugin.Command.DestroyGateCommand;
import net.abyss.abyssmainplugin.Command.GenerateGateCommand;
import net.abyss.abyssmainplugin.Command.ZombieGateOpenCommand;
import net.abyss.abyssmainplugin.Event.PortalEvent;
import net.abyss.abyssmainplugin.Event.MonsterDeathEvent;
import net.abyss.abyssmainplugin.Event.OnPlayerConnect;
import net.abyss.abyssmainplugin.Manager.GateManager;
import net.abyss.abyssmainplugin.Manager.MonsterManager;
import net.abyss.abyssmainplugin.Manager.TitleManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AbyssMainPlugin extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        eventRegister();
        commandRegister();

        GateManager.setPlugin(this);
        MonsterManager.setPlugin(this);
        TitleManager.setPlugin(this);
    }

    @Override
    public void onDisable()
    {

    }

    private void eventRegister()
    {
        Bukkit.getPluginManager().registerEvents(new OnPlayerConnect(), this);
        Bukkit.getPluginManager().registerEvents(new MonsterDeathEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PortalEvent(), this);
    }

    private void commandRegister()
    {
        getCommand("generateGate").setExecutor(new GenerateGateCommand());
        getCommand("destroyGate").setExecutor(new DestroyGateCommand());
        getCommand("checkAllGate").setExecutor(new CheckAllGateCommand());
        getCommand("openZombieGate").setExecutor(new ZombieGateOpenCommand());
    }
}