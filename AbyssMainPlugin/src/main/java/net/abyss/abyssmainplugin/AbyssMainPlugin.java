package net.abyss.abyssmainplugin;

import net.abyss.abyssmainplugin.Command.CheckAllGateCommand;
import net.abyss.abyssmainplugin.Command.DestroyGateCommand;
import net.abyss.abyssmainplugin.Command.GenerateGateCommand;
import net.abyss.abyssmainplugin.Event.OnPlayerConnect;
import net.abyss.abyssmainplugin.Manager.GateManager;
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
        GateManager gateManager = GateManager.getInstance();
    }

    @Override
    public void onDisable()
    {

    }

    private void eventRegister()
    {
        OnPlayerConnect.setPlugin(this);
        OnPlayerConnect eventOnEnable = new OnPlayerConnect();
        Bukkit.getPluginManager().registerEvents(eventOnEnable, this);
    }

    private void commandRegister()
    {
        getCommand("generateGate").setExecutor(new GenerateGateCommand());
        getCommand("destroyGate").setExecutor(new DestroyGateCommand());
        getCommand("checkAllGate").setExecutor(new CheckAllGateCommand());
    }
}