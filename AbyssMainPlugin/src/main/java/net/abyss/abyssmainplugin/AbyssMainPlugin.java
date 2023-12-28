package net.abyss.abyssmainplugin;

import net.abyss.abyssmainplugin.Command.*;
import net.abyss.abyssmainplugin.Db.db_connect;
import net.abyss.abyssmainplugin.Event.*;
import net.abyss.abyssmainplugin.Event.PlayerInvEvent;
import net.abyss.abyssmainplugin.Manager.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AbyssMainPlugin extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        eventRegister();
        commandRegister();

        db_connect.setPlugin(this);
        GameManager.setPlugin(this);
        GameManager.getInstance().Init();
        CentralTower.setPlugin(this);
        GateManager.setPlugin(this);
        MonsterManager.setPlugin(this);
        ItemManager.setPlugin(this);
        TitleManager.setPlugin(this);
        PlayerManager.setPlugin(this);
    }

    @Override
    public void onDisable()
    {

    }

    private void eventRegister()
    {
        Bukkit.getPluginManager().registerEvents(new OnPlayerConnect(), this);
        Bukkit.getPluginManager().registerEvents(new MonsterEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PortalEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerItemUseEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerExpCancel(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageEvent(), this);
        Bukkit.getPluginManager().registerEvents(new CharacterStatInvClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInvEvent(), this);
        Bukkit.getPluginManager().registerEvents(new GameStartEvent(), this);
    }

    private void commandRegister()
    {
        getCommand("generateGate").setExecutor(new GenerateGateCommand());
        getCommand("destroyGate").setExecutor(new DestroyGateCommand());
        getCommand("checkAllGate").setExecutor(new CheckAllGateCommand());
        getCommand("openZombieGate").setExecutor(new ZombieGateOpenCommand());
        getCommand("statOpen").setExecutor(new StatOpenCommand());
        getCommand("setStat").setExecutor(new setStatCommand());
    }
}