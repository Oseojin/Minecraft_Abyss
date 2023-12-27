package net.abyss.abyssmainplugin.Event;

import dev.lone.itemsadder.api.CustomStack;
import net.abyss.abyssmainplugin.Inventory.CharacterStatGUI;
import net.abyss.abyssmainplugin.Manager.PlayerManager;
import net.abyss.abyssmainplugin.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CharacterStatInvClickEvent implements Listener
{
    @EventHandler
    public void onClick(InventoryClickEvent event)
    {
        if(event.getClickedInventory() == null)
        {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        PlayerData playerData = PlayerManager.getInstance().getPlayerData(player);

        if(event.getView().getOriginalTitle().equalsIgnoreCase("STATGUI"))
        {
            CustomStack stack = CustomStack.byItemStack(event.getCurrentItem());
            if(stack == null)
            {
                event.setCancelled(true);
                return;
            }
            Bukkit.getConsoleSender().sendMessage(stack + "");
            if(event.getClickedInventory().equals(player.getInventory()))
            {
                switch (stack.getNamespace())
                {
                    case "custom_weapon":
                        playerData.equipWeapon(stack);
                        break;
                    case "custom_helmet":
                        playerData.equipHelmet(stack);
                        break;
                    case "custom_chest":
                        playerData.equipChest(stack);
                        break;
                    case "custom_leggings":
                        playerData.equipLeggings(stack);
                        break;
                    case "custom_boots":
                        playerData.equipBoots(stack);
                        break;
                    default:
                        event.setCancelled(true);
                }
            }
            else
            {
                switch (event.getSlot())
                {
                    case 22:
                        playerData.takeOfWeapon();
                        break;
                    case 15:
                        playerData.takeOfHelmet();
                        break;
                    case 24:
                        playerData.takeOfChest();
                        break;
                    case 33:
                        playerData.takeOfLeggings();
                        break;
                    case 42:
                        playerData.takeOfBoots();
                        break;
                    default:
                        event.setCancelled(true);
                }
            }

            event.setCancelled(true);
            event.getClickedInventory().close();
            new CharacterStatGUI(player).open();
        }
    }
}
